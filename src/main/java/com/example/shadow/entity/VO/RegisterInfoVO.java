package com.example.shadow.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInfoVO {
    public String passport;
    public String password;
    public String username;
    public String email;
    public String checkCode;
    public String cover;
    public String phone;
}
