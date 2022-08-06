package com.example.shadow.service;

import com.example.shadow.entity.Enterprise;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shAdow
 * @since 2021-10-17
 */
public interface EnterpriseService extends IService<Enterprise> {
    Enterprise getEnterpriseByUid(Integer uid);

    void authEnterprise(Integer id, Integer result);
}
