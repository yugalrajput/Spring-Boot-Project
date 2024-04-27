package com.rays;

import com.rays.common.FrontCtl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringBootProjectApplication {


    @Autowired
    private FrontCtl frontCtl;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProjectApplication.class, args);

    }

    @Bean
    public WebMvcConfigurer corsConfigure() {
        WebMvcConfigurer w = new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                CorsRegistration cors = registry.addMapping("/**");
                cors.allowedOrigins("http://localhost:4200");
                cors.allowedHeaders("*");
                cors.allowCredentials(true);
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(frontCtl).addPathPatterns("/**").excludePathPatterns("/Auth/**");
            }
        };
        return w;

    }


}
