package uk.sky.fodmap.functional.glue;

import com.google.inject.Inject;
import io.cucumber.datatable.DataTable;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.Given;
import uk.sky.fodmap.functional.client.CassandraClient;

import java.util.Collection;

@ScenarioScoped
public class CassandraStepDefinitions {

    private final CassandraClient cassandraClient;

    @Inject
    public CassandraStepDefinitions(CassandraClient cassandraClient) {
        this.cassandraClient = cassandraClient;
    }

    @Given("the database is populated with a record")
    public void data_is_populated_with_a_record(DataTable dataTable) {
        dataTable.asMaps(String.class, String.class).forEach(map -> {
            String columns = parseColumnsOrValueForCassandraQuery(map.keySet());
            String values = parseColumnsOrValueForCassandraQuery(map.values());
            cassandraClient.insertFodmapItem(columns, values);
        });
    }

    public String parseColumnsOrValueForCassandraQuery(Collection<Object> collection){
        return collection.toString().toLowerCase().substring(1, collection.toString().length() - 1);
    }

}
