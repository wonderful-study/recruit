package com.hzu.recruit.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthActionInterceptor authActionInterceptor;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //告知系统static 当成 静态资源访问
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        //配置虚拟路径，把upload前面的路径换了
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/project/recruit/recruit-web/src/main/resources/static/images/upload/");

    }

    //配置拦截器的顺序
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //除了静态的全部拦截
        registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
        //拦截需要登录的请求
        registry.addInterceptor(authActionInterceptor).addPathPatterns("/accounts/profile");
        super.addInterceptors(registry);
    }


}
