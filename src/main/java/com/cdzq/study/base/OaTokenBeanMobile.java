package com.cdzq.study.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(value = "com.cdzq.study.base.OaTokenBeanMobile",description = "OATokenMobile")
@Getter
@Setter
public class OaTokenBeanMobile {

    @ApiModelProperty(value = "loginid")
    @NotNull(message = "loginid不能为空")
    private String loginid;
    @ApiModelProperty(value = "stamp")
    @NotNull(message = "stamp不能为空")
    private String stamp;
    @ApiModelProperty(value = "token")
    @NotNull(message = "token不能为空")
    private String token;
}
