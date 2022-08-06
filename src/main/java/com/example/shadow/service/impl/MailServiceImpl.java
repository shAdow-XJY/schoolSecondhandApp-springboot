package com.example.shadow.service.impl;


import com.example.shadow.service.MailService;
import com.example.shadow.util.RandomUtils;
import com.example.shadow.util.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailServiceImpl implements MailService {


    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public Boolean sendPlainMail(String title, String content, String toEmail) {
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //谁发的
        message.setFrom(from);
        //谁要接收
        message.setTo(toEmail);
        //邮件标题
        message.setSubject(title);
        //邮件内容
        message.setText(content);
        try {
            mailSender.send(message);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean sendMimeMail(String title, String content, String toEmail, File[] attachments) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            mimeMessageHelper.setSubject(title);
            mimeMessageHelper.setText(content, true);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setFrom(from);
            for (File attachment : attachments) {
                mimeMessageHelper.addAttachment(attachment.getName(), attachment);
            }
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Boolean sendFourCheckCode(String toEmail) {
        String code = RandomUtils.getFourBitRandom();
        boolean sendResult = sendPlainMail("shAdow应用验证码", "验证码" + code
                + " ,有效期为5分钟。您的shAdow应用帐号正在登录应用，如非本人操作，请立即更改密码。", toEmail);
        if (!sendResult) return false;
        redisUtils.set(toEmail, code, 60 * 5);
        return true;
    }

    @Override
    public Boolean sendSixCheckCode(String toEmail) {
        String code = RandomUtils.getSixBitRandom();
        boolean sendResult = sendPlainMail("shAdow应用验证码", "验证码" + code
                + " ,有效期为5分钟。您的shAdow应用帐号正在登录应用，如非本人操作，请立即更改密码。", toEmail);
        if (!sendResult) return false;
        redisUtils.set(toEmail, code, 60 * 5);
        return true;
    }


    /**
     *  mail:
     *     #smtp服务主机  qq邮箱则为smtp.qq.com
     *     host: smtp.163.com
     *     #服务协议
     *     protocol: smtp
     *     # 编码集
     *     default-encoding: UTF-8
     *     #发送邮件的账户
     *     username: cyw_1584537639@163.com
     *     #授权码
     *     password: YLEFKHBIVGGRERZG
     *     test-connection: true
     *     properties:
     *       mail:
     *         smtp:
     *           auth: true
     *           starttls:
     *             enable: true
     *             required: true
     */


}