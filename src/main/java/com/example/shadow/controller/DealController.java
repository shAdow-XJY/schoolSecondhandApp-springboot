package com.example.shadow.controller;


import com.example.shadow.entity.Deal;
import com.example.shadow.entity.Response.R;
import com.example.shadow.entity.Response.ResponseEnum;
import com.example.shadow.entity.VO.*;
import com.example.shadow.service.DealService;
import com.example.shadow.service.UserService;
import com.example.shadow.util.Assert;
import com.example.shadow.util.UserUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.bind.annotation.*;

import reactor.util.annotation.Nullable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shAdow
 * @since 2021-11-01
 */
@RestController
@RequestMapping("/shadow/deal")
public class DealController {
    @Resource
    private DealService iDealService;

    @Resource
    private UserUtil userUtil;

    @Resource
    private UserService userService;


    @ApiOperation("用户获取订单列表(不包括自己)")
    @GetMapping("")
    public R listDeals(@RequestParam(required = false) @Nullable Integer pageSize,
                       @RequestParam(required = false) @Nullable Integer pageNo,
                       @ApiParam("订单过滤列表，‘书籍’‘器材’‘乐器’‘用品’‘其它’")
                       @RequestParam(required = false) String serviceName,
                       HttpServletRequest request) {
        if (pageSize == null || pageSize <= 0) pageSize = 10;
        if (pageNo == null || pageNo <= 0) pageNo = 1;
        if (serviceName == null) serviceName = "";
        Integer uid = userUtil.getUid(request);

        List<DealVO> dealVOList = iDealService.listDeals(uid, serviceName,pageNo, pageSize);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("page_no", pageNo);
        responseData.put("page_size", pageSize);
        responseData.put("deals", dealVOList);
        return R.ok().data(responseData);
    }

    @ApiOperation("用户获取自己的订单列表")
    @GetMapping("/mydeals")
    public R listMyDeals(@RequestParam(required = false) @Nullable Integer pageSize,
                       @RequestParam(required = false) @Nullable Integer pageNo,
                       HttpServletRequest request) {
        if (pageSize == null || pageSize <= 0) pageSize = 10;
        if (pageNo == null || pageNo <= 0) pageNo = 1;

        Integer uid = userUtil.getUid(request);

        List<DealmyVO> dealVOList = iDealService.listMyDeals(uid, pageNo, pageSize);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("page_no", pageNo);
        responseData.put("page_size", pageSize);
        responseData.put("deals", dealVOList);
        return R.ok().data(responseData);
    }
//    public R listDeals(@RequestParam(required = false) @Nullable Integer pageSize,
//                       @RequestParam(required = false) @Nullable Integer pageNo,
//                       @ApiParam("订单过滤列表，空代表全部，new代表新分配订单，runnning end类推") @RequestParam(required = false) String filter,
//                       HttpServletRequest request) {
//        if (pageSize == null || pageSize <= 0) pageSize = 100;
//        if (pageNo == null || pageNo <= 0) pageNo = 1;
//        if (filter == null) filter = "";
//        Assert.isTrue(userService.getUserType(userUtil.getUid(request))!=0, ResponseEnum.NOT_ADMIN_ERROR);
//        Integer uid = userUtil.getUid(request);
//        List<DealVO> dealVOList = iDealService.listDeals(uid, filter, pageNo, pageSize);
//        Map<String, Object> responseData = new HashMap<>();
//        responseData.put("page_no", pageNo);
//        responseData.put("page_size", pageSize);
//        responseData.put("deals", dealVOList);
//        return R.ok().data(responseData);
//    }


    @ApiOperation("获取订单具体内容")
    @GetMapping("/{dealId}")
    public R getDealDetails(
            @ApiParam("订单的唯一标识") @PathVariable Integer dealId) {
        DealDetailVO dealContentVO = iDealService.getDetail(dealId);
        return R.ok().data("deal_detail", dealContentVO);
    }


    @ApiOperation("企业上传支付证明")
    @PostMapping("/{dealId}/fee")
    public R uploadFeeAuth(
            @ApiParam("订单的唯一标识") @PathVariable Integer dealId, @ApiParam("支付证明url") @RequestParam String feeUrl) {
//        System.out.println(feeUrl);
//        Deal deal = iDealService.getById(dealId);
//        if (deal != null) {
//            deal.setPayPictureUrl(feeUrl);
//            iDealService.updateById(deal);
//            return R.ok();
//        }
        return R.error().message("订单不存在");
    }

    @ApiOperation("查看订单服务方案")
    @GetMapping("/service/{dealId}")
    public R getDealService(@ApiParam("订单的唯一标识") @PathVariable Integer dealId, HttpServletRequest request) {
        DealServiceVO dealServiceVO = iDealService.getDealService(dealId);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("deal_service", dealServiceVO);
        return R.ok().data(responseData);
    }

