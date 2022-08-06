package com.example.shadow.entity.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum ResponseEnum {
    SUCCESS(1, "请求成功"),
    ERROR(0, "发生错误"),

    //1xx 服务器错误
    SERVLET_ERROR(102, "servlet请求异常"),//-2xx 参数校验
    FILE_UPLOAD_FAILED(103, "文件上传失败"),

    //2xx 用户错误
    USER_NOT_LOGIN(201, "用户未登陆"),
    NOT_STUDENT_ERROR(202, "用户非学生"),
    CHECK_CODE_ERROR(203, "验证码不正确"),
    CHECK_CODE_EXPIRED(204, "验证码已过期"),
    MAIL_IS_NULL(205, "用户邮箱为空"),
    USER_HAS_REGISTER(206, "账号已经被注册"),
    USER_LOGIN_EXPIRE(207, "本次登陆已过期，请重新登陆"),
    PASSPORT_NOT_EXIST(208, "用户账号不存在"),
    PASSPORT_IS_NULL(209, "用户账号为空"),
    PASSWORD_IS_NULL(210, "用户密码为空"),
    PASSWORD_ERROR(211, "账号密码不正确"),
    ID_NUMBER_ERROR(212, "身份证号码错误"),
    USER_HAD_REGISTER(213, "用户已经认证，不可重复认证"),
    PHONE_FORMAT_ERROR(214, "手机号码格式错误"),
    NOT_ENTERPRISE_ERROR(215, "非企业用户禁止申请项目"),
    NOT_ADMIN_ERROR(216, "非管理员禁止操作"),
    ERROR_UNAUTH(217, "未认证用户禁止操作"),


    //3xx订单错误
    DEAL_ID_IS_NULL(301, "订单id为空"),
    DEAL_NOT_EXISt(302, "订单不存在"),
    DEAL_STATUS_NULL(303, "订单状态为空"),
    DEAL_PARTY_EXIT(304,"当事人在订单中已经存在"),
    AUTH_ILLEGAL(305,"非订单创建人，请求非法"),
    DEAL_FILE_UPLOAD_FAIL(306,"订单文件上传失败"),
    DEAL_FILE_EXIST(307,"订单文件已经存在"),
    DEAL_FILE_DELETE_FAIL(308,"订单文件删除失败"),
    DEAL_FILE_DOWNLOAD_FAIL(308,"订单文件删除失败"),
    DEAL_RECORD_NOT_EXIST(309,"订单记录不存在"),
    DEAL_STATE_ERROR(310,"订单状态错误"),
    NOT_ASSIGN_STUDENT(311,"非分配学生，禁止处理"),

    //聊天错误
    SELF_CONTACT_ERROR(312,"聊天用户为本人");

    private final Integer code;
    private final String message;

}
