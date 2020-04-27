package com.etoak.config;

import com.etoak.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.mapping}")
    private String imgMapping;

    @Value("${upload.dir}")
    private String imgLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(imgMapping).addResourceLocations("file:"+imgLocation);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //添加登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**")//不拦截登录和注册请求,以及校验用户名
                .excludePathPatterns("/code")               //不拦截验证码
                .excludePathPatterns("/lib/**","/imgs/**"); //不拦截静态资源
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        //当访问contextPath时，直接跳转到index页面
        registry.addViewController("/").setViewName("index");
    }
}
