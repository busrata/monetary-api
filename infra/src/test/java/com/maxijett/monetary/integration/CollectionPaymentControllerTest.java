package com.maxijett.monetary.integration;


import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.collectionpayment.rest.dto.CollectionPaymentDTO;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@IT
@Sql(scripts = "classpath:sql/driver-cash.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/collection-payment.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/store-collection.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CollectionPaymentControllerTest extends AbstractIT {

    @Test
    public void saveCollectionPaymentByDriver() {

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

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(collectionPaymentDTO.getCash(), collectionPayment.getCash());
        assertEquals(collectionPaymentDTO.getDriverId(), collectionPayment.getDriverId());
        assertEquals(collectionPaymentDTO.getStoreId(), collectionPayment.getStoreId());
        assertEquals(collectionPaymentDTO.getGroupId(), collectionPayment.getGroupId());
        assertNotNull(collectionPayment.getId());

    }

    @Test
    public void saveCollectionPaymentByStoreChainAdmin() {

        //Given
        CollectionPaymentDTO collectionPaymentDTO = CollectionPaymentDTO.builder()
                .id(101L)
                .clientId(20000L)
                .groupId(20L)
                .storeId(200L)
                .pos(BigDecimal.valueOf(84))
                .cash(BigDecimal.ZERO)
                .date(ZonedDateTime.now())
                .build();

        //When
        ResponseEntity<CollectionPayment> response = testRestTemplate.exchange("/api/v1/collection-payment/by-store-chain-admin",
                HttpMethod.POST, new HttpEntity<>(collectionPaymentDTO, null), new ParameterizedTypeReference<CollectionPayment>() {
                });

        //Then
        CollectionPayment collectionPayment = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(collectionPaymentDTO.getPos(), collectionPayment.getPos());
        assertEquals(BigDecimal.ZERO, collectionPayment.getCash());
        assertEquals(collectionPaymentDTO.getStoreId(), collectionPayment.getStoreId());
        assertEquals(collectionPaymentDTO.getGroupId(), collectionPayment.getGroupId());
        assertNotNull(collectionPayment.getId());

    }

    @Test
    public void retrieveDriverCollectionPaymentPaidToStore() {
        //Given
        Long driverId = 1L;
        Long groupId = 20L;
        String startDate = LocalDate.now().toString();
        String endDate = LocalDate.now().toString();

        createCollectionPaymentRecord(driverId, 23L, 20000L, groupId, BigDecimal.valueOf(34.00), BigDecimal.ZERO, ZonedDateTime.now());
        createCollectionPaymentRecord(driverId, 24L, 20000L, groupId, BigDecimal.valueOf(75.00), BigDecimal.ZERO, ZonedDateTime.now());
        createCollectionPaymentRecord(driverId, 25L, 20000L, groupId, BigDecimal.valueOf(134.00), BigDecimal.ZERO, ZonedDateTime.now());
        createCollectionPaymentRecord(driverId, 25L, 20000L, groupId, BigDecimal.valueOf(134.00), BigDecimal.ZERO, ZonedDateTime.now().minusDays(1));
        createCollectionPaymentRecord(driverId, 25L, 20000L, groupId, BigDecimal.valueOf(134.00), BigDecimal.ZERO, ZonedDateTime.now().plusDays(1));


        //When
        ResponseEntity<List<CollectionPayment>> response =
                testRestTemplate.exchange("/api/v1/collection-payment/by-driver/{driverId}?groupId={groupId}&startDate={startDate}&endDate={endDate}",
                        HttpMethod.GET, new HttpEntity<>(null, null), new ParameterizedTypeReference<>() {
                        }, driverId, groupId, startDate, endDate);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertThat(response.getBody()).isNotNull().hasSize(3)
                .extracting("driverId", "groupId", "storeId", "cash", "pos", "clientId")
                .containsExactlyInAnyOrder(
                        tuple(1L, 20L, 23L, new BigDecimal("34.00"), new BigDecimal("0.00"), 20000L),
                        tuple(1L, 20L, 24L, new BigDecimal("75.00"), new BigDecimal("0.00"), 20000L),
                        tuple(1L, 20L, 25L, new BigDecimal("134.00"), new BigDecimal("0.00"), 20000L)
                );
    }

    @Test
    public void deleteCollectionPaymentByStoreChainAdmin() {
        //Given
        Long id = 100L;

        //When
        ResponseEntity<CollectionPayment> response = testRestTemplate.exchange("/api/v1/collection-payment/delete?id=" + id,
                HttpMethod.PUT, new HttpEntity<>(null, null), new ParameterizedTypeReference<CollectionPayment>() {
                });

        //Then
        CollectionPayment collectionPayment = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, collectionPayment.getId());
    }

    @Test
    public void getAllCollectionPaymentByGroupIdAndDate() {
        //Given
        Long groupId = 20L;
        String requestDate = "2022-09-02T12:00:00.000+03:00";


        //When
        ResponseEntity<List<CollectionPayment>> response = testRestTemplate.exchange(
                "/api/v1/collection-payment/all?groupId={groupId}&requestDate={requestDate}",
                HttpMethod.GET, new HttpEntity<>(null, null),
                new ParameterizedTypeReference<List<CollectionPayment>>() {
                }, groupId, requestDate
        );

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<CollectionPayment> body = response.getBody();
        assertThat(body).isNotNull().hasSize(1)
                .extracting("pos", "groupId")
                .containsExactlyInAnyOrder(
                        tuple(BigDecimal.valueOf(50.05), groupId)
                );

        assertThat(body.get(0).getCreateOn().toString().contains("2022-09-02"));

    }

    @Test
    public void retrieveCollectionPaymentMonthlyByStore() {
        //Given
        Long storeId = 25L;
        Long driverId = 315L;
        Long groupId = 20L;
        String requestDate = LocalDate.now().toString();

        createCollectionPaymentRecord(driverId, 23L, 20000L, groupId, BigDecimal.valueOf(34.00), BigDecimal.ZERO, ZonedDateTime.now());
        createCollectionPaymentRecord(driverId, 24L, 20000L, groupId, BigDecimal.valueOf(75.00), BigDecimal.ZERO, ZonedDateTime.now());
        createCollectionPaymentRecord(driverId, 25L, 20000L, groupId, BigDecimal.valueOf(34.00), BigDecimal.ZERO, ZonedDateTime.now());
        createCollectionPaymentRecord(driverId, 25L, 20000L, groupId, BigDecimal.valueOf(75.00), BigDecimal.ZERO, ZonedDateTime.now().minusMonths(2));
        createCollectionPaymentRecord(driverId, 25L, 20000L, groupId, BigDecimal.valueOf(134.00), BigDecimal.ZERO, ZonedDateTime.now());
        createCollectionPaymentRecord(driverId, 25L, 20000L, groupId, BigDecimal.valueOf(234.00), BigDecimal.ZERO, ZonedDateTime.now().minusYears(2));


        //When
        ResponseEntity<List<CollectionPayment>> response =
                testRestTemplate.exchange("/api/v1/collection-payment/by-store/{storeId}/monthly?requestDate={requestDate}",
                        HttpMethod.GET, new HttpEntity<>(null, null), new ParameterizedTypeReference<>() {
                        }, storeId, requestDate);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertThat(response.getBody()).isNotNull().hasSize(2)
                .extracting("driverId", "groupId", "storeId", "cash", "pos", "clientId")
                .containsExactlyInAnyOrder(
                        tuple(315L, 20L, 25L, new BigDecimal("34.00"), new BigDecimal("0.00"), 20000L),
                        tuple(315L, 20L, 25L, new BigDecimal("134.00"), new BigDecimal("0.00"), 20000L)
                );

    }
}
