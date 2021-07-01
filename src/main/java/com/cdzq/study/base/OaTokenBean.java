package com.cdzq.study.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(value = "com.cdzq.study.base.OaTokenBean",description = "OAToken")
@Getter
@Setter
public class OaTokenBean {

    @ApiModelProperty(value = "oatoken")
    @NotNull(message = "oatoken不能为空")
    private String oatoken;
}
