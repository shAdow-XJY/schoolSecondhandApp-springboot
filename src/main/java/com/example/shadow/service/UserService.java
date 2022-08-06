package com.example.shadow.service;

import com.example.shadow.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.shadow.entity.VO.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shAdow
 * @since 2021-10-17
 */
public interface UserService extends IService<User> {
    void userRegister(RegisterInfoVO registerInfoVO);
    String getNameById(Integer id);
    Boolean existUserByPassport(String passport);
    Integer getUserIdByPassport(String passport);
    Map<String, Object> login(String passport, String pwd);

    void authStudent(Integer uid, AuthStudentVO authStudentVO);
    void authEnterprise(Integer uid, AuthEnterpriseVO enterpriseVO);
    BasicUserInfoVO preBasicUserInfo(Integer uid);
    StudentUserInfoVO preStudentUserInfo(Integer uid);
    EnterpriseUserInfoVO preEnterpriseUserInfo(Integer uid);
    int getUserType(Integer uid);


    List<StudentVO> listStudents(Integer pageNo, Integer pageSize);

    List<AuthListInfo> listAuthers();

    User getUserInfoByPassport(String passport);

    Map<String,Object> transferUserInfo(User user);
}
