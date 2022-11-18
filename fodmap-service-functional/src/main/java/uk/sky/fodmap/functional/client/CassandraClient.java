package uk.sky.fodmap.functional.client;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.google.inject.Inject;
import uk.sky.fodmap.functional.cassandra.EmbeddedCassandra;

public class CassandraClient {

    private final Session session;

    @Inject
    public CassandraClient(EmbeddedCassandra embeddedCassandra){
        session = embeddedCassandra.getSession();
    }

    public ResultSet insertFodmapItem(String columns, String values){
        return session.execute(String.format("INSERT INTO fodmap.food_item (%s) VALUES(%s);", columns, values));
    }
}
