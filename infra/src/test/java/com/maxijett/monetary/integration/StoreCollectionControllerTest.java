package com.maxijett.monetary.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.store.model.StoreCollection;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@IT
@Sql(scripts = "classpath:sql/store-collection.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StoreCollectionControllerTest extends AbstractIT {

  @Test
  void getStoreCollectionById(){

    //Given
    Long id = 200L;

    //When
    ResponseEntity<StoreCollection> response = testRestTemplate.exchange("/api/v1/store-collection/by-id/" + id, HttpMethod.GET,
        new HttpEntity<>(null, null), new ParameterizedTypeReference<StoreCollection>() {});

    //Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

}
