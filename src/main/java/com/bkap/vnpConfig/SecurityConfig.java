package com.bkap.vnpConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class SecurityConfig implements WebMvcConfigurer{
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/imgupp/**")
            .addResourceLocations("file:C:/Users/Dell/eclipse-workspace/TemmePsa-2/Upload/");
}
}
