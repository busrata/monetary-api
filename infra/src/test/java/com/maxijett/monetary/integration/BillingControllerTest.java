package com.maxijett.monetary.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.billingpayment.rest.dto.BillingPaymentDTO;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@IT
@Sql(scripts = "classpath:sql/cash-box.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/store-collection.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BillingControllerTest  extends AbstractIT {

  @Test
  public void createBillingPaymentWithPayloadTypeCollectionByStoreChainAdmin(){

    //Given
    BillingPaymentDTO billingPaymentDTO = BillingPaymentDTO.builder()
        .storeId(200L)
        .amount(BigDecimal.valueOf(40))
        .clientId(20L)
        .paymentType(PaymentType.CASH)
        .payloadType(PayloadType.COLLECTION)
        .payingAccount("storeChainAdmin")
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
    assertEquals(billingPaymentDTO.getPaymentType(), actualBilling.getPaymentType());
    assertEquals(billingPaymentDTO.getClientId(), actualBilling.getClientId());
    assertEquals(billingPaymentDTO.getAmount(), actualBilling.getAmount());
    assertEquals(billingPaymentDTO.getPayingAccount(), actualBilling.getPayingAccount());
    assertEquals(billingPaymentDTO.getPayloadType(), actualBilling.getPayloadType());

  }

  @Test
  public void createBillingPaymentWithPayloadTypeNettingByStoreChainAdmin(){

    //Given
    BillingPaymentDTO billingPaymentDTO = BillingPaymentDTO.builder()
        .storeId(200L)
        .amount(BigDecimal.valueOf(40))
        .clientId(20L)
        .paymentType(PaymentType.CASH)
        .payloadType(PayloadType.NETTING)
        .payingAccount("storeChainAdmin")
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
    assertEquals(billingPaymentDTO.getPaymentType(), actualBilling.getPaymentType());
    assertEquals(billingPaymentDTO.getClientId(), actualBilling.getClientId());
    assertEquals(billingPaymentDTO.getAmount(), actualBilling.getAmount());
    assertEquals(billingPaymentDTO.getPayingAccount(), actualBilling.getPayingAccount());
    assertEquals(billingPaymentDTO.getPayloadType(), actualBilling.getPayloadType());
  }


}
