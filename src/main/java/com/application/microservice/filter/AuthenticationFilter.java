package com.application.microservice.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.application.microservice.service.JwtUtil;

import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private RestTemplate template;

    @Autowired
    private JwtUtil jwtUtil;

    public static class Config {
        // Config properties can be defined here if needed
    }

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                // Check if the Authorization header is present
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return Mono.error(new RuntimeException("Missing Authorization header"));
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                } else {
                    return Mono.error(new RuntimeException("Invalid Authorization header format"));
                }

                try {
                    // Validate the token using the jwtUtil service
                    jwtUtil.validateToken(authHeader);
                } catch (Exception e) {
                    return Mono.error(new RuntimeException("Unauthorized access to application: " + e.getMessage()));
                }
            }
            return chain.filter(exchange);
        };
    }
}
