package uk.sky.fodmap.dto;

import com.datastax.driver.core.DataType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Map;
import java.util.Objects;

@Table("food_item")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FodmapItem {

    @JsonProperty("food_group")
    @PrimaryKeyColumn(name = "food_group",ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String foodGroup;

    @PrimaryKeyColumn(name = "name", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String name;

    @JsonProperty("overall_rating")
    @Column("overall_rating")
    private String overallRating;

    @JsonProperty("stratified_rating")
    @Column("stratified_rating")
//    @CassandraType(type = DataType.Name.UDT, userTypeName = "stratified_rating" )
    private StratifiedData stratifiedRating;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FodmapItem that = (FodmapItem) o;
        return Objects.equals(foodGroup, that.foodGroup) && Objects.equals(name, that.name) && Objects.equals(overallRating, that.overallRating) && Objects.equals(stratifiedRating, that.stratifiedRating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodGroup, name, overallRating, stratifiedRating);
    }

    @Override
    public String toString() {
        return "FodmapItem{" +
                "foodGroup='" + foodGroup + '\'' +
                ", name='" + name + '\'' +
                ", overallRating='" + overallRating + '\'' +
                ", stratifiedRating=" + stratifiedRating +
                '}';
    }
}