    @ApiOperation("查看未审核订单")
    @GetMapping("/unassigned")
    public R listUnassignedDeals(@RequestParam(required = false) @Nullable Integer pageSize,
                                    @RequestParam(required = false) @Nullable Integer pageNo,
                                    HttpServletRequest request) {
        if (pageSize == null || pageSize <= 0) pageSize = 10;
        if (pageNo == null || pageNo <= 0) pageNo = 1;
        Integer uid = userUtil.getUid(request);
        if (uid != 1) return R.error().message("非管理员禁止访问");
        List<DealVO> dealVOList = iDealService.listUnassignedDeals(pageNo, pageSize);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("page_no", pageNo);
        responseData.put("page_size", pageSize);
        responseData.put("deals", dealVOList);
        return R.ok().data(responseData);

    }

    @ApiOperation("查看支付申请订单")
    @GetMapping("/fee-list")
    public R listDealFeeList(@RequestParam(required = false) @Nullable Integer pageSize,
                                @RequestParam(required = false) @Nullable Integer pageNo,
                                HttpServletRequest request) {
        if (pageSize == null || pageSize <= 0) pageSize = 10;
        if (pageNo == null || pageNo <= 0) pageNo = 1;
        Integer uid = userUtil.getUid(request);
        if (uid != 1) return R.error().message("非管理员禁止访问");
        List<DealVO> dealVOList = iDealService.listFeeList(pageNo, pageSize);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("page_no", pageNo);
        responseData.put("page_size", pageSize);
        responseData.put("deals", dealVOList);
        return R.ok().data(responseData);

    }

    @ApiOperation("管理员通过订单申请")
    @PostMapping("/assign")
    public R assignDeal(@ApiParam("订单的唯一标识") @RequestParam Integer dealId,
                           HttpServletRequest request) {
        Integer uid = userUtil.getUid(request);
        Assert.isTrue(uid == 1, ResponseEnum.NOT_ADMIN_ERROR);
        Assert.isTrue(iDealService.getById(dealId).getStatus() == 0, ResponseEnum.DEAL_STATE_ERROR);
        iDealService.assignDeal(dealId);
        return R.ok().message("订单已经分配，等待学生处理");
    }

    @ApiOperation("管理员拒绝订单申请")
    @PostMapping("/assign/reject")
    public R rejectAssignDeal(@ApiParam("订单的唯一标识") @RequestParam Integer dealId,
                                 HttpServletRequest request) {
        Integer uid = userUtil.getUid(request);
        Assert.isTrue(uid == 1, ResponseEnum.NOT_ADMIN_ERROR);
        iDealService.rejectAssignDeal(dealId);
        return R.ok().message("订单拒绝成功");
    }

    @ApiOperation("审核处理新订单")
    @PostMapping("/{dealId}")
    public R handleDeal(@RequestParam() @ApiParam("0代表拒绝，1代表同意") Integer handleResult,
                           @PathVariable(value = "dealId") Integer dealId,
                           HttpServletRequest request) {
        Integer uid = userUtil.getUid(request);
        Assert.isTrue(userService.getUserType(uid) == 2, ResponseEnum.NOT_STUDENT_ERROR);
        Deal deal = iDealService.getById(dealId);
        Assert.isTrue(deal.getToId().equals(uid), ResponseEnum.NOT_ASSIGN_STUDENT);
        Assert.isTrue(deal.getStatus().equals(1), ResponseEnum.DEAL_STATE_ERROR);
        if (handleResult == 1) {
            iDealService.approveDeal(dealId);
        } else {
            iDealService.rejectDeal(dealId);
        }
        return R.ok().message("订单处理成功");
    }

    @ApiOperation("管理员审核支付证明处理结果")
    @PostMapping("/{dealId}/fee-handle")
    public R handleDealFee(@RequestParam() @ApiParam("0代表拒绝，1代表同意") Integer handleResult,
                              @PathVariable(value = "dealId") Integer dealId,
                              HttpServletRequest request) {
        /*Integer uid = userUtil.getUid(request);
        Assert.isTrue(uid == 1, ResponseEnum.NOT_ADMIN_ERROR);
        Deal deal = iDealService.getById(dealId);
        if (deal == null) return R.error().message("订单不存在");
        if (handleResult == 1) {
            deal.setIsPayment(true);
        } else {
            deal.setPayPictureUrl(null);
        }
        iDealService.updateById(deal);*/
        return R.ok().message("订单处理成功");
    }

    @ApiOperation("管理员收取订单费用，设置订单为已支付")
    @PostMapping("/set-pay")
    public R payForDeal(@PathVariable(value = "dealId") Integer dealId,
                           HttpServletRequest request) {
        Integer uid = userUtil.getUid(request);
        Assert.isTrue(uid == 1, ResponseEnum.NOT_ADMIN_ERROR);
        Deal deal = iDealService.getById(dealId);
        deal.setIsPayment(true);
        return R.ok().message("订单已经支付");
    }

    @ApiOperation("发起订单")
    @PostMapping("/commit")
    public R commitDeal(
            @RequestBody DealCreateVO dealCreateVO, HttpServletRequest request) {
        //判断是否登陆
        Integer uid = userUtil.getUid(request);
        Assert.isTrue(userService.getUserType(uid) != 0, ResponseEnum.ERROR_UNAUTH);

        System.out.print(dealCreateVO);
        iDealService.commitDeal(uid, dealCreateVO);
        return R.ok().message("订单发起成功");
    }
}

