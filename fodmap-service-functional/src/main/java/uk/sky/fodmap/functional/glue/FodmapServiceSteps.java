package uk.sky.fodmap.functional.glue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import io.cucumber.datatable.DataTable;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import uk.sky.fodmap.dto.FodmapItem;
import uk.sky.fodmap.functional.client.FodmapServiceClient;
import uk.sky.fodmap.functional.dto.Response;
import uk.sky.fodmap.functional.util.FoodGroups;

import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@ScenarioScoped
public class FodmapServiceSteps {


    private static final ObjectMapper om = new ObjectMapper();
    private final FodmapServiceClient fodmapServiceClient;
    private Response response;

    @Inject
    public FodmapServiceSteps(FodmapServiceClient fodmapServiceClient) {
        this.fodmapServiceClient = fodmapServiceClient;
    }

    @When("^a request is made to get fodmap items by food group (.+)$")
    public void makeRequestToFodmapService(FoodGroups foodGroup) {
        response = fodmapServiceClient.getItemsByFoodGroup(foodGroup);
    }

    @Then("^status code of (\\d+) should be returned$")
    public void statusCodeOfShouldBeReturned(int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatus());
    }

    @And("the response body contains the following fodmap items")
    public void theResponseBodyContainsTheFollowingFodmapItems(DataTable dataTable) throws JsonProcessingException {
        String body = response.getBody();
        String substring = body.substring(1, body.length() - 1);
        FodmapItem actualFodmapItem = om.readValue(substring, FodmapItem.class);
        FodmapItem expectedFodmapItem = convertToFodmapItem(dataTable);
        assertEquals(actualFodmapItem, expectedFodmapItem);
    }

    @And("the response body contains an empty list")
    public void theResponseBodyContainsAnEmptyList() {
        assertEquals("[]", response.getBody());
    }

    @Given("no fodmap items are persisted in the database for PULSES_TOFU_AND_NUTS")
    public void noFodmapItemsArePersistedInTheDatabaseForPULSES_TOFU_AND_NUTS() {
    }

    @And("^the response body contains the error message (.+)$")
    public void theResponseBodyContainsTheErrorMessageDownstreamFailureDatabase(String expectedErrorMessage) throws JsonProcessingException {
        System.out.println("The body contains" + response.getBody());
    }

    private FodmapItem convertToFodmapItem(DataTable dataTable) throws JsonProcessingException {
        String jsonString = dataTable.asMaps(String.class, String.class).stream().
                findFirst()
                .map(mapIn -> mapIn
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(entry -> String.format("\"%s\"", entry.getKey()), Map.Entry::getValue))
                        .toString().strip().replace(" ", "").replace("=", ":")).get();
        return om.readValue(jsonString, FodmapItem.class);
    }
}

