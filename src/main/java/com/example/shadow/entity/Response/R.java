package com.example.shadow.entity.Response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value="R对象", description="响应体规范格式")
public class R {
    @ApiModelProperty(value = "响应码")
    private Integer code;
    @ApiModelProperty(value = "响应消息")
    private String message;
    @ApiModelProperty(value = "响应数据")
    private Map<String,Object> data=new HashMap<>();

    private R(){};

    /**
     * 返回成功的结果
     * @return
     */
    public static R ok(){
        R r=new R();
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setMessage(ResponseEnum.SUCCESS.getMessage());
        return r;
    }

    /**
     * 返回失败的结果
     * @return
     */
    public static R error(){
        R r=new R();
        r.setCode(ResponseEnum.ERROR.getCode());
        r.setMessage(ResponseEnum.ERROR.getMessage());
        return r;
    }

    /**
     * 自定义枚举设置特定的结果
     * @param responseEnum
     * @return
     */
    public static R setResult(ResponseEnum responseEnum){
        R r=new R();
        r.setCode(responseEnum.getCode());
        r.setMessage(responseEnum.getMessage());
        return r;
    }

    /**
     * 设置特定的消息
     * @param message
     * @return
     */
    public R message(String message){
        this.message=message;
        return this;
    }

    /**
     * 自定义code
     * @param code
     * @return
     */
    public R code(Integer code){
        this.code=code;
        return this;
    }

    public R data(Map<String,Object> map) {
        this.data=map;
        return this;
    }

    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }


}
