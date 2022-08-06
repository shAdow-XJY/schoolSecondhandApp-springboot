package com.example.shadow.controller;


import com.example.shadow.entity.Response.R;
import com.example.shadow.entity.Response.ResponseEnum;
import com.example.shadow.entity.VO.ContactVO;
import com.example.shadow.entity.VO.MsgVO;
import com.example.shadow.service.MsgService;
import com.example.shadow.util.Assert;
import com.example.shadow.util.RedisUtils;
import com.example.shadow.util.UserUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
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
 * @since 2021-10-20
 */
@RestController
@RequestMapping("/shadow/msg")
public class MsgController {
    @Resource
    private UserUtil userUtil;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private MsgService iMsgService;

    @ApiOperation("获取用户联系人列表及最近的一条消息记录")
    @GetMapping("/contacts")
    public R getContacts(@RequestParam(required = false) @Nullable Integer pageSize,
                         @RequestParam(required = false) @Nullable Integer pageNo, HttpServletRequest request) {
        if (pageSize == null || pageSize <= 0) pageSize = 100;
        if (pageNo == null || pageNo <= 0) pageNo = 1;
        //判断是否登陆
        Integer uid = userUtil.getUid(request);

        List<ContactVO> contactVOList = iMsgService.getContacts(uid, pageNo, pageSize);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("page_no", pageNo);
        responseData.put("page_size", pageSize);
        responseData.put("contacts", contactVOList);
        return R.ok().data(responseData);
    }

    @ApiOperation("查看用户与某个具体的人的聊天记录")
    @GetMapping("/contacts/{contactId}")
    public R getMessageContent(@ApiParam("对方的唯一标识") @PathVariable Integer contactId,
                               @RequestParam(required = false) @Nullable Integer pageSize,
                               @RequestParam(required = false) @Nullable Integer pageNo, HttpServletRequest request) {
        if (pageSize == null || pageSize <= 0) pageSize = 1000;
        if (pageNo == null || pageNo <= 0) pageNo = 1;
        //判断是否登陆
        Integer uid = userUtil.getUid(request);
        List<MsgVO> list = iMsgService.getMsgWithUid(uid, contactId, pageNo, pageSize);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("page_no", pageNo);
        responseData.put("page_size", pageSize);
        responseData.put("msg", list);
        return R.ok().data(responseData);
    }


    @ApiOperation("用户给某个人发送消息")
    @PostMapping("/contacts/{contactId}")
    public R sendMessage(@ApiParam("对方的唯一标识") @PathVariable Integer contactId, HttpServletRequest request,
                         @ApiParam("0代表文本消息，1代表文件消息") @RequestParam Integer msgType,
                         @RequestParam(required = false) @Nullable String content,
                         @RequestBody(required = false) @Nullable MultipartFile file) {
        //判断是否登陆
        Integer uid = userUtil.getUid(request);
        //判断是否是自己聊天
        //Assert.isTrue(uid != contactId, ResponseEnum.SELF_CONTACT_ERROR);

        redisUtils.del(uid+"contactsmsg"+contactId);
        if (msgType == 0) {
            iMsgService.savePlainMsg(uid, contactId, content);
        } else {
            iMsgService.saveFileMsg(uid, contactId, file);
        }
        return R.ok().message("消息发送成功");
    }


}

