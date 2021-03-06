package com.cdzq.study.config;

import com.cdzq.study.interceptor.BaseInterceptor;
import com.cdzq.study.security.SecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Bean
	public SecurityInterceptor getSecurityInterceptor(){
		return new SecurityInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(new BaseInterceptor())
		        .addPathPatterns("/**")
		        .excludePathPatterns("/static/**")// 排除静态资源
				.excludePathPatterns("/file/**")// 排除静态资源
				.excludePathPatterns("/avatar/**")// 排除静态资源
				.excludePathPatterns("/druid/**")// 阿里巴巴 druid
				.excludePathPatterns("/swagger-ui.html")
				.excludePathPatterns("/swagger-resources/**")
				.excludePathPatterns("/error");// 排除404
		registry.addInterceptor(getSecurityInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/static/**")// 排除静态资源
				.excludePathPatterns("/file/**")// 排除静态资源
				.excludePathPatterns("/avatar/**")// 排除静态资源
				.excludePathPatterns("/druid/**")// 阿里巴巴 druid
				.excludePathPatterns("/swagger-ui.html")
				.excludePathPatterns("/swagger-resources/**")
				.excludePathPatterns("/error");// 排除404
		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
