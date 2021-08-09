package com.cdzq.study.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cdzq.study.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtConfig jwtConfig;

    /**
     * 创建Token
     * @param claims
     * @return
     */
    public String createToken(Map<String, Object> claims) {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());
        String token = JWT.create()
                .withIssuer(jwtConfig.getIssuer())
                .withIssuedAt(new Date())
                .withExpiresAt(DateUtils.addMinutes(new Date(), jwtConfig.getTokenExpires()))
                .withPayload(claims)
                .sign(algorithm);
        return jwtConfig.getTokenStartWith() + token;
    }

    /**
     * 从request请求中获取Token
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        final String bearerToken = request.getHeader(jwtConfig.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtConfig.getTokenStartWith())) {
            return bearerToken.replace(jwtConfig.getTokenStartWith(), "");
        }
        return null;
    }

    /**
     * 从Token中获取某key的值
     * @param token
     * @param key
     * @return
     */
    public Claim verifierToken(String token, String key) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(jwtConfig.getIssuer())
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(key);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }


}
