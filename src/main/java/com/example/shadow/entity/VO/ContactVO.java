package com.example.shadow.entity.VO;

import lombok.Data;

@Data
public class ContactVO {
    public Integer contactId;
    public Integer contentType;

    public String contactName;
    public String cover;

    public String content;
    public String contentUrl;
    public String contentName;
    public String contentSender;
}
