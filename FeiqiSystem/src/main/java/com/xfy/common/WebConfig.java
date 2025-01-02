/**
 * WebConfig
 *
 * @author Feiqi
 * @date 2024/10/08  下午 2:45
 */
package com.xfy.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        /*指定Controller统一的接口前缀，相当于在url上拼了一个"/?"前缀*/
        configurer.addPathPrefix("/feiqi", clazz -> clazz.isAnnotationPresent(RestController.class));
    }

    /*加自定义拦截器JwtInterceptor 设置拦截规则*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/feiqi/**")
                .excludePathPatterns("/feiqi/admin/login").excludePathPatterns("/feiqi/admin/register");
    }
}