package org.spring.springprojectT.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfigMvcClass implements WebMvcConfigurer {

    String saveFiles="file:///E:/full/upload/"; // 실제 파일이 저장되는 경로

 @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
     registry.addResourceHandler("/upload/**") // /upload/이미지명 ->가상
             .addResourceLocations(saveFiles);
 }
}
