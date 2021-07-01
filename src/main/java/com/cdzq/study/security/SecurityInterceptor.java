package com.cdzq.study.security;

import com.auth0.jwt.interfaces.Claim;
import com.cdzq.study.base.ResultData;
import com.cdzq.study.exception.ExceptionCode;
import com.cdzq.study.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtUtils jwtUtils;

	/**
	 * 权限拦截器
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		if(!(handler instanceof HandlerMethod)){
			return HandlerInterceptor.super.preHandle(request, response, handler);
		}
		HandlerMethod handlerMethod=(HandlerMethod)handler;
		Method method=handlerMethod.getMethod();
		if (method.isAnnotationPresent(PassToken.class)) {
			PassToken passToken = method.getAnnotation(PassToken.class);
			if (passToken.required()) {
				return HandlerInterceptor.super.preHandle(request, response, handler);
			}
		}
		String token = jwtUtils.getToken(request);
		if(token!=null){
			Claim user_id = jwtUtils.verifierToken(token, "user_id");
			if(user_id==null){
				ResultData resultData = ResultData.error();
				resultData.setResultcode(ExceptionCode.NOT_FORBIDDEN.getCode());
				resultData.setMessage(ExceptionCode.NOT_FORBIDDEN.getValue());
				ResponseUtil.out(response, resultData);
				return false;
			}else{
				request.setAttribute("user_id",user_id.asString());
			}
			Claim user_name = jwtUtils.verifierToken(token, "user_name");
			if(user_name==null){
				ResultData resultData = ResultData.error();
				resultData.setResultcode(ExceptionCode.NOT_FORBIDDEN.getCode());
				resultData.setMessage(ExceptionCode.NOT_FORBIDDEN.getValue());
				ResponseUtil.out(response, resultData);
				return false;
			}else{
				request.setAttribute("user_name",user_name.asString());
			}
		}else{
			ResultData resultData = ResultData.error();
			resultData.setResultcode(ExceptionCode.NOT_LOGIN.getCode());
			resultData.setMessage(ExceptionCode.NOT_LOGIN.getValue());
			ResponseUtil.out(response, resultData);
			return false;
		}
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}
