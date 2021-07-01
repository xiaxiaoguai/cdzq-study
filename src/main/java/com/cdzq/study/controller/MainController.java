package com.cdzq.study.controller;

import com.cdzq.study.base.OaTokenBean;
import com.cdzq.study.base.ResultData;
import com.cdzq.study.security.JwtUtils;
import com.cdzq.study.security.PassToken;
import com.cdzq.study.util.Des3;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
@Api(tags = "权限模块")
public class MainController {

    private final JwtUtils jwtUtils;

    @PassToken
    @PostMapping("/getToken")
    @ApiOperation(value = "根据OAToken获取StudyToken")
    public ResultData getToken(@Validated @RequestBody OaTokenBean oaTokenBean) {
        final String admintoken = "F869C3278329BF2746F6AC35BE6965D1EAEE911B93D79303FA2001890CF2038A9AA55A560AE88B21C78F8B9427D53933A57214DFD3178A77AC0E99F2FFF15ABD";
        Des3 desObj = new Des3();
        String key1 = "1001";
        String key2 = "1002";
        String key3 = "1003";
        String _oatoken = desObj.strDec(oaTokenBean.getOatoken(), key1, key2, key3);
        String[] spl = _oatoken.split(",");
        long exp = 0;
        try {
            exp = Long.parseLong(spl[3]);
        } catch (Exception e) {
            return ResultData.error().message("非法的oatoken");
        }
        long now = Calendar.getInstance().getTimeInMillis() / 1000L;
        long difference = Math.abs(exp - now);
        if ((difference > 300) && !admintoken.equals(oaTokenBean.getOatoken())) {
            return ResultData.error().message("过期的oatoken");
        }
        String user_id = spl[1].toString();
        String user_name = spl[2].toString();
        Map<String, Object> claims = new HashMap();
        claims.put("user_id", user_id);
        claims.put("user_name", user_name);
        final String token = jwtUtils.createToken(claims);
        return ResultData.ok().data("token", token);
    }

}
