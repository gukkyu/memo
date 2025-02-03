package com.memo.config;

import com.memo.common.FileManagerService;
import com.memo.intercepter.PermissionInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration // 설정 관련 Spring bean
public class WebMvcConfig implements WebMvcConfigurer {

    private final PermissionInterceptor interceptor;

    // 인터셉터 설정
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/img/**", "/images/**", "/user/sign-out");
    }

    // 예언된 이미지 path와 서버에 업로드 된 실제 파일 mapping
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**") // ** - *, input path http://localhost/images/1_1736930601620/cropped_image.png
                .addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 파일 path
    }

}
