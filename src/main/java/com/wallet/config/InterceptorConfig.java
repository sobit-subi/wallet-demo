package com.wallet.config;

import com.wallet.common.JwtInterceptor;
import com.wallet.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor(userMapper)) // configure JWT interceptor rule
                .addPathPatterns("/api/wallet/**");  // intercept all url paths
        super.addInterceptors(registry);
    }

//    @Override
//    protected void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .exposedHeaders(HttpHeaders.SET_COOKIE)
//                .maxAge(3600);
//    }

    @Bean
    public JwtInterceptor getJwtInterceptor() {
        return new JwtInterceptor(userMapper);
    }
}
