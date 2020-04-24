package com.etoak.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

//配置支持同步请求(表单)发送rest请求的过滤器
@Configuration
public class RestFilterConfig {
    //将post请求转成put，delete请求
    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> registrationBean(){
        FilterRegistrationBean<HiddenHttpMethodFilter> restFilter = new FilterRegistrationBean<>(new HiddenHttpMethodFilter());
        restFilter.addUrlPatterns("/*");
        return restFilter;
    }
}
