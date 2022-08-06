package com.example.shadow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shadow.entity.Contact;
import com.example.shadow.mapper.ContactMapper;
import com.example.shadow.service.ContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {
    @Resource
    private ContactMapper contactMapper;


    @Override
    public List<Contact> getContactsByUid(Integer uid, Integer pageNo, Integer pageSize) {
        QueryWrapper<Contact> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("uid", uid).select("contact_id");
        Page<Contact> page = new Page<>(pageNo, pageSize);
        return baseMapper.selectPage(page, queryWrapper).getRecords();

    }

    @Override
    @Transactional
    public void deleteContact(Integer fromId, Integer toId) {
        QueryWrapper<Contact> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",fromId).eq("contact_id",toId).or().eq("uid",toId).eq("contact_id",fromId);
        List<Contact> list=contactMapper.selectList(queryWrapper);
        for (Contact contact : list) {
            list.remove(contact);
        }
    }
}
