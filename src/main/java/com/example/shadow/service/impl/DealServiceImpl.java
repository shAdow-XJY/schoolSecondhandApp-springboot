package com.example.shadow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shadow.entity.Contact;
import com.example.shadow.entity.Deal;
import com.example.shadow.entity.User;
import com.example.shadow.entity.VO.*;
import com.example.shadow.mapper.DealMapper;
import com.example.shadow.mapper.ServiceMapper;
import com.example.shadow.mapper.UserMapper;
import com.example.shadow.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shadow.util.DateUtil;
import com.example.shadow.util.RedisUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shAdow
 * @since 2021-11-01
 */
@Service
public class DealServiceImpl extends ServiceImpl<DealMapper, Deal> implements DealService {
    @Resource
    private DealMapper dealMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ServiceMapper serviceMapper;
    @Resource
    private MsgService msgService;
    @Resource
    private UserService userService;
    @Resource
    private ContactService contactService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private EnterpriseService enterpriseService;
    @Resource
    private StudentService studentService;
//    @Override
//    public List<DealVO> listDeals(Integer uid, String filter, Integer pageNo, Integer pageSize) {
//        if (redisUtils.hasKey(uid + "DealVOList" + filter + pageNo)) {
//            return (List<DealVO>) redisUtils.get(uid + "DealVOList" + filter + pageNo);
//        }
//        QueryWrapper<User> uQueryWrapper = new QueryWrapper<>();
//        uQueryWrapper.eq("id", uid).select("user_type");
//        int userType = userMapper.selectOne(uQueryWrapper).getUserType();
//        String idType = "";
//        if (userType == 3) {
//            idType = "from_id";
//        } else if (userType == 2) {
//            idType = "to_id";
//        }
//        Integer state = null;
//        switch (filter) {
//            case "new":
//                state = 0;
//                break;
//            case "running":
//                state = 2;
//                break;
//            case "end":
//                state = 3;
//                break;
//            case "reject":
//                state = -1;
//                break;
//        }
//        QueryWrapper<Deal> queryWrapper = new QueryWrapper<>();
//        if (!StringUtil.isNullOrEmpty(idType)) queryWrapper.eq(idType, uid);
//        if (state != null) {
//            if (state == 0 && idType.equals("from_id")) {
//                queryWrapper.in("status", 0, 1);
//            } else if (state == 0 && idType.equals("to_id")) {
//                queryWrapper.eq("status", 1);
//            } else
//                queryWrapper.eq("status", state);
//        }
//        queryWrapper.orderBy(true, false, "create_time");
//        Page<Deal> page = new Page<>(pageNo, pageSize);
//        List<Deal> deals = dealMapper.selectPage(page, queryWrapper).getRecords();
//        List<DealVO> dealVOList = new ArrayList<>();
//        for (Deal deal : deals) {
//            DealVO dealVO = new DealVO();
//            dealVO.setDealId(deal.getId());
//            dealVO.setCommitTime(deal.getCommitTime());
//            dealVO.setDealName(deal.getDealName());
//            dealVO.setEndTime(deal.getEndTime());
//            int fromId = deal.getFromId();
//            User fromUser = userMapper.selectById(fromId);
//            dealVO.setFromName(fromUser.getNickname());
//            dealVOList.add(dealVO);
//        }
//        redisUtils.set(uid + "DealVOList" + filter + pageNo, dealVOList, 10);
//        return dealVOList;
//    }

    @Override
    public List<DealVO> listDeals(Integer uid, String serviceName,Integer pageNo, Integer pageSize) {
        QueryWrapper<Deal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.ne("from_id",uid);
        int service_id = 0;
        switch (serviceName){
            case "书籍":
                service_id = 1;
                break;
            case "用品":
                service_id = 2;
                break;
            case "乐器":
                service_id = 3;
                break;
            case "器材":
                service_id = 4;
                break;
            case "其它":
                service_id = 5;
                break;
        }
        if(service_id!=0){
            queryWrapper.eq("service_id", service_id);
        }
        Page<Deal> page = new Page<>(pageNo, pageSize);
        List<Deal> deals = dealMapper.selectPage(page, queryWrapper).getRecords();
        List<DealVO> dealVOList = new ArrayList<>();
        for (Deal deal : deals) {
            DealVO dealVO = new DealVO();
            BeanUtils.copyProperties(deal, dealVO);
            dealVO.setDealId(deal.getId());
            int fromId = deal.getFromId();
            User fromUser = userMapper.selectById(fromId);
            dealVO.setFromName(fromUser.getNickname());
            dealVOList.add(dealVO);
        }
        return dealVOList;
    }

    @Override
    public List<DealmyVO> listMyDeals(Integer uid, Integer pageNo, Integer pageSize){
        QueryWrapper<Deal> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("status", 1).or().eq("status", 2);
        queryWrapper.eq("from_id",uid).or().eq("to_id",uid);

        Page<Deal> page = new Page<>(pageNo, pageSize);
        List<Deal> deals = dealMapper.selectPage(page, queryWrapper).getRecords();
        List<DealmyVO> dealVOList = new ArrayList<>();
        for (Deal deal : deals) {
            DealmyVO dealVO = new DealmyVO();
            BeanUtils.copyProperties(deal, dealVO);
            dealVO.setDealId(deal.getId());
            int fromId = deal.getFromId();
            User fromUser = userMapper.selectById(fromId);
            dealVO.setFromName(fromUser.getNickname());
            dealVOList.add(dealVO);
        }
        return dealVOList;
    }

    @Override
    public DealDetailVO getDetail(Integer dealId) {
        String key="dealDetail"+dealId;
        if(redisUtils.hasKey(key)){
            return (DealDetailVO) redisUtils.get(key);
        }
        Deal deal = dealMapper.selectById(dealId);
        DealDetailVO dealDetailVO = new DealDetailVO();
        BeanUtils.copyProperties(deal, dealDetailVO);
        dealDetailVO.setService(serviceMapper.selectById(deal.getServiceId()));
        dealDetailVO.setFromName(userService.getNameById(deal.getFromId()));
        dealDetailVO.setEnterprise(enterpriseService.getEnterpriseByUid(deal.getFromId()));
        if(deal.getStatus()==2||deal.getStatus()==3&&deal.getToId() != null){
            dealDetailVO.setToName(userService.getNameById(deal.getToId()));
            dealDetailVO.setStudent(studentService.getStudentByUid(deal.getToId()));
        }
        redisUtils.set(key,dealDetailVO,30);
        return dealDetailVO;
    }

    @Override
    public DealServiceVO getDealService(Integer dealId) {
        QueryWrapper<Deal> dealQueryWrapper = new QueryWrapper<>();
        dealQueryWrapper.select("service_id").eq("id", dealId);
        Integer serviceId = dealMapper.selectOne(dealQueryWrapper).getServiceId();
        com.example.shadow.entity.Service service = serviceMapper.selectById(serviceId);
        DealServiceVO dealServiceVO = new DealServiceVO();
        BeanUtils.copyProperties(service, dealServiceVO);
        return dealServiceVO;
    }

    @Override
    @Transactional
    public void approveDeal(Integer dealId) {
//        Deal deal = baseMapper.selectById(dealId);
//        deal.setId(dealId);
//        deal.setStatus(2);
//        int pricePerMonth = serviceMapper.selectById(deal.getServiceId()).getPrice();
//
//        //TODO 价格的计算有点问题，先这样，看下怎么计算合理
//        deal.setTotalMoney(pricePerMonth / 30.0 * DateUtil.differentDays(new Date(), deal.getEndTime()));
//        deal.setIsPayment(false);
//
//        baseMapper.updateById(deal);
//        msgService.saveDealMsg(1, deal.getFromId(), "您的项目编号为:" + deal.getId() + " 的律师处理已经审批完成，审批结果为同意。持续时间为" + new Date() + "~" + deal.getEndTime() + ",总费用为:" + deal.getTotalMoney() + "。项目已经启动。");
//
//        QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("uid", deal.getFromId());
//        queryWrapper.eq("contact_id", deal.getToId());
//        if (contactService.count(queryWrapper) > 0) return;
//        Contact contact = new Contact();
//        contact.setUid(deal.getFromId());
//        contact.setContactId(deal.getToId());
//        contactService.save(contact);
//        contact.setContactId(deal.getFromId());
//        contact.setUid(deal.getToId());
//        contactService.save(contact);
    }


    @Override
    @Transactional
    public void rejectDeal(Integer dealId) {
        Deal deal = new Deal();
        deal.setId(dealId);
        deal.setToId(null);
        deal.setStatus(0);
        baseMapper.updateById(deal);
        deal = baseMapper.selectById(deal);
        msgService.saveDealMsg(1, deal.getFromId(), "您的项目编号为:" + deal.getId() + " 的律师处理已经审批完成，审批结果为拒绝；请联系管理员重新进行分配");
    }

    @Override
    public void commitDeal(Integer uid, DealCreateVO dealCreateVO) {
        Deal deal = new Deal();
        deal.setCommitTime(new Date());
        deal.setStatus(0);
        BeanUtils.copyProperties(dealCreateVO, deal);
        deal.setFromId(uid);
        baseMapper.insert(deal);
    }

    @Override
    public List<DealVO> listUnassignedDeals(Integer pageNo, Integer pageSize) {
        QueryWrapper<Deal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0);
        Page<Deal> page = new Page<>(pageNo, pageSize);
        List<Deal> deals = dealMapper.selectPage(page, queryWrapper).getRecords();
        List<DealVO> dealVOList = new ArrayList<>();
        for (Deal deal : deals) {
            DealVO dealVO = new DealVO();
            BeanUtils.copyProperties(deal, dealVO);
            dealVO.setDealId(deal.getId());
            int fromId = deal.getFromId();
            User fromUser = userMapper.selectById(fromId);
            dealVO.setFromName(fromUser.getNickname());
            dealVOList.add(dealVO);
        }
        return dealVOList;
    }

    @Override
    public void rejectAssignDeal(Integer dealId) {
        Deal deal = new Deal();
        deal.setId(dealId);
        deal.setStatus(-1);
        dealMapper.updateById(deal);
    }

    @Override
    public List<DealVO> listFeeList(Integer pageNo, Integer pageSize) {
        QueryWrapper<Deal> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("status", 2, 3).eq("is_payment", false).isNotNull("pay_picture_url");
        Page<Deal> page = new Page<>(pageNo, pageSize);
        List<Deal> deals = dealMapper.selectPage(page, queryWrapper).getRecords();
        List<DealVO> dealVOList = new ArrayList<>();
        for (Deal deal : deals) {
            DealVO dealVO = new DealVO();
            BeanUtils.copyProperties(deal, dealVO);
            dealVO.setDealId(deal.getId());
            int fromId = deal.getFromId();
            User fromUser = userMapper.selectById(fromId);
            dealVO.setFromName(fromUser.getNickname());
            dealVOList.add(dealVO);
        }
        return dealVOList;
    }

    @Override
    @Transactional
    public void assignDeal(Integer dealId) {
        Deal deal = new Deal();
        deal.setId(dealId);
        deal.setStatus(1);
        dealMapper.updateById(deal);
        msgService.saveAssignMsg(dealId);
    }
}
