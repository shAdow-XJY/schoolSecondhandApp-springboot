package com.example.shadow.controller;


import com.example.shadow.entity.Response.R;
import com.example.shadow.service.ServiceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shAdow
 * @since 2021-11-01
 */
@RestController
@RequestMapping("/shadow/service")
public class ServiceController {
    @Resource
    private ServiceService service;

    @ApiOperation("获取所有服务内容列表")
    @GetMapping("/")
    public R getServiceList() {
//        System.out.println("service.list()");
//        System.out.println("service.list()");
//        System.out.println("service.list()");
//        System.out.println(service.list());
        return R.ok().data("services", service.list());
    }
}

