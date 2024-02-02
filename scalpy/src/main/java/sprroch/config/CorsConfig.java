package sprroch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 특정 도메인에서의 요청을 허용하려면 해당 도메인을 추가하세요.
        config.addAllowedOrigin("http://localhost:8080"); // 모든 도메인에서의 요청 허용 (개발 또는 테스트 환경에서만 사용)

        // 허용할 HTTP 메서드 및 헤더를 설정합니다.
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedHeader("*"); // 모든 헤더 허용

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}