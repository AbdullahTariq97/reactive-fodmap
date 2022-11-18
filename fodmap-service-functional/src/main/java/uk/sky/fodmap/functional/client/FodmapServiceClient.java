package uk.sky.fodmap.functional.client;

import com.google.inject.Inject;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.UriComponentsBuilder;
import uk.sky.fodmap.functional.dto.Response;
import uk.sky.fodmap.functional.util.FoodGroups;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

public class FodmapServiceClient {

    private final CloseableHttpClient closeableHttpClient;

    @Inject
    public FodmapServiceClient(CloseableHttpClient closeableHttpClient){
        this.closeableHttpClient = closeableHttpClient;
    }

    public Response getItemsByFoodGroup(FoodGroups foodGroup) {
        URI uri = UriComponentsBuilder.fromPath("/items").queryParam("group", foodGroup.toString().toLowerCase()).build().toUri();
        HttpUriRequest request = RequestBuilder.create("GET").setUri(uri).build();
        return useHttpClient(request);
    }

    public Response useHttpClient(HttpUriRequest request){
        try(closeableHttpClient) {
            try {
                return mapToResponse(closeableHttpClient.execute(HttpHost.create("localhost:8080"),request));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response mapToResponse(HttpResponse httpResponse) throws IOException {
        String responseJsonString = httpResponse.getEntity() != null ? EntityUtils.toString(httpResponse.getEntity()) : "";
        Header[] allHeaders = httpResponse.getAllHeaders();
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        return new Response(statusCode, responseJsonString, Arrays.asList(allHeaders));
    }
}
