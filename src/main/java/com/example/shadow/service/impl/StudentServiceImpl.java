package com.example.shadow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.shadow.entity.Student;
import com.example.shadow.entity.User;
import com.example.shadow.exception.exceptionEntity.BusinessException;
import com.example.shadow.mapper.StudentMapper;
import com.example.shadow.mapper.UserMapper;
import com.example.shadow.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public Boolean isStudent(Integer id) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", id).eq("status", 1);
        return studentMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public Student getStudentByUid(Integer uid) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return studentMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional
    public void authStudent(Integer id, Integer result) {
        Student student = new Student();
        student.setId(id);
        student=studentMapper.selectById(student);
        switch (result) {
            case 0:
                student.setStatus(2);
                break;
            case 1:
                student.setStatus(1);
                break;
            default:
                throw new BusinessException("无法识别的处理结果");
        }
        studentMapper.updateById(student);
        User user = new User();
        user.setUserType(2);
        user.setId(student.getUid());
        userMapper.updateById(user);
    }
}
