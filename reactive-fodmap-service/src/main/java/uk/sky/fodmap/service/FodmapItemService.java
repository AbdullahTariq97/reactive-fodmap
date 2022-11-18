package uk.sky.fodmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import uk.sky.fodmap.dto.FodmapItem;
import uk.sky.fodmap.repository.FodmapItemRepository;

@Service
public class FodmapItemService {

    @Autowired
    private FodmapItemRepository fodmapItemRepository;

    public Flux<FodmapItem> getItemsByGroup(String groupName) {
        return Flux.fromIterable(fodmapItemRepository.findByFoodGroupIgnoreCase(groupName.toLowerCase()));
    }
}
