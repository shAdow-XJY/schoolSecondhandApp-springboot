package com.example.shadow.entity.VO;

import com.example.shadow.entity.Enterprise;
import com.example.shadow.entity.Service;
import com.example.shadow.entity.Student;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.Date;

@Data
public class DealDetailVO {

    private Integer id;
    // id
    private String fromName;
    public Student student;
    public Enterprise enterprise;
    private String toName;
    private String dealName;
    private String dealContent;
    private Service service;
    private Date commitTime;
    private Date endTime;
    @ApiModelProperty(value = "出售"+"收购")
    private String dealType;
    private Double totalMoney;
    @ApiModelProperty(value = "订单发起人id")
    private Integer fromId;
    @ApiModelProperty(value = "是否支付")
    private Boolean isPayment;
    private String PictureUrl;
    private Date createTime;
    private Date modifyTime;
    private Integer status;
}
