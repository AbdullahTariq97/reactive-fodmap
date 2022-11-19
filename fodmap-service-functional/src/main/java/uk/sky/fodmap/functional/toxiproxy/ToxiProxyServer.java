package uk.sky.fodmap.functional.toxiproxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ToxiProxyServer {

    private Process process;

    public ToxiProxyServer() {
        start();
    }

    public void start() {
        log.info("Starting Toxiproxy");
        try {
            process = new ProcessBuilder()
                    .command("/usr/local/Cellar/toxiproxy/2.5.0/bin/toxiproxy-server")
                    .inheritIO()
                    .start();
        } catch (Exception e) {
            log.error("Failed to start local Toxiproxy", e);
            System.exit(1);
        }
        log.info("Started Toxiproxy");
    }

    public void stop() {
        if (process != null) {
            log.info("Stopping Toxiproxy");
            process.destroy();
            log.info("Stopped Toxiproxy");
        } else {
            log.info("Toxiproxy not running");
        }
    }
}
