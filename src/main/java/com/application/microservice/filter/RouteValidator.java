package com.application.microservice.filter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    private static final List<String> openApiEndpoints = List.of(
        "/auth/login",
        "/auth/register",
        "/auth/resetPassword",
        "/auth/forgotPassword",
        "/auth/resendOtp",
        "/auth/otpVerification",
        "/eureka"
        
    );

   
    public Predicate<ServerHttpRequest> isSecured = request ->
    openApiEndpoints
        .stream()
        .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
