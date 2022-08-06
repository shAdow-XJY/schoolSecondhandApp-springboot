package com.example.shadow.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FileVO {
    private String fileName;
    @ApiModelProperty("0代表文件夹，1代表普通文件")
    private Integer fileType;
}
