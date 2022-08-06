package com.example.shadow.entity.VO;

import lombok.Data;

import java.util.Date;

@Data
public class MsgVO {

    public String senderName;
    public String senderCover;
    public String content;
    public Integer contentType;
    public String contentUrl;
    public String contentName;
    public Date sendTime;
}