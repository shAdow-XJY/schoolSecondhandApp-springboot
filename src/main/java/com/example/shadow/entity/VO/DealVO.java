package com.example.shadow.entity.VO;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DealVO {
    public int dealId;
    public String fromName;
    public String dealName;
    private Double totalMoney;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    @ApiModelProperty(value = "图片url地址")
    private String PictureUrl;

}
