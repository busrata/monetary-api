package com.maxijett.monetary.integration;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.driver.model.DriverCash;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "classpath:sql/driver-cash.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DriverCashControllerTest extends AbstractIT {

  @Test
  public void getDriverCashAndPrepaidAmountByDriverIdAndGroupId() {
    //Given
    Long driverId = 1L;
    Long groupId = 20L;

    //When
    ResponseEntity<DriverCash> response = testRestTemplate.exchange(
        "/api/v1/driver/" + driverId + "/amount?groupId=" + groupId,
        HttpMethod.GET, new HttpEntity<>(groupId, null),
        new ParameterizedTypeReference<DriverCash>() {
        });

    //Then
    DriverCash actualResponse = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(driverId, actualResponse.getDriverId());
    assertEquals(groupId, actualResponse.getGroupId());
    assertEquals(20000L, actualResponse.getClientId());
    assertEquals(new BigDecimal("100.00"), actualResponse.getCash());
    assertEquals(new BigDecimal("50.00"), actualResponse.getPrepaidCollectionCash());

  }
}
