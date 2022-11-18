package uk.sky.fodmap.functional;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "html:reports/report.html",
        glue = "uk.sky.fodmap.functional.glue",
        features = "src/main/resources/features"
        )
public class CucumberTestRunner {

}
