package com.example.shadow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shadow.entity.Enterprise;
import com.example.shadow.entity.Response.ResponseEnum;
import com.example.shadow.entity.Student;
import com.example.shadow.entity.User;
import com.example.shadow.entity.VO.*;
import com.example.shadow.exception.exceptionEntity.BusinessException;
import com.example.shadow.mapper.UserMapper;
import com.example.shadow.service.EnterpriseService;
import com.example.shadow.service.StudentService;
import com.example.shadow.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.shadow.util.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shAdow
 * @since 2021-10-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private StudentService studentService;

    @Resource
    private EnterpriseService enterpriseService;


    @Override
    public void userRegister(RegisterInfoVO registerInfoVO) {

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("passport", registerInfoVO.getPassport());
        if (userMapper.selectCount(userQueryWrapper) > 0) throw new BusinessException(ResponseEnum.USER_HAS_REGISTER);
        User user = new User();
        {
            user.setCover(registerInfoVO.getCover());
            user.setNickname(registerInfoVO.getUsername());
            user.setEmail(registerInfoVO.getEmail());
            user.setPassport(registerInfoVO.getPassport());
            user.setPassword(registerInfoVO.getPassword());
            user.setPhone(registerInfoVO.getPhone());
        }
        //对密码进行md5加密保存
        user.setPassword(user.getPassword());
        System.out.println(user);
        userMapper.insert(user);
    }

    @Override
    public String getNameById(Integer id) {
        User user = userMapper.selectById(id);
        return user.getNickname();
    }

    @Override
    public Boolean existUserByPassport(String passport) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passport", passport);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public Integer getUserIdByPassport(String passport) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passport", passport).select("id");
        return userMapper.selectOne(queryWrapper).getId();
    }

    @Override
    public Map<String, Object> login(String passport, String pwd) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passport", passport).eq("password", pwd);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) throw new BusinessException(ResponseEnum.PASSWORD_ERROR);
        Map<String, Object> map = new HashMap<>();
        String token = TokenUtil.getToken(passport);
        map=transferUserInfo(user);
        map.put("token", token);
        return map;
    }

    @Override
    @Transactional
    public void authStudent(Integer uid, AuthStudentVO authStudentVO) {
        Student student = new Student();
        student.setUid(uid);
        BeanUtils.copyProperties(authStudentVO, student);
        student.setAuthTime(new Date());
        studentService.save(student);

    }

    @Override
    @Transactional
    public void authEnterprise(Integer uid, AuthEnterpriseVO enterpriseVO) {
        Enterprise enterprise = new Enterprise();
        enterprise.setUid(uid);
        BeanUtils.copyProperties(enterpriseVO, enterprise);
        enterprise.setBusinessLicense(enterpriseVO.getBusiness_licenseUrl());
        enterprise.setAuthTime(new Date());
        enterpriseService.save(enterprise);

    }

    @Override
    public BasicUserInfoVO preBasicUserInfo(Integer uid) {
        User user = userMapper.selectById(uid);
        BasicUserInfoVO basicUserInfoVO = new BasicUserInfoVO();
        BeanUtils.copyProperties(user, basicUserInfoVO);
        basicUserInfoVO.setRegisterTime(user.getCreateTime());
        return basicUserInfoVO;
    }

    @Override
    public StudentUserInfoVO preStudentUserInfo(Integer uid) {
        StudentUserInfoVO studentUserInfoVO = new StudentUserInfoVO();
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        Student student = studentService.getOne(queryWrapper);
        BeanUtils.copyProperties(student, studentUserInfoVO);
        return studentUserInfoVO;
    }

    @Override
    public EnterpriseUserInfoVO preEnterpriseUserInfo(Integer uid) {
        EnterpriseUserInfoVO enterpriseUserInfoVO = new EnterpriseUserInfoVO();
        QueryWrapper<Enterprise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        Enterprise enterprise = enterpriseService.getOne(queryWrapper);
        BeanUtils.copyProperties(enterprise, enterpriseUserInfoVO);
        return enterpriseUserInfoVO;
    }

    @Override
    public int getUserType(Integer uid) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.select("user_type").eq("id", uid);
        return userMapper.selectOne(userQueryWrapper).getUserType();
    }

    @Override
    public List<StudentVO> listStudents(Integer pageNo, Integer pageSize) {
        Page<Student> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<Student>(pageNo, pageSize);
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status",1);
        List<Student> list = studentService.page(page,queryWrapper).getRecords();
        List<StudentVO> studentVOS = new ArrayList<>();
        User user = null;
        for (Student student : list) {
            StudentVO studentVO = new StudentVO();
            queryWrapper.clear();
            queryWrapper.select("nickname", "passport");
            user = userMapper.selectById(student.getUid());
            studentVO.setId(student.getUid());
            studentVO.setNickname(user.getNickname());
            studentVOS.add(studentVO);
        }
        return studentVOS;
    }

    @Override
    public List<AuthListInfo> listAuthers() {
        List<AuthListInfo> authListInfos=new ArrayList<>();

        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status",0);
        List<Student> list=studentService.list(queryWrapper);
        for (Student student : list) {
            AuthListInfo authListInfo=new AuthListInfo();
            authListInfo.setId(student.getId());
            authListInfo.setAuthType("student");
            authListInfo.setAuthTime(student.getCreateTime());
            authListInfo.setNickName(userMapper.selectById(student.getUid()).getNickname());
            authListInfos.add(authListInfo);
        }

        QueryWrapper<Enterprise> queryWrapper2=new QueryWrapper<>();
        queryWrapper2.eq("status",0);
        List<Enterprise> list2=enterpriseService.list(queryWrapper2);
        for (Enterprise enterprise : list2) {
            AuthListInfo authListInfo=new AuthListInfo();
            authListInfo.setId(enterprise.getId());
            authListInfo.setAuthType("enterprise");
            authListInfo.setAuthTime(enterprise.getCreateTime());
            authListInfo.setNickName(userMapper.selectById(enterprise.getUid()).getNickname());
            authListInfos.add(authListInfo);
        }

        return authListInfos;


    }

    @Override
    public User getUserInfoByPassport(String passport) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("passport",passport);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Map<String,Object> transferUserInfo(User user) {
        if (user == null) throw new BusinessException(ResponseEnum.PASSWORD_ERROR);
        Map<String, Object> map = new HashMap<>();
        map.put("cover", user.getCover());
        map.put("email", user.getEmail());
        map.put("nickname", user.getNickname());
        switch (user.getUserType()) {
            case 0:
                map.put("user_type", "未认证用户");
                break;
            case 1:
                map.put("user_type", "管理员");
                break;
            case 2:
                map.put("user_type", "学生用户");
                break;
            case 3:
                map.put("user_type", "企业用户");
                break;
        }
        return map;
    }


}
