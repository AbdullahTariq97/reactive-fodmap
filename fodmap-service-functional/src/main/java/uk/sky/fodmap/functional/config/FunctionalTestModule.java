package uk.sky.fodmap.functional.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import uk.sky.fodmap.functional.application.SpringLocalApplication;
import uk.sky.fodmap.functional.cassandra.EmbeddedCassandra;
import uk.sky.fodmap.functional.client.CassandraClient;
import uk.sky.fodmap.functional.client.FodmapServiceClient;
import uk.sky.fodmap.functional.toxiproxy.ToxiProxyServer;
import uk.sky.fodmap.functional.toxiproxy.ToxiProxyService;
import uk.sky.fodmap.functional.util.ShutdownHooks;

public class FunctionalTestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EmbeddedCassandra.class).toInstance(EmbeddedCassandra.start());
        bind(ToxiProxyServer.class).toInstance(new ToxiProxyServer());
        bind(ToxiproxyClient.class).toInstance(new ToxiproxyClient());
        bind(CassandraClient.class).in(Singleton.class);
        bind(ToxiProxyService.class).in(Singleton.class);
        bind(SpringLocalApplication.class).in(Singleton.class);
        bind(FodmapServiceClient.class).in(Singleton.class);
        bind(ShutdownHooks.class).in(Singleton.class);
    }
}
