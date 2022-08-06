package com.example.shadow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shadow.entity.User;
import org.mapstruct.Mapper;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-08-15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
