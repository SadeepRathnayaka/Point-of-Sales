package com.point_of_sales.api_gateway.security;

import com.point_of_sales.api_gateway.exception.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request;

            // Check if the route is secured, and if it's not, skip JWT authentication
            if (!routeValidator.isSecured.test(exchange.getRequest())) {
                // If not secured, just continue to the next filter
                return chain.filter(exchange);
            }

            // Check for Authorization header
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new AuthenticationException("Missing Request Header!");
            }

            String authHeader = extractTokenFromHeader(exchange.getRequest());

            if (authHeader == null) {
                exchange.getResponse().setStatusCode(config.getUnauthorizedStatus());
                return exchange.getResponse().setComplete();
            }

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseSignedClaims(authHeader)
                        .getPayload();

                if (isTokenExpired(claims)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                String role = claims.get("Role", String.class);
                String username = claims.getSubject();

//                System.out.println(username);

                request = exchange.getRequest()
                                .mutate()
                        .header("Username", username)
                                .header("Role", role)
                                .build();

//                System.out.println(request.getHeaders());

            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    private String extractTokenFromHeader(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring(7) : null;
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    @Setter
    @Getter
    public static class Config {
        private org.springframework.http.HttpStatus unauthorizedStatus = org.springframework.http.HttpStatus.UNAUTHORIZED;

    }
}
