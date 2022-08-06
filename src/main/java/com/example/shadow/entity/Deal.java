package com.example.shadow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author shAdow
 * @since 2021-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Deal对象", description="")
public class Deal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String dealName;

    private String dealContent;

    private Integer serviceId;

    private Date commitTime;

    private Date endTime;

    @ApiModelProperty(value = "交易咨询"+"订单申报")
    private String dealType;

    private Double totalMoney;

    @ApiModelProperty(value = "是否支付")
    private Boolean isPayment;

    private Date createTime;

    private Date modifyTime;

    @ApiModelProperty(value = "订单发起人id")
    private Integer fromId;

    @ApiModelProperty(value = "订单承接人id")
    private Integer toId;

    @ApiModelProperty(value = "0代表未审核，1代表已审核，2代表订单已结束，-1代表审核失败")
    private Integer status;

    @ApiModelProperty(value = "图片url地址")
    private String PictureUrl;


}
