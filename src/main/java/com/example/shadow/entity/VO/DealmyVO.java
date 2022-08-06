package com.example.shadow.entity.VO;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DealmyVO {
    public int dealId;
    private String dealContent;
    public String fromName;
    public String dealName;
    private Double totalMoney;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    private Date endTime;
    private Integer status;
}
