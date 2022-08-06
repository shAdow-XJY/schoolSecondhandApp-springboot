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
 * @since 2021-10-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Msg对象", description="")
public class Msg implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer fromUid;

    private Integer toUid;

    private String content;

    @ApiModelProperty(value = "0代表文本消息,1代表其他消息")
    private Integer contentType;

    @ApiModelProperty(value = "其他消息的文件url")
    private String contentUrl;

    @ApiModelProperty(value = "文件名")
    private String contentName;

    private Date sendTime;

    private Date createTime;

    private Date modifyTime;


}
