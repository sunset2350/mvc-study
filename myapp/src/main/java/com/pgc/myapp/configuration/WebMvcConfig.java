package com.pgc.myapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    // CORS(cross origin resource sharing)
    // 다른 origin 끼리 자원을 공유할 수 있게 하는 것
    // 기본으로 웹(js)에서는 CORS가 안됨.
    // origin : 구성요는ㄴ 프로토콜 + 주소(도메인,IP) + 포트
    // http:// + localhost + :5500
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**") // 모든 경로에 대해
                .allowedOrigins(
                        "http://localhost:5500",
                        "http://127.0.0.1:5500") // 로컬 호스트 origin 허용
                .allowedMethods("*"); // 모든 메서드 허용
    }
}