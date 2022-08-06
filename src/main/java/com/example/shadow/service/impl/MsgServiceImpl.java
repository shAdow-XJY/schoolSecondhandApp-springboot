package com.example.shadow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shadow.entity.Contact;
import com.example.shadow.entity.DTO.MsgTypeEnum;
import com.example.shadow.entity.Msg;
import com.example.shadow.entity.User;
import com.example.shadow.entity.VO.ContactVO;
import com.example.shadow.entity.VO.MsgVO;
import com.example.shadow.mapper.ContactMapper;
import com.example.shadow.mapper.MsgMapper;
import com.example.shadow.mapper.UserMapper;
import com.example.shadow.service.ContactService;
import com.example.shadow.service.MsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shadow.util.RedisUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shAdow
 * @since 2021-10-20
 */
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements MsgService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MsgMapper msgMapper;
//
//    @Resource
//    private FileService fileService;
    @Resource
    private ContactMapper contactMapper;

    @Resource
    private ContactService contactService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 获取联系人列表，同时返回最近的一条消息
     *
     * @param uid
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public List<ContactVO> getContacts(Integer uid, Integer pageNo, Integer pageSize) {
        if (redisUtils.hasKey(uid + "contacts" + pageNo)) {
            return (List<ContactVO>) redisUtils.get(uid + "contacts" + pageNo);
        }
        User user = userMapper.selectById(uid);
        List<Contact> contacts = contactService.getContactsByUid(uid, pageNo, pageSize);
        List<Integer> list = new ArrayList<>();
        for (Contact contact : contacts) {
            list.add(contact.getContactId());
        }
        List<User> users = userMapper.selectBatchIds(list);
        List<ContactVO> contactVOList = new ArrayList<>();
        Page<Msg> page = new Page<>(1, 1);
        for (User user1 : users) {
            ContactVO contactVO = new ContactVO();
            contactVO.setContactName(user1.getNickname());
            contactVO.setContactId(user1.getId());
            contactVO.setCover(user1.getCover());
            QueryWrapper<Msg> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.
                    eq("from_uid", uid).eq("to_uid", user1.getId()).
                    or(true).eq("from_uid", user1.getId()).eq("to_uid", uid).
                    select("from_uid", "content", "content_type", "content_name", "content_url").
                    orderBy(true, false, "send_time");
            try {
                Msg msg = msgMapper.selectPage(page, queryWrapper1).getRecords().get(0);
                if (msg != null) {
                    contactVO.setContent(msg.getContent());
                    contactVO.setContentType(msg.getContentType());
                    contactVO.setContentUrl(msg.getContentUrl());
                    contactVO.setContentName(msg.getContentName());
                    if (msg.getFromUid().equals(uid)) {
                        contactVO.setContentSender(user.getNickname());
                    } else {
                        contactVO.setContentSender(user1.getNickname());
                    }
                }
            } catch (Exception ignored) {
            } finally {
                contactVOList.add(contactVO);
            }
        }
        redisUtils.set(uid + "contacts" + pageNo, contactVOList, 10);
        return contactVOList;
    }

    /**
     * 查看与某人的聊天记录 我是uid 对方是contactId
     *
     * @param uid
     * @param contactId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public List<MsgVO> getMsgWithUid(Integer uid, Integer contactId, Integer pageNo, Integer pageSize) {
        if (redisUtils.hasKey(uid + "contactsmsg" + contactId)) {
            return (List<MsgVO>) redisUtils.get(uid + "contactsmsg" + contactId);
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", uid);
        User u1 = userMapper.selectOne(userQueryWrapper);
        userQueryWrapper.clear();
        userQueryWrapper.eq("id", contactId);
        User u2 = userMapper.selectOne(userQueryWrapper);

        QueryWrapper<Msg> queryWrapper = new QueryWrapper<Msg>();
        queryWrapper.eq("from_uid", uid).eq("to_uid", contactId).
                or(true).eq("from_uid", contactId).eq("to_uid", uid).
                select("from_uid", "content", "content_type", "content_name", "content_url", "send_time").
                orderBy(true, false, "send_time");
        //TODO 分页获取消息，这样太卡了
        Page<Msg> page = new Page<>(pageNo, pageSize);
        List<Msg> msgList = msgMapper.selectPage(page, queryWrapper).getRecords();
//        List<Msg> msgList = msgMapper.selectList(queryWrapper);
        List<MsgVO> msgVOList = new ArrayList<>();
        for (Msg msg : msgList) {
            MsgVO msgVO = new MsgVO();
            msgVO.setContent(msg.getContent());
            msgVO.setContentType(msg.getContentType());
            msgVO.setContentUrl(msg.getContentUrl());
            msgVO.setSendTime(msg.getSendTime());
            msgVO.setContentName(msg.getContentName());
            if (msg.getFromUid().equals(uid)) {
                msgVO.setSenderName(u1.getNickname());
                msgVO.setSenderCover(u1.getCover());
            } else {
                msgVO.setSenderName(u2.getNickname());
                msgVO.setSenderCover(u2.getCover());
            }
            msgVOList.add(msgVO);
        }
        redisUtils.set(uid + "contactsmsg" + contactId, msgVOList, 30);
        return msgVOList;
    }

    /**
     * 保存文本消息
     *
     * @param uid
     * @param contactId
     * @param content
     */
    @Override
    public void savePlainMsg(Integer uid, Integer contactId, String content) {
        //没有判断是否已是在联系列表上
        Contact contact = new Contact();
        contact.setUid(uid);
        contact.setContactId(contactId);

        QueryWrapper<Contact> contactQueryWrapper = new QueryWrapper<>();
        System.out.print(contactQueryWrapper.eq("uid",uid));

        contactMapper.insert(contact);


        Msg msg = new Msg();
        msg.setFromUid(uid);
        msg.setToUid(contactId);
        msg.setContent(content);
        msg.setContentType(MsgTypeEnum.PLAIN_TYPE.getCode());
        msg.setSendTime(new Date());
        msgMapper.insert(msg);
    }

    /**
     * 保存文件消息
     *
     * @param uid
     * @param contactId
     * @param file
     */
    @Override
    public void saveFileMsg(Integer uid, Integer contactId, MultipartFile file) {
//        InputStream inputStream = null;
//        try {
//            inputStream = file.getInputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new BusinessException(ResponseEnum.FILE_UPLOAD_FAILED);
//        }
//        String originalFilename = file.getOriginalFilename();
//        String url = fileService.upload(inputStream, "msg", originalFilename);
//        Msg msg = new Msg();
//        msg.setFromUid(uid);
//        msg.setToUid(contactId);
//        msg.setContentType(MsgTypeEnum.FILE_TYPE.getCode());
//        msg.setSendTime(new Date());
//        msg.setContentUrl(url);
//        msg.setContentName(originalFilename);
//        msgMapper.insert(msg);
    }

    // 保存项目处理消息
    @Override
    public void saveDealMsg(Integer uid, Integer contactId, String content) {
        Msg msg = new Msg();
        msg.setFromUid(uid);
        msg.setToUid(contactId);
        msg.setContent(content);
        msg.setContentType(MsgTypeEnum.PROJECT_TYPE.getCode());
        msg.setSendTime(new Date());
        msgMapper.insert(msg);
    }

    // 保存分配信息
    @Override
    public void saveAssignMsg(Integer dealId) {
//        Msg msg = new Msg();
//        msg.setFromUid(0);
//
//        msg.setContent("您有一个新的项目分配，请处理！");
//        msg.setSendTime(new Date());
//        msg.setContentType(MsgTypeEnum.ASSIGN_TYPE.getCode());
//        msgMapper.insert(msg);
    }
}
