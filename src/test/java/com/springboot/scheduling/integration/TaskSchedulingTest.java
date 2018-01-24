package com.springboot.scheduling.integration;

import com.springboot.jooq.tables.pojos.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TaskSchedulingTest {

    private final String BASE_URI = "http://localhost:8080/api/";

    private TestRestTemplate testRestTemplate;

    @Before
    public void init() {
        this.testRestTemplate = new TestRestTemplate();
    }

    @Test
    public void checkForRegisteredTasksTest()
    {
        HttpEntity requestEntity = new HttpEntity<>(Void.class);
        ParameterizedTypeReference<List<Task>> typeReference = new ParameterizedTypeReference<List<Task>>() {};

        ResponseEntity<List<Task>> responseEntity = testRestTemplate.exchange(
                BASE_URI + "/task", HttpMethod.GET, requestEntity, typeReference
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(responseEntity.getBody().get(0).getId());
        assertThat(responseEntity.getBody().get(0).getExecuted()).isEqualTo(true);
    }
}
