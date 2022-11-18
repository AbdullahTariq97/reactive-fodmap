package uk.sky.fodmap.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.core.publisher.Flux;
import uk.sky.fodmap.dto.FodmapItem;
import uk.sky.fodmap.handler.FodmapItemHandler;

@Configuration
public class RequestRouter {

    @Autowired
    private FodmapItemHandler fodmapItemHandler;

    @Bean
    public RouterFunction<EntityResponse<Flux<FodmapItem>>> getItemsByGroup(){
        return RouterFunctions.route(RequestPredicates.GET("/items"), request -> {
            System.out.println("A request was received by our router");
            return fodmapItemHandler.getItemsByGroup(request);
        });
    }
}
