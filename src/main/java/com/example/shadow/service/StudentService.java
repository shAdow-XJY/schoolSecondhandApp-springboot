package com.example.shadow.service;

import com.example.shadow.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shAdow
 * @since 2021-10-17
 */
public interface StudentService extends IService<Student> {
    Boolean isStudent(Integer id);

    Student getStudentByUid(Integer uid);

    void authStudent(Integer id, Integer result);
}
