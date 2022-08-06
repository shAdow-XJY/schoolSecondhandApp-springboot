package com.example.shadow.entity.VO;

import lombok.Data;

import java.util.Date;

@Data
public class BasicUserInfoVO {
    public String passport;
    public String nickname;
    public String email;
    public String  phone;
    private String cover;
    private Date registerTime;
}
