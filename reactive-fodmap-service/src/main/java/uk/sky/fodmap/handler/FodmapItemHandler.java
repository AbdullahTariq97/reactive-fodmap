package uk.sky.fodmap.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.invoke.MissingParametersException;
import org.springframework.boot.actuate.endpoint.invoke.OperationParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.sky.fodmap.config.SupportedFoodGroupProps;
import uk.sky.fodmap.dto.FodmapItem;
import uk.sky.fodmap.dto.GroupTypeParameter;
import uk.sky.fodmap.service.FodmapItemService;

import java.util.Set;

@Service
public class FodmapItemHandler {

    @Autowired
    private FodmapItemService fodmapItemService;

    @Autowired
    private SupportedFoodGroupProps supportedFoodGroupProps;

    public Mono<EntityResponse<Flux<FodmapItem>>> getItemsByGroup(ServerRequest request) {
        System.out.println("A request was received by our handler");
        return request
            .queryParam("group")
            .map(this::verifyRequest)
            .map(group -> EntityResponse.fromPublisher(fodmapItemService.getItemsByGroup(group), FodmapItem.class).build())
            .orElseThrow(() -> new MissingParametersException(Set.of(new GroupTypeParameter())));
    }

    private String verifyRequest(String group) {
        if(!supportedFoodGroupProps.getList().contains(group.toUpperCase())){
            throw new UnsupportedOperationException("Unsupported group type provided. Please enter supported group type ");
        }
        return group;
    }
}
