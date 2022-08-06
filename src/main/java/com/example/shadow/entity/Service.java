package com.example.shadow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Service对象", description="")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "1代表1级服务")
    private Integer level;

    private String serviceName;

    private String serviceContent;

    private Date createTime;

    private Date modifyTime;


}
