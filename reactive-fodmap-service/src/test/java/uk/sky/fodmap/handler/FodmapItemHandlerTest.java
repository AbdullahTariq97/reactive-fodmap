package uk.sky.fodmap.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.endpoint.invoke.MissingParametersException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import uk.sky.fodmap.config.SupportedFoodGroupProps;
import uk.sky.fodmap.dto.FodmapItem;
import uk.sky.fodmap.service.FodmapItemService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FodmapItemHandlerTest {

    @Mock
    private FodmapItemService fodmapItemService;

    @Mock
    private ServerRequest serverRequest;

    @Mock
    private SupportedFoodGroupProps supportedFoodGroupProps;

    @InjectMocks
    private FodmapItemHandler fodmapItemHandler;

    private static final Map<String,String> exampleItems = Map.of("VEGETABLE", "carrot", "FRUIT", "apple");
    private static final List<Object> supportedGroups = List.of(
            "FRUIT",
            "VEGETABLE",
            "DAIRY",
            "PULSES_TOFU_AND_NUTS",
            "BEVERAGES",
            "MEAT_FISH_AND_EGGS",
            "FATS_AND_OILS",
            "CONDIMENTS",
            "SNACKS_BARS_AND_COOKIES",
            "CONFECTIONERY_AND_SUGARS");

    @ParameterizedTest
    @ValueSource(strings = {"VEGETABLE", "FRUIT"})
    public void givenGroupArgumentPassed_shouldReturnAssociatedFodmapItems(String groupType){
        // Given
        when(supportedFoodGroupProps.getList()).thenReturn(supportedGroups);
        when(serverRequest.queryParam("group")).thenReturn(Optional.of(groupType));
        when(fodmapItemService.getItemsByGroup(groupType)).thenReturn(getFodmapItemFlux(groupType));

        // When
        Mono<EntityResponse<Flux<FodmapItem>>> itemsByGroup = fodmapItemHandler.getItemsByGroup(serverRequest);

        // Then
        StepVerifier.create(itemsByGroup).assertNext(entityResponse -> {
            assertThat(entityResponse.statusCode()).isEqualTo(HttpStatus.OK);
            Flux<FodmapItem> entity = entityResponse.entity();
            StepVerifier.create(entity).expectNext(new FodmapItem(groupType, exampleItems.get(groupType), null, null)).verifyComplete();
        }).verifyComplete();
    }

    @Test
    public void givenNoGroupArgumentPassed_shouldThrowRuntimeException(){
        // Given
        when(serverRequest.queryParam("group")).thenReturn(Optional.empty());

        // When
        RuntimeException runtimeException = assertThrows(MissingParametersException.class, () -> fodmapItemHandler.getItemsByGroup(serverRequest));
        assertThat(runtimeException).extracting("message").isEqualTo("Failed to invoke operation because the following required parameters were missing: group type");
    }

    @Test
    public void givenUnsupportedGroupTypePassedIn_shouldThrowRuntimeException(){
        // Given
        when(serverRequest.queryParam("group")).thenReturn(Optional.of("unsupported group type"));

        // When
        RuntimeException runtimeException = assertThrows(UnsupportedOperationException.class, () -> fodmapItemHandler.getItemsByGroup(serverRequest));
        assertThat(runtimeException).extracting("message").isEqualTo("Unsupported group type provided. Please enter supported group type ");
    }

    @Test
    public void givenGroupTypePassedHasMixedCase_shouldNotThrowRuntimeException(){
        // Given
        when(supportedFoodGroupProps.getList()).thenReturn(supportedGroups);
        when(serverRequest.queryParam("group")).thenReturn(Optional.of("Vegetable"));
        when(fodmapItemService.getItemsByGroup("Vegetable")).thenReturn(getFodmapItemFlux("Vegetable"));

        // When
        assertDoesNotThrow(() -> fodmapItemHandler.getItemsByGroup(serverRequest));
    }

    public Flux<FodmapItem> getFodmapItemFlux(String groupName){
        return Flux.just(new FodmapItem(groupName, exampleItems.get(groupName.toUpperCase()), null, null));
    }
}
