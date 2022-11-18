package uk.sky.fodmap.functional.cassandra;

import com.datastax.driver.core.AtomicMonotonicTimestampGenerator;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.apache.cassandra.service.StorageService;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;

import java.io.IOException;

public class EmbeddedCassandra {

    private static EmbeddedCassandra INSTANCE;
    private Session session;
    private Cluster cluster;
    private static final int CASSANDRA_PORT = 9142;

    private EmbeddedCassandra() {
        this.cluster = createCluster();
        this.session = cluster.connect();
    }

    /**
     * Main method used to spin up embedded cassandra independently of tests
     */
    public static void main(String[] args) {
        EmbeddedCassandra.start();
        createCluster();
    }

    public static EmbeddedCassandra start() {
        if (INSTANCE == null) {
            try {
                EmbeddedCassandraServerHelper.startEmbeddedCassandra();
            } catch (IOException | InterruptedException e) {
                System.out.println("The exception thrown was " + e);
                throw new RuntimeException(e);
            }
            StorageService.instance.removeShutdownHook();
            }
        INSTANCE = new EmbeddedCassandra();
        return INSTANCE;
    }

    private static Cluster createCluster() {
        Cluster cluster = Cluster.builder()
                .addContactPoints("localhost")
                .withTimestampGenerator(new AtomicMonotonicTimestampGenerator())
                .withPort(CASSANDRA_PORT)
                .withoutJMXReporting()
                .withClusterName("embedded-cassandra-cluster")
                .build();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Session session = cluster.connect();
        session.execute("CREATE KEYSPACE IF NOT EXISTS fodmap WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 }");
        session.execute("CREATE TYPE fodmap.stratified_rating(amount int, fructose text, lactose text, manitol text, sorbitol text, gos text, fructan text);");
        session.execute("CREATE TABLE fodmap.food_item (food_group text, name text, overall_rating text, stratified_rating stratified_rating, PRIMARY KEY (food_group,name));");
        return cluster;
    }

    public synchronized Session getSession() {
        if (session == null || session.isClosed()) {
            session = cluster.connect();
        }
        return session;
    }

    public int getPort() {
        return CASSANDRA_PORT;
    }
}
