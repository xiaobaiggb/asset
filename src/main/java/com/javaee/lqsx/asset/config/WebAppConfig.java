package com.javaee.lqsx.asset.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @description: 注册拦截器
 **/
//注册拦截器
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        //注册自己的拦截器并设置拦截的请求路径
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/**")//拦截的访问路径，拦截所有
                .excludePathPatterns("/static/*","/**/**/*.css","/**/**/*.js","/**/**/**/*.css","/**/**/**/*.js","/**/**/**/*.jpg")
                .excludePathPatterns("/gologin","/login","/loginOut","/register","/registerSub");//排除的请求路径，排除静态资源路径，把不拦截的排除掉
        super.addInterceptors(registry);
    }
}
