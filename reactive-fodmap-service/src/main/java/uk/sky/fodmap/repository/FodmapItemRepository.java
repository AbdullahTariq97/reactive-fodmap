package uk.sky.fodmap.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import uk.sky.fodmap.dto.FodmapItem;

import java.util.List;

@Repository
public interface FodmapItemRepository extends CassandraRepository<FodmapItem, String> {

    @Query("SELECT * FROM fodmap.food_item WHERE food_group = ?0")
    List<FodmapItem> findByFoodGroupIgnoreCase(String foodGroup);
}
