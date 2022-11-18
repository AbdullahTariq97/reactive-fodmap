package uk.sky.fodmap.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import uk.sky.fodmap.dto.FodmapItem;
import uk.sky.fodmap.repository.FodmapItemRepository;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FodmapItemServiceTest {

    @Mock
    private FodmapItemRepository fodmapItemRepository;

    @InjectMocks
    private FodmapItemService fodmapItemService;

    private final List<FodmapItem> dummyFodmapVegetableItems = List.of(new FodmapItem("vegetable", "carrot", null, null), new FodmapItem("vegetable", "leek", null, null));
    private final List<FodmapItem> dummyFodmapFruitItems = List.of(new FodmapItem("fruit", "apple", null, null), new FodmapItem("fruit", "banana", null, null));
    private final Map<String, List<FodmapItem>> foodGroupToFodmapItems = Map.of("vegetable", dummyFodmapVegetableItems, "fruit", dummyFodmapFruitItems);

    @ParameterizedTest
    @ValueSource(strings = {"vegetable", "fruit"})
    public void givenGroupTypePassedIn_shouldReturnFluxWithFodmapItems(String groupType){
        // Given
        when(fodmapItemRepository.findByFoodGroupIgnoreCase(groupType)).thenReturn(foodGroupToFodmapItems.get(groupType));

        // When
        Flux<FodmapItem> itemsByGroup = fodmapItemService.getItemsByGroup(groupType);

        // Then
        StepVerifier.create(itemsByGroup).expectNext(foodGroupToFodmapItems.get(groupType).get(0)).expectNext(foodGroupToFodmapItems.get(groupType).get(1)).verifyComplete();
    }

    @Test
    public void givenGroupWithMixedCasePassedIn_shouldCallRepositoryWithLowerCaseFoodGroup(){
        // When
        Flux<FodmapItem> itemsByGroup = fodmapItemService.getItemsByGroup("VeGetable");

        // Then
        verify(fodmapItemRepository).findByFoodGroupIgnoreCase("vegetable");
    }
}