package com.jingdianjichi.subject.application.config;

import com.jingdianjichi.subject.application.interceptor.FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
    
    @Bean
    public FeignRequestInterceptor getFeignRequestInterceptor()
    {
        return new FeignRequestInterceptor();
    }
}
