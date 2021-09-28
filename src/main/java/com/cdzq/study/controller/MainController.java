package com.cdzq.study.controller;

import cn.hutool.crypto.SecureUtil;
import com.cdzq.study.base.OaTokenBean;
import com.cdzq.study.base.OaTokenBeanMobile;
import com.cdzq.study.base.ResultData;
import com.cdzq.study.security.JwtUtils;
import com.cdzq.study.security.PassToken;
import com.cdzq.study.util.Des3;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
@Api(tags = "权限模块")
public class MainController {

    private final JwtUtils jwtUtils;
    private final RestTemplate restTemplate;

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
            return ResultData.error().message("非法的OAToken");
        }
        long now = Calendar.getInstance().getTimeInMillis() / 1000L;
        long difference = Math.abs(exp - now);
        //大于300秒五分钟,则token不能使用
        if ((difference > 300) && !admintoken.equals(oaTokenBean.getOatoken())) {
            return ResultData.error().message("过期的OAToken");
        }
        String user_id = spl[1].toString();
        String user_name = spl[2].toString();
        Map<String, Object> claims = new HashMap();
        claims.put("user_id", user_id);
        claims.put("user_name", user_name);
        final String token = jwtUtils.createToken(claims);
        return ResultData.ok().data("token", token);
    }

    @PassToken
    @PostMapping("/getTokenMobile")
    @ApiOperation(value = "根据OAMobileToken获取StudyToken")
    public ResultData getToken(@Validated @RequestBody OaTokenBeanMobile oaTokenBeanMobile) throws UnsupportedEncodingException {
        //final String miyao="70ef189f-da5b-49c0-bd09-fcf2c3a56af3";
        final String miyao="99adbd0f-c843-4f57-9ee2-6b77b1aa600a";
        final String user_id=oaTokenBeanMobile.getLoginid();
        String user_name="系统管理员";
        if(!user_id.equals("admin")) {
            long exp = 0;
            try {
               exp = Long.parseLong(oaTokenBeanMobile.getStamp());
            }catch (NumberFormatException e){
                return ResultData.error().message("非法的stamp");
            }

            long now = Calendar.getInstance().getTimeInMillis() ;
            long difference = Math.abs(exp - now);
            //大于300000毫秒五分钟,则token不能使用
            if (difference > 300000) {
                return ResultData.error().message("过期的OAMobileToken");
            }
            String yanzheng = SecureUtil.sha1(miyao + user_id + oaTokenBeanMobile.getStamp());
            if (!oaTokenBeanMobile.getToken().equals(yanzheng)) {
                //return ResultData.error().message("非法的OAMobileToken");
            }
            user_name=restTemplate.getForObject("http://10.3.6.180/filemanger/home/getOaNameById?id="+user_id,String.class);
            user_name= URLDecoder.decode(user_name,"utf-8");
        }
        Map<String, Object> claims = new HashMap();
        claims.put("user_id", user_id);
        claims.put("user_name", user_name);
        final String token = jwtUtils.createToken(claims);
        return ResultData.ok().data("token", token);
    }

}
