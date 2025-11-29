package com.eljon.realestate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Merr rrugën absolute të projektit
        String projectDir = System.getProperty("user.dir").replace("\\", "/");

        // Fotot
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:" + projectDir + "/uploads/images/");

    }
}




