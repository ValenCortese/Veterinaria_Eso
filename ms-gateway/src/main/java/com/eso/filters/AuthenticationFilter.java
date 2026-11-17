package com.eso.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. Obtener la ruta de la petición
        String path = exchange.getRequest().getPath().value();

        // La pantalla de acceso y sus recursos deben poder cargarse sin sesión.
        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS
                || path.startsWith("/auth/") || path.endsWith(".html")
                || path.startsWith("/assets/")) {
            return chain.filter(exchange);
        }

        // 3. Obtener el header Authorization
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 4. Si no tiene header o no empieza con "Bearer " -> HTTP 401
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 5. Extraer el token (quitar el prefijo "Bearer ")
        String token = authHeader.substring(7);

        // 6. Validar el token con jwtService.validateToken(token)
        if (!jwtService.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 7. Si es válido -> agregar username como header para downstream
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("X-Auth-User", jwtService.extractUsername(token))
                .build();

        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}