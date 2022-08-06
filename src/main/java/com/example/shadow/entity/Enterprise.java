package com.example.shadow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
 * @since 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Enterprise对象", description="")
public class Enterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private String enterpriseName;

    private String institutionCode;

    private String enterpriseAdd;

    private String businessLicense;

    private Date authTime;

    private Date createTime;

    private Date modifyTime;

    @ApiModelProperty(value = "0代表未认证,1代表认证通过,2代表认证拒绝")
    private Integer status;


}
