package uk.sky.fodmap.functional.config;

import com.google.inject.AbstractModule;
import uk.sky.fodmap.functional.application.SpringLocalApplication;
import uk.sky.fodmap.functional.cassandra.EmbeddedCassandra;
import uk.sky.fodmap.functional.client.CassandraClient;
import uk.sky.fodmap.functional.client.FodmapServiceClient;
import uk.sky.fodmap.functional.toxiproxy.ToxiProxyServer;

public class FunctionalTestModule extends AbstractModule {
    @Override
    protected void configure() {
       bind(EmbeddedCassandra.class).toInstance(EmbeddedCassandra.start());
       bind(ToxiProxyServer.class).toInstance(new ToxiProxyServer());
       bind(SpringLocalApplication.class).toInstance(new SpringLocalApplication());
       bind(CassandraClient.class);
       bind(FodmapServiceClient.class);
    }
}
