package com.example.shadow.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DealServiceVO {

    @ApiModelProperty(value = "1代表1级服务")
    public Integer level;


    public String serviceName;

    public String serviceContent;
}
