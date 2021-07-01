package com.cdzq.study.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    /** Request Headers ： Authorization */
    private String header;

    /** 令牌前缀，最后留个空格 Bearer */
    private String tokenStartWith;

    /** 令牌 秘钥 */
    private String secret;

    /** 令牌 颁发者 */
    private String issuer;

    /** 令牌过期时间 分钟 */
    private Integer tokenExpires;

    /** 重写get 加个空格 */
    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }
}
