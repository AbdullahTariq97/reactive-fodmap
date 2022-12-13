package uk.sky.fodmap.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Order(-2)
public class GlobalErrorWebHandler implements WebExceptionHandler {

    private static final ObjectMapper om = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        try {
            byte[] bytes = om.writeValueAsBytes(Map.of("message", ex.getMessage()));
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            exchange.getResponse().setRawStatusCode(500);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (JsonProcessingException jsonProcessingException) {
            return null;
        }
    }
}
