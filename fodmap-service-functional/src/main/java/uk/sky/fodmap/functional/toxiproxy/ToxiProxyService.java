package uk.sky.fodmap.functional.toxiproxy;

import eu.rekawek.toxiproxy.ToxiproxyClient;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;

import static eu.rekawek.toxiproxy.model.ToxicDirection.DOWNSTREAM;

@Slf4j
public class ToxiProxyService {

    private static final int CASSANDRA_LISTENING_PORT = 9143;
    private static final int CASSANDRA_FORWARDING_PORT = 9142;
    private ToxiproxyClient toxiproxyClient;

    @Inject
    public ToxiProxyService(ToxiproxyClient toxiproxyClient) {
        this.toxiproxyClient = toxiproxyClient;
        createCassandraProxy();
    }

    private void createCassandraProxy() {
        System.out.println("creating cassandra proxy");
        try {
            if (toxiproxyClient.getProxyOrNull("cassandra-proxy") == null) {
                do {
                    toxiproxyClient.createProxy("cassandra-proxy", "localhost:" + CASSANDRA_LISTENING_PORT, "localhost:" + CASSANDRA_FORWARDING_PORT);
                } while (toxiproxyClient.getProxyOrNull("cassandra-proxy") == null);
            } else {
                log.info("The cassandra proxy already exists");
            }
        } catch (IOException e) {
            log.error("error creating proxy for cassandra", e);
            System.exit(1);
        }
    }

    public void addTimeoutToxic(String proxyName) {
        try {
            toxiproxyClient.getProxy(proxyName).toxics().timeout("cassandra-timeout", DOWNSTREAM, 1000);
        } catch (Exception e) {
            log.error("failed to create toxic for cassandra", e);
            System.exit(1);
        }
    }
}
