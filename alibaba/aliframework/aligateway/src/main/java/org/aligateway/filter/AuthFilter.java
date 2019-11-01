package org.aligateway.filter;

import java.util.Map;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import reactor.core.publisher.Mono;


@Component
public class AuthFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (token == null || token.isEmpty()) {
            ServerHttpResponse response = exchange.getResponse();
            Map<Object, Object> map = Maps.newHashMap();
            map.put("code", 401);
            map.put("message", "非法请求！");
            map.put("cause", "Token not is null");

            ObjectMapper mapper = new ObjectMapper();
            try {
                byte[] bytes = mapper.writeValueAsBytes(map);
                // 输出错误信息到页面
                DataBuffer buffer = response.bufferFactory().wrap(bytes);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


        }

        return chain.filter(exchange);
    }


    //设置过滤器的执行顺序
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}

