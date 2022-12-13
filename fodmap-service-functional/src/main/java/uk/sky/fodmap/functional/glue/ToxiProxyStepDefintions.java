package uk.sky.fodmap.functional.glue;

import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.Given;
import uk.sky.fodmap.functional.toxiproxy.ToxiProxyService;

import javax.inject.Inject;

@ScenarioScoped
public class ToxiProxyStepDefintions {

    private final ToxiProxyService toxiProxyService;

    @Inject
    public ToxiProxyStepDefintions(ToxiProxyService toxiProxyService) {
        this.toxiProxyService = toxiProxyService;
    }

    @Given("the database is down")
    public void givenTheDatabaseIsDown() {
        toxiProxyService.addTimeoutToxic("cassandra-proxy");
    }
}
