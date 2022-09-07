package com.maxijett.monetary.integration;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.collectionreport.model.DriverDailyBonus;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@IT
@Sql(scripts = "classpath:sql/collection-report.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CollectionReportControllerTest extends AbstractIT {


  @Test
  public void getDailyBonus(){

    //Given
    LocalDate dateTime = LocalDate.now();

    Long driverId = 1L;

    Boolean isRequestFromMobil= true;

    //When
    ResponseEntity<DriverDailyBonus> response = testRestTemplate.exchange("/api/v1/collection-report/daily-bonus/" + driverId + "?startDate=" + dateTime + "&endDate=" + dateTime+ "&isRequestFromMobil=" + isRequestFromMobil ,
        HttpMethod.GET, new HttpEntity<>(null, null),
        new ParameterizedTypeReference<DriverDailyBonus>(){});

    //Then
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
  }


}
