package com.zxy.config;

import com.zxy.config.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final List<String> EXCLUDE_PATH = Arrays.asList(
            "/", "/css/**", "/js/**", "/images/**", "/api/**", "/lib/**"
            , "/login", "/bootstrap/**", "/jquery/**", "/register","/toReg"
            ,"/toDapan","/toIndex","/page/news"
    );

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    /**
     * 解决跨域请求
     *
     * @param registry 跨域注册参数
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    /**
     * 静态资源映射
     *
     * @param registry 资源映射参数
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * 拦截器注册
     *
     * @param registry 拦截器参数
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATH);
    }
}
