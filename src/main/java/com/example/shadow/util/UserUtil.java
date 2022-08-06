package com.example.shadow.util;


import com.example.shadow.entity.Response.ResponseEnum;
import com.example.shadow.exception.exceptionEntity.BusinessException;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class UserUtil {

    @Resource
    private  RedisUtils redisUtils;

    //返回用户唯一标识
    public  Integer getUid(HttpServletRequest request) {
        String token = request.getHeader("token");
        Integer uid = (Integer) redisUtils.get(token);
        if (uid == null) throw new BusinessException(ResponseEnum.USER_NOT_LOGIN);
        return uid;
    }
}
