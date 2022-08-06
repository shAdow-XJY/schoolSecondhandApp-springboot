package com.example.shadow.service;

import com.example.shadow.entity.Contact;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shAdow
 * @since 2021-10-20
 */
public interface ContactService extends IService<Contact> {
    List<Contact> getContactsByUid(Integer uid, Integer pageNo, Integer pageSize);

    void deleteContact(Integer fromId, Integer toId);
}
