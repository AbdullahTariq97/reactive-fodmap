package uk.sky.fodmap.functional.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import io.cucumber.guice.CucumberModules;
import io.cucumber.guice.InjectorSource;

public class FunctionalTestInjectorSource implements InjectorSource {

    /**
     * The CucumberModules.createScenarioModule() provides a module with bindings for cucumber classes annotated with @ScenarioScoped
     */
    @Override
    public Injector getInjector() {
        return Guice.createInjector(Stage.PRODUCTION, CucumberModules.createScenarioModule(), new FunctionalTestModule(), new HttpClientModule());
    }
}
