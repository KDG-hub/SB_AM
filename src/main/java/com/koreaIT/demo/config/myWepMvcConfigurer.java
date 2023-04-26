package com.koreaIT.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.koreaIT.demo.interceptor.BeforeActotionInterceptor;
import com.koreaIT.demo.interceptor.NeedLoginInterceptor;

@Configuration
public class myWepMvcConfigurer implements WebMvcConfigurer{
	private BeforeActotionInterceptor beforeActotionInterceptor;
	private NeedLoginInterceptor needLoginInterceptor;

	@Autowired
	public myWepMvcConfigurer(BeforeActotionInterceptor beforeActotionInterceptor) {
		this.beforeActotionInterceptor = beforeActotionInterceptor;
	}
	
	@Autowired
	public myWepMvcConfigurer(NeedLoginInterceptor needLoginInterceptor) {
		this.needLoginInterceptor = needLoginInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActotionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**");
		
		registry.addInterceptor(needLoginInterceptor).addPathPatterns("/doAdd").addPathPatterns("/doDelete").addPathPatterns("/doModify");
	}
}
