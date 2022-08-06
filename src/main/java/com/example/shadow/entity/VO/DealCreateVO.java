package com.example.shadow.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DealCreateVO {
    private String dealContent;
    private String dealName;
    private int serviceId;
    private String dealType;
    private Double totalMoney;
    @ApiModelProperty(value = "图片url地址")
    private String PictureUrl;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
