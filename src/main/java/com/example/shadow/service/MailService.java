package com.example.shadow.service;

import javax.mail.MessagingException;
import java.io.File;

public interface MailService {

    Boolean sendPlainMail(String title, String content, String toEmail);

    Boolean sendMimeMail(String title, String content, String toEmail, File[] attachments) throws MessagingException;

    Boolean sendFourCheckCode(String toEmail);

    Boolean sendSixCheckCode(String toEmail);
}
