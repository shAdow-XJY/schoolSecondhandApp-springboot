package com.example.shadow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shadow.entity.Enterprise;
import com.example.shadow.entity.User;
import com.example.shadow.exception.exceptionEntity.BusinessException;
import com.example.shadow.mapper.EnterpriseMapper;
import com.example.shadow.mapper.UserMapper;
import com.example.shadow.service.EnterpriseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shAdow
 * @since 2021-10-17
 */
@Service
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise> implements EnterpriseService {
    @Resource
    private UserMapper userMapper;

    @Override
    public Enterprise getEnterpriseByUid(Integer uid) {
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void authEnterprise(Integer id, Integer result) {
        Enterprise enterprise = new Enterprise();
        enterprise.setId(id);
        enterprise=baseMapper.selectById(id);
        switch (result) {
            case 0:
                enterprise.setStatus(2);
                break;
            case 1:
                enterprise.setStatus(1);
                break;
            default:
                throw new BusinessException("无法识别的处理结果");
        }
        baseMapper.updateById(enterprise);
        User user = new User();
        user.setUserType(3);
        user.setId(enterprise.getUid());
        userMapper.updateById(user);
    }
}
