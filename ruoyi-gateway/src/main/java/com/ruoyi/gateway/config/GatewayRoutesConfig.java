package com.ruoyi.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("ruoyi-auth", r -> r.path("/auth/**")
                .filters(f -> f.stripPrefix(1))
                .uri("http://localhost:9200"))
            .route("ruoyi-system", r -> r.path("/system/**")
                .filters(f -> f.stripPrefix(1))
                .uri("http://localhost:9201"))
            .route("ruoyi-gen", r -> r.path("/code/**")
                .filters(f -> f.stripPrefix(1))
                .uri("http://localhost:9202"))
            .route("ruoyi-job", r -> r.path("/schedule/**")
                .filters(f -> f.stripPrefix(1))
                .uri("http://localhost:9203"))
            .route("ruoyi-file", r -> r.path("/file/**")
                .filters(f -> f.stripPrefix(1))
                .uri("http://localhost:9300"))
            .build();
    }
}
