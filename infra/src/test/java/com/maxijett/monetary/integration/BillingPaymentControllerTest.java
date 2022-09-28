package com.maxijett.monetary.integration;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.billingpayment.rest.dto.BillingPaymentDTO;
import com.maxijett.monetary.adapters.billingpayment.rest.dto.BillingPaymentDeleteDTO;
import com.maxijett.monetary.adapters.billingpayment.rest.dto.BillingPaymentPrePaidDTO;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentDelete;
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
@Sql(scripts = "classpath:sql/billing-payment.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cash-box.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/store-collection.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "classpath:sql/driver-cash.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BillingPaymentControllerTest extends AbstractIT {

    @Test
    public void createBillingPaymentWithPayloadTypeCollectionByStoreChainAdmin() {

        //Given
        BillingPaymentDTO billingPaymentDTO = BillingPaymentDTO.builder()
                .storeId(200L)
                .cash(BigDecimal.valueOf(40))
                .clientId(20L)
                .pos(BigDecimal.ZERO)
                .payloadType(PayloadType.COLLECTION)
                .payingAccount("storeChainAdmin")
                .createOn(ZonedDateTime.now())
                .groupId(21L)
                .build();

        //When
        ResponseEntity<BillingPayment> response = testRestTemplate.exchange("/api/v1/billing-payment/by-admin",
                HttpMethod.POST, new HttpEntity<>(billingPaymentDTO, null), new ParameterizedTypeReference<BillingPayment>() {
                });

        BillingPayment actualBilling = response.getBody();

        //Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(actualBilling.getId());
        assertEquals(billingPaymentDTO.getStoreId(), actualBilling.getStoreId());
        assertEquals(billingPaymentDTO.getCash(), actualBilling.getCash());
        assertEquals(billingPaymentDTO.getClientId(), actualBilling.getClientId());
        assertEquals(billingPaymentDTO.getPos(), actualBilling.getPos());
        assertEquals(billingPaymentDTO.getPayingAccount(), actualBilling.getPayingAccount());
        assertEquals(billingPaymentDTO.getPayloadType(), actualBilling.getPayloadType());

    }

    @Test
    public void createBillingPaymentWithPayloadTypeNettingByStoreChainAdmin() {

        //Given
        BillingPaymentDTO billingPaymentDTO = BillingPaymentDTO.builder()
                .storeId(200L)
                .cash(BigDecimal.valueOf(40))
                .clientId(20L)
                .pos(BigDecimal.ZERO)
                .payloadType(PayloadType.NETTING)
                .payingAccount("camlikChainAdmin")
                .createOn(ZonedDateTime.now())
                .groupId(20L)
                .build();

        //When
        ResponseEntity<BillingPayment> response = testRestTemplate.exchange("/api/v1/billing-payment/by-admin",
                HttpMethod.POST, new HttpEntity<>(billingPaymentDTO, null), new ParameterizedTypeReference<BillingPayment>() {
                });

        BillingPayment actualBilling = response.getBody();

        //Then

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(actualBilling.getId());
        assertEquals(billingPaymentDTO.getStoreId(), actualBilling.getStoreId());
        assertEquals(billingPaymentDTO.getPos(), actualBilling.getPos());
        assertEquals(billingPaymentDTO.getClientId(), actualBilling.getClientId());
        assertEquals(billingPaymentDTO.getCash(), actualBilling.getCash());
        assertEquals(billingPaymentDTO.getPayingAccount(), actualBilling.getPayingAccount());
        assertEquals(billingPaymentDTO.getPayloadType(), actualBilling.getPayloadType());
        assertEquals(false, actualBilling.getIsDeleted());
    }

    @Test
    public void deleteBillingPaymentWithPayloadTypeNettingByStoreChainAdmin() {

        //Given
        BillingPaymentDeleteDTO billingPaymentDeleteDTO = BillingPaymentDeleteDTO.builder()
                .id(35L)
                .payingAccount("storeChainAdmin")
                .payloadType(PayloadType.NETTING)
                .build();

        //When
        ResponseEntity<BillingPaymentDelete> response = testRestTemplate.exchange("/api/v1/billing-payment/delete", HttpMethod.POST, new HttpEntity<>(billingPaymentDeleteDTO, null), new ParameterizedTypeReference<BillingPaymentDelete>() {
        });

        BillingPaymentDelete actualBillingDelete = response.getBody();

        //Then
        assertEquals(billingPaymentDeleteDTO.getId(), actualBillingDelete.getId());
        assertEquals(billingPaymentDeleteDTO.getPayloadType(), actualBillingDelete.getPayloadType());
        assertEquals(billingPaymentDeleteDTO.getPayingAccount(), actualBillingDelete.getPayingAccount());
        assertEquals(true, actualBillingDelete.getIsDeleted());

    }

    @Test
    public void getPaidBillingPaymentFromColdStoreByDriver() {
        //Given
        BillingPaymentPrePaidDTO billingPaymentPrePaidDTO = BillingPaymentPrePaidDTO.builder()
                .driverId(1L)
                .pos(BigDecimal.ZERO)
                .payloadType(PayloadType.NETTING)
                .storeId(200L)
                .clientId(20000L)
                .prePaidBillingAmount(BigDecimal.valueOf(40))
                .groupId(20L)
                .createOn(ZonedDateTime.now())
                .build();

        //When
        ResponseEntity<BillingPayment> response = testRestTemplate.exchange("/api/v1/billing-payment/cold-store-by-driver",
                HttpMethod.POST, new HttpEntity<>(billingPaymentPrePaidDTO, null), new ParameterizedTypeReference<BillingPayment>() {
                });

        BillingPayment actualBilling = response.getBody();

        //Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(actualBilling.getId());
        assertEquals(billingPaymentPrePaidDTO.getStoreId(), actualBilling.getStoreId());
        assertEquals(billingPaymentPrePaidDTO.getPrePaidBillingAmount(), actualBilling.getCash());
        assertEquals(billingPaymentPrePaidDTO.getClientId(), actualBilling.getClientId());
        assertEquals(billingPaymentPrePaidDTO.getPayloadType(), actualBilling.getPayloadType());

    }

    @Test
    public void retrieveBillingPaymentsByDateAndGroupId() {

        //Given
        Long groupId = 20L;
        LocalDate requestDate = LocalDate.of(2022, 9, 2);

        //When
        ResponseEntity<List<BillingPayment>> response = testRestTemplate.exchange(
                "/api/v1/billing-payment/all?groupId={groupId}&requestDate={requestDate}",
                HttpMethod.GET, new HttpEntity<>(null, null),
                new ParameterizedTypeReference<List<BillingPayment>>() {
                }, groupId, requestDate
        );

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<BillingPayment> body = response.getBody();
        assertThat(body).isNotNull().hasSize(2)
                .extracting("cash", "pos", "groupId")
                .containsExactlyInAnyOrder(
                        tuple(BigDecimal.valueOf(50.05), new BigDecimal("0.00"), groupId),
                        tuple(new BigDecimal("0.00"), BigDecimal.valueOf(55.05), groupId)
                );
        assertThat(body.get(0).getCreateOn().toString().contains("2022-09-02"));
        assertThat(body.get(1).getCreateOn().toString().contains("2022-09-02"));

    }

    @Test
    void retrieveBillingPaymentMonthlyByStore() {
        //Given
        Long storeId = 222L;
        String requestDate = LocalDate.now().toString();

        createBillingPaymentRecord(222L, 20000L, 50L, BigDecimal.valueOf(55.05), ZonedDateTime.now(), "storeChainAdmin", PayloadType.NETTING, BigDecimal.ZERO);
        createBillingPaymentRecord(222L, 20000L, 50L, BigDecimal.valueOf(50.05), ZonedDateTime.now(), "storeChainAdmin", PayloadType.NETTING, BigDecimal.ZERO);
        createBillingPaymentRecord(222L, 20000L, 50L, BigDecimal.ZERO, ZonedDateTime.parse("2022-08-02T12:00:00.000Z"), "storeChainAdmin", PayloadType.NETTING, BigDecimal.TEN);
        createBillingPaymentRecord(333L, 20000L, 50L, BigDecimal.valueOf(55.05), ZonedDateTime.now(), "storeChainAdmin", PayloadType.NETTING, BigDecimal.ZERO);


        //When
        ResponseEntity<List<BillingPayment>> response = testRestTemplate.exchange("/api/v1/billing-payment/by-store/{storeId}/monthly?requestDate={requestDate}",
                HttpMethod.GET, new HttpEntity<>(null, null), new ParameterizedTypeReference<>() {
                }, storeId, requestDate);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isNotNull().hasSize(2)
                .extracting("storeId", "cash", "pos")
                .containsExactlyInAnyOrder(
                        tuple(storeId, BigDecimal.valueOf(55.05), new BigDecimal("0.00")),
                        tuple(storeId, BigDecimal.valueOf(50.05), new BigDecimal("0.00"))
                );

    }

    @Test
    void retrieveBillingPaymentListByDateAndStoreId(){

        //Given
        Long storeId = 111L;
        String startDate = LocalDate.now().minusDays(5L).toString();
        String endDate = LocalDate.now().toString();

        createBillingPaymentRecord(storeId, 20000L, 50L, BigDecimal.valueOf(50.05), ZonedDateTime.now().minusDays(2L), "storeChainAdmin", PayloadType.NETTING, BigDecimal.ZERO);
        createBillingPaymentRecord(storeId, 20000L, 50L, BigDecimal.valueOf(55.05), ZonedDateTime.now().minusDays(1L), "storeChainAdmin", PayloadType.NETTING, BigDecimal.ZERO);
        createBillingPaymentRecord(storeId, 20000L, 50L, BigDecimal.ZERO, ZonedDateTime.now(), "storeChainAdmin", PayloadType.NETTING, BigDecimal.TEN);

        createBillingPaymentRecord(storeId, 20000L, 50L, BigDecimal.ZERO, ZonedDateTime.now().plusDays(1L), "storeChainAdmin", PayloadType.NETTING, BigDecimal.TEN);
        createBillingPaymentRecord(333L, 20000L, 50L, BigDecimal.valueOf(55.05), ZonedDateTime.now(), "storeChainAdmin", PayloadType.NETTING, BigDecimal.ZERO);


        //When
        ResponseEntity<List<BillingPayment>> response = testRestTemplate.exchange("/api/v1/billing-payment/by-store/{storeId}/date-between?startDate={startDate}&endDate={endDate}",
            HttpMethod.GET, new HttpEntity<>(null, null), new ParameterizedTypeReference<>() {
            }, storeId, startDate, endDate);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isNotNull().hasSize(3)
            .extracting("storeId", "cash","pos")
            .containsExactlyInAnyOrder(
                tuple(storeId, BigDecimal.valueOf(55.05), new BigDecimal("0.00")),
                tuple(storeId, BigDecimal.valueOf(50.05), new BigDecimal("0.00")),
                tuple(storeId, new BigDecimal("0.00"),new BigDecimal("10.00"))
            );
        assertThat(response.getBody().get(0).getCreateOn().toLocalDate()).isBetween(startDate, endDate);
    }

}
