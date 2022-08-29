package com.maxijett.monetary.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.entity.DriverPaymentTransactionEntity;
import com.maxijett.monetary.adapters.cashbox.rest.jpa.repository.DriverPaymentTransactionRepository;
import com.maxijett.monetary.driver.model.DriverCash;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.model.enumeration.DriverEventType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@IT
@Sql(scripts = "classpath:sql/driver-cash.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DriverCashControllerTest extends AbstractIT {

    @Autowired
    DriverPaymentTransactionRepository  driverPaymentTransactionRepository;


  @Test
  public void getInstantCashesGraterThanZeroByGroupId(){

    Long groupId = 20L;

    ResponseEntity<List<DriverCash>> response = testRestTemplate.exchange("/api/v1/driver-cash/instant-list?groupId="+ groupId + "&clientId=",
        HttpMethod.GET,
        new HttpEntity<>(null, null), new ParameterizedTypeReference<List<DriverCash>>() {
    });

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());

    List<DriverCash> responseList = response.getBody();


    assertThat(responseList).isNotNull().hasSize(4)
        .extracting("driverId", "groupId", "cash")
        .containsExactlyInAnyOrder(
            tuple(1L, 20L, BigDecimal.valueOf(100.12)),
            tuple(12L, 20L, BigDecimal.valueOf(55.25)),
            tuple(13L, 20L, BigDecimal.valueOf(66.32)),
            tuple(14L, 20L, BigDecimal.valueOf(34.65))
        );

  }

  @Test
  public void getInstantCashesGraterThanZeroByClientId() {

    Long clientId = 20000L;

    ResponseEntity<List<DriverCash>> response = testRestTemplate.exchange(
        "/api/v1/driver-cash/instant-list?groupId=&clientId=" + clientId,
        HttpMethod.GET,
        new HttpEntity<>(null, null), new ParameterizedTypeReference<List<DriverCash>>() {
        });

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());

    List<DriverCash> responseList = response.getBody();

    assertThat(responseList).isNotNull().hasSize(4)
        .extracting("driverId", "clientId", "cash")
        .containsExactlyInAnyOrder(
            tuple(1L, 20000L, BigDecimal.valueOf(100.12)),
            tuple(12L, 20000L, BigDecimal.valueOf(55.25)),
            tuple(13L, 20000L, BigDecimal.valueOf(66.32)),
            tuple(14L, 20000L, BigDecimal.valueOf(34.65))
        );

  }

  @Test
  public void getDriverCashAndPrepaidAmountByDriverIdAndGroupId() {
    //Given
    Long driverId = 1L;
    Long groupId = 20L;

    //When
    ResponseEntity<DriverCash> response = testRestTemplate.exchange(
        "/api/v1/driver-cash/" + driverId + "/amount?groupId=" + groupId,
        HttpMethod.GET, new HttpEntity<>(groupId, null),
        new ParameterizedTypeReference<DriverCash>() {
        });

    //Then
    DriverCash actualResponse = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(driverId, actualResponse.getDriverId());
    assertEquals(groupId, actualResponse.getGroupId());
    assertEquals(20000L, actualResponse.getClientId());
    assertEquals(new BigDecimal("100.12"), actualResponse.getCash());
    assertEquals(new BigDecimal("50.00"), actualResponse.getPrepaidCollectionCash());

  }


    @Test
    public void getDriverCollectedCashWithGroup() {

        //Given
        Long driverId = 1L;
        Long groupId = 20L;

        createDriverPaymentTransactionRecord(driverId, groupId, new BigDecimal("45.25"), DriverEventType.PACKAGE_DELIVERED);
        createDriverPaymentTransactionRecord(driverId, groupId, new BigDecimal("40.50"), DriverEventType.PACKAGE_DELIVERED);
        createDriverPaymentTransactionRecord(driverId, groupId, new BigDecimal("100.00"), DriverEventType.SUPPORT_ACCEPTED);
        createDriverPaymentTransactionRecord(driverId, groupId, new BigDecimal("98.00"), DriverEventType.ADMIN_GET_PAID);

        //When
        ResponseEntity<List<DriverPaymentTransaction>> response = testRestTemplate.exchange(
                "/api/v1/driver-cash/" + driverId + "/collected?groupId=" + groupId,
                HttpMethod.GET, new HttpEntity<>(groupId, null),
                new ParameterizedTypeReference<List<DriverPaymentTransaction>>() {
                });

        List<DriverPaymentTransaction> responseTransactions = response.getBody();


        assertThat(responseTransactions).isNotNull().hasSize(3)
                .extracting("driverId", "groupId", "paymentCash")
                .containsExactlyInAnyOrder(
                        tuple(1L, 20L, new BigDecimal("45.25")),
                        tuple(1L, 20L, new BigDecimal("40.50")),
                        tuple(1L, 20L, new BigDecimal("100.00"))
                );


    }

    private void createDriverPaymentTransactionRecord(Long driverId, Long groupId, BigDecimal cashAmount, DriverEventType eventType) {
        DriverPaymentTransactionEntity entity = new DriverPaymentTransactionEntity();
        entity.setDriverId(driverId);
        entity.setOrderNumber(RandomStringUtils.random(10));
        entity.setGroupId(groupId);
        entity.setDateTime(ZonedDateTime.now());
        entity.setEventType(eventType);
        entity.setPaymentCash(cashAmount);

        driverPaymentTransactionRepository.save(entity);
    }
}
