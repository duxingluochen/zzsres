package com.mapscience.core.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter{

	@Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new CustomHandlerInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
	
}
