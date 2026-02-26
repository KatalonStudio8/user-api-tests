package com.example.api.cucumbersteps;

import com.example.api.utils.RestConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class UserSteps {

    private RestTemplate restTemplate;
    private ResponseEntity<String> response;
    private ObjectMapper objectMapper;

    @Given("the API is available")
    public void theApiIsAvailable() {
        restTemplate = RestConfig.getRestTemplate();
        objectMapper = new ObjectMapper();
    }

    @When("I request user with ID {int}")
    public void iRequestUserWithId(int userId) {
        String url = RestConfig.getBaseUrl() + "/users/" + userId;
        response = restTemplate.getForEntity(url, String.class);
    }

    @When("I create a user using {string}")
    public void iCreateAUserUsing(String jsonFile) throws IOException {
        String url = RestConfig.getBaseUrl() + "/users";
        
        // Lire le fichier JSON depuis les ressources
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("data/" + jsonFile);
        assertNotNull("Le fichier " + jsonFile + " n'existe pas", inputStream);
        
        String jsonBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        
        // Configurer les headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        
        // Effectuer l'appel POST
        response = restTemplate.postForEntity(url, request, String.class);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatusCode().value());
    }


    @Then("the user fields should not be null")
    public void theUserFieldsShouldNotBeNull() throws IOException {
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        
        assertNotNull("id should not be null", jsonNode.get("id"));
        assertNotNull("email should not be null", jsonNode.get("email"));
        assertNotNull("username should not be null", jsonNode.get("username"));
        assertNotNull("name should not be null", jsonNode.get("name"));
    }

    @Then("the response should contain an id")
    public void theResponseShouldContainAnId() throws IOException {
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        assertNotNull("Response should contain an id", jsonNode.get("id"));
    }
}
