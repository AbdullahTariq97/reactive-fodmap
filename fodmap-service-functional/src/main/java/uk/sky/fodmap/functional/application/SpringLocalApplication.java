package uk.sky.fodmap.functional.application;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import uk.sky.fodmap.ReactiveFodmapApplication;

public class SpringLocalApplication {

    private ConfigurableApplicationContext configurableApplicationContext;

    public SpringLocalApplication(){
        configurableApplicationContext = SpringApplication.run(ReactiveFodmapApplication.class);
    }
}
