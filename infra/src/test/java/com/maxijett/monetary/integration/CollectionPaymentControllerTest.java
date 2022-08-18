package com.maxijett.monetary.integration;


import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.collectionpayment.rest.dto.CollectionPaymentDTO;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.ZonedDateTime;


@IT
@Sql(scripts = "classpath:sql/driver-cash.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/store-collection.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CollectionPaymentControllerTest extends AbstractIT {

  @Test
  public void saveCollectionPaymentByDriver(){

    //Given
    CollectionPaymentDTO collectionPaymentDTO = CollectionPaymentDTO.builder()
            .id(101L)
            .clientId(20000L)
            .groupId(20L)
            .storeId(200L)
            .cash(BigDecimal.valueOf(84))
            .date(ZonedDateTime.now())
            .driverId(1L)
            .build();

    //When
    ResponseEntity<CollectionPayment> response = testRestTemplate.exchange("/api/v1/collection-payment/by-driver",
        HttpMethod.POST, new HttpEntity<>(collectionPaymentDTO, null), new ParameterizedTypeReference<CollectionPayment>() {
        });

    //Then
    CollectionPayment collectionPayment = response.getBody();

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(collectionPaymentDTO.getCash(), collectionPayment.getCash());
    Assertions.assertEquals(collectionPaymentDTO.getDriverId(), collectionPayment.getDriverId());
    Assertions.assertEquals(collectionPaymentDTO.getStoreId(), collectionPayment.getStoreId());
    Assertions.assertEquals(collectionPaymentDTO.getGroupId(), collectionPayment.getGroupId());
    Assertions.assertNotNull(collectionPayment.getId());

  }

}