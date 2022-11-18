package uk.sky.fodmap.functional.glue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.inject.Inject;
import io.cucumber.datatable.DataTable;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import uk.sky.fodmap.dto.FodmapItem;
import uk.sky.fodmap.functional.client.FodmapServiceClient;
import uk.sky.fodmap.functional.dto.Response;
import uk.sky.fodmap.functional.util.FoodGroups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@ScenarioScoped
public class FodmapServiceSteps {


    private final FodmapServiceClient fodmapServiceClient;
    private Response response;

    @Inject
    public FodmapServiceSteps(FodmapServiceClient fodmapServiceClient){
        this.fodmapServiceClient = fodmapServiceClient;
    }

    @When("^a request is made to get fodmap items by food group (.+)$")
    public void makeRequestToFodmapService(FoodGroups foodGroup){
        response = fodmapServiceClient.getItemsByFoodGroup(foodGroup);
    }

    @Then("^status code of (\\d+) should be returned$")
    public void statusCodeOfShouldBeReturned(int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatus());
    }

    @And("the response body contains the following fodmap items")
    public void theResponseBodyContainsTheFollowingFodmapItems(DataTable dataTable) {
        System.out.println("The response body contains " + response.getBody());

        StringBuilder sb = new StringBuilder();

        String jsonString = dataTable.asMaps(String.class, String.class).stream().
                findFirst()
                .map(mapIn -> {
                    sb.append("\"name\":" + "\"" + mapIn.get("name") + "\"");
                    sb.append(",");
                    sb.append("\"food_group\":" + "\"" + mapIn.get("food_group") + "\"");
                    sb.append(",");
                    sb.append("\"overall_rating\":" + "\"" + mapIn.get("overall_rating") + "\"");
                    sb.append(",");
                    sb.append("\"stratified_rating\":" + mapIn.get("stratified_rating") );

                    return String.format("{%s}", sb.toString());
                }).get();


        ObjectMapper om = new ObjectMapper();
        try {
            FodmapItem fodmapItem = om.readValue(jsonString, FodmapItem.class);
            System.out.println("fodmap item" + fodmapItem.getStratifiedRating().getFructan());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}