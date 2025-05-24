package com.guohenry.springbootmall2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("購物網站 API 文件")
                        .version("1.0.0")
                        .description("這是阿和購物網站的 OpenAPI 文件"));
    }
}
