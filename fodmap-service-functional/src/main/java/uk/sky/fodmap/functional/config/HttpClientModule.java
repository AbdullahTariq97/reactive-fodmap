package uk.sky.fodmap.functional.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientModule extends AbstractModule {

    @Provides
    public CloseableHttpClient webClient(){
        return HttpClients.createDefault();
    }
}
