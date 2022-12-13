package uk.sky.fodmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.CassandraConnectionFailureException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import uk.sky.fodmap.dto.FodmapItem;
import uk.sky.fodmap.exception.DownstreamFailureException;
import uk.sky.fodmap.repository.FodmapItemRepository;

import java.util.List;

@Service
public class FodmapItemService {

    @Autowired
    private FodmapItemRepository fodmapItemRepository;

    public Flux<FodmapItem> getItemsByGroup(String groupName) {
        return Flux.fromIterable(getByFoodGroupIgnoreCase(groupName));
    }

    private List<FodmapItem> getByFoodGroupIgnoreCase(String groupName) {
        try {
            return fodmapItemRepository.findByFoodGroupIgnoreCase(groupName.toLowerCase());
        } catch (CassandraConnectionFailureException exception) {
            throw new DownstreamFailureException("downstream failure : database", exception.getCause());
        }
    }
}
