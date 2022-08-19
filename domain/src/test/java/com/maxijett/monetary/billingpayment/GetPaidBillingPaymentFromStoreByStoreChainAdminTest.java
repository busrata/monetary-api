package com.maxijett.monetary.billingpayment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetPaidBillingPaymentFromStoreByStoreChainAdminTest{

  GetPaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler useCaseHandler = new GetPaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler();

  @Test
  public void shouldBeSaveBillingPayment(){

    //Given
    BillingPaymentCreate billingPaymentCreate = BillingPaymentCreate.builder()
        .amount(BigDecimal.valueOf(48))
        .paymentType(PaymentType.CREDIT_CARD)
        .payloadType(PayloadType.COLLECTION)
        .storeId(2L)
        .payingAccount("storeChainAdmin")
        .clientId(20L)
        .build();

    //When
    BillingPayment responseBillingPayment = useCaseHandler.handle(billingPaymentCreate);

    //Then
    Assertions.assertNotNull(responseBillingPayment.getId());
    assertEquals(billingPaymentCreate.getPaymentType(), responseBillingPayment.getPaymentType());
    assertEquals(billingPaymentCreate.getPayloadType(), responseBillingPayment.getPayloadType());
    assertEquals(billingPaymentCreate.getStoreId(), responseBillingPayment.getStoreId());
    assertEquals(billingPaymentCreate.getClientId(), responseBillingPayment.getClientId());
    assertEquals(billingPaymentCreate.getAmount(), responseBillingPayment.getAmount());
    assertEquals(billingPaymentCreate.getPayingAccount(), responseBillingPayment.getPayingAccount());

  }

  @Test
  public void shouldBeSaveBillingPaymentWithPayloadTypeNetting(){

    //Given
    BillingPaymentCreate billingPaymentCreate = BillingPaymentCreate.builder()
        .amount(BigDecimal.valueOf(48))
        .paymentType(PaymentType.CREDIT_CARD)
        .payloadType(PayloadType.NETTING)
        .storeId(2L)
        .payingAccount("storeChainAdmin")
        .clientId(20L)
        .build();

    //When
    BillingPayment responseBillingPayment = useCaseHandler.handle(billingPaymentCreate);

    //Then
    Assertions.assertNotNull(responseBillingPayment.getId());
    assertEquals(billingPaymentCreate.getPaymentType(), responseBillingPayment.getPaymentType());
    assertEquals(billingPaymentCreate.getPayloadType(), responseBillingPayment.getPayloadType());
    assertEquals(billingPaymentCreate.getStoreId(), responseBillingPayment.getStoreId());
    assertEquals(billingPaymentCreate.getClientId(), responseBillingPayment.getClientId());
    assertEquals(billingPaymentCreate.getAmount(), responseBillingPayment.getAmount());
    assertEquals(billingPaymentCreate.getPayingAccount(), responseBillingPayment.getPayingAccount());
  }

  @Test
  public void shouldBeSaveBillingPaymentWithPayloadTypeCollection(){
    //store ve storePaymentTransaction check
  }


}
