package com.example.shadow.controller;


import com.example.shadow.entity.Response.R;
import com.example.shadow.service.EnterpriseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shAdow
 * @since 2021-10-17
 */
@RestController
@RequestMapping("/shadow/enterprise")
public class EnterpriseController {
    @Resource
    private EnterpriseService enterpriseService;

    @ApiOperation("根据id获取企业认证信息")
    @GetMapping("/info/{id}")
    public R getEnterpriseInfo(@PathVariable Integer id, HttpServletRequest request){
        return R.ok().data("info",enterpriseService.getById(id));
    }
}

