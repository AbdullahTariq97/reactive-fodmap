package uk.sky.fodmap.functional.util;

import uk.sky.fodmap.functional.application.SpringLocalApplication;
import uk.sky.fodmap.functional.toxiproxy.ToxiProxyServer;

import javax.inject.Inject;

public class ShutdownHooks {

    @Inject
    public ShutdownHooks(ToxiProxyServer toxiProxyServer, SpringLocalApplication springLocalApplication) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            toxiProxyServer.stop();
            springLocalApplication.stop();
        }));
    }
}
