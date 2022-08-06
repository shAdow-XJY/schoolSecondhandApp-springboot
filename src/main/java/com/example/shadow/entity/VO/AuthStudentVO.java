package com.example.shadow.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthStudentVO {

    private String realName;
    @ApiModelProperty(value = "学校")
    private String school;
    @ApiModelProperty(value = "学号")
    private String idNumber;
    @ApiModelProperty(value = "“硕士”,“博士”,“本科”")
    private String degree;
    @ApiModelProperty(value = "年")
    private Integer studyTime;
    @ApiModelProperty(value = "0代表man,1代表woman,2代表私密")
    private Integer sex;

}
