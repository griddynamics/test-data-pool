package com.griddynamics.qa.datapool.fetchers;

import org.springframework.http.*;
import org.springframework.test.web.client.MockRestServiceServer;
import org.testng.annotations.Test;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.testng.Assert.*;

public class SimpleRestFetcherTest extends AbstractFetcherTest {

    @Test
    public void testRun_String() throws Exception {
        final String url = "http://localhost:3211/api/v120/b15";
        final String response = "{name:K, lname: asdfK}";

        SimpleRestFetcher<String> restFetcher = new SimpleRestFetcher<>(buildStandardConfig(url, String.class));
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restFetcher.getRestTemplate());

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.TEXT_PLAIN));

        ResponseEntity<String> actualResult = restFetcher.run();

        assertResponse(response, actualResult);

        mockServer.verify();
    }

    @Test
    public void testRun_Entity() throws Exception {
        final String url = "http://localhost:3211/entity/v144/n12";
        final String response = "{\"name\":\"Im\", \"value\":\"144\"}";
        final TestEntity entity = new TestEntity("Im", 144);

        SimpleRestFetcher<TestEntity> restFetcher = new SimpleRestFetcher<>(buildStandardConfig(url, TestEntity.class));
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restFetcher.getRestTemplate());

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        ResponseEntity<TestEntity> actualResult = restFetcher.run();

        assertResponse(entity, actualResult);

        mockServer.verify();
    }

    private <T> void assertResponse(T entity, ResponseEntity<T> actualResult) {
        assertNotNull(actualResult);
        assertEquals(actualResult.getStatusCode(), HttpStatus.OK);
        assertNotNull(actualResult.getBody());
        assertEquals(actualResult.getBody(), entity);
    }
}