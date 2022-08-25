package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.adapters.BillingPaymentPortFakeDataAdapter;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentPrePaidCreate;
import com.maxijett.monetary.cashbox.adapters.DriverCashFakeDataAdapter;
import com.maxijett.monetary.cashbox.adapters.DriverPaymentTransactionFakeDataAdapter;
import com.maxijett.monetary.driver.model.DriverCash;
import com.maxijett.monetary.driver.model.DriverPaymentTransaction;
import com.maxijett.monetary.driver.port.DriverCashPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetPaidBillingPaymentFromColdStoreByDriverUseCaseHandlerTest {

    GetPaidBillingPaymentFromColdStoreByDriverUseCaseHandler useCaseHandler;
    BillingPaymentPortFakeDataAdapter billingPaymentPort;
    DriverCashFakeDataAdapter driverCashPort;


    @BeforeEach
    public void setUp() {
        billingPaymentPort = new BillingPaymentPortFakeDataAdapter();
        driverCashPort = new DriverCashFakeDataAdapter();
        useCaseHandler = new GetPaidBillingPaymentFromColdStoreByDriverUseCaseHandler(billingPaymentPort, driverCashPort);
    }
    @Test
    public void shouldBeReturnBillingPaymentExistsWhenBillingPaymentPrePaidCreate() {

        //Given
        BillingPaymentPrePaidCreate billingPaymentPrePaidCreate = BillingPaymentPrePaidCreate.builder()
                .paymentType(PaymentType.CASH)
                .payloadType(PayloadType.COLLECTION)
                .storeId(2L)
                .clientId(20L)
                .driverId(12L)
                .prePaidBillingAmount(BigDecimal.valueOf(40))
                .build();

        //When
        BillingPayment responseBillingPayment = useCaseHandler.handle(billingPaymentPrePaidCreate);

        //Then
        assertEquals(billingPaymentPrePaidCreate.getPrePaidBillingAmount(), responseBillingPayment.getAmount());
        assertEquals(billingPaymentPrePaidCreate.getPaymentType(), responseBillingPayment.getPaymentType());
        assertEquals(billingPaymentPrePaidCreate.getPayloadType(), responseBillingPayment.getPayloadType());
        assertEquals(billingPaymentPrePaidCreate.getStoreId(), responseBillingPayment.getStoreId());
        assertEquals(billingPaymentPrePaidCreate.getClientId(), responseBillingPayment.getClientId());

    }

    @Test
    public void shouldBeSaveBillingPaymentPrePaidCreate(){

        //Given
        BillingPaymentPrePaidCreate billingPaymentPrePaidCreate = BillingPaymentPrePaidCreate.builder()
                .paymentType(PaymentType.CASH)
                .payloadType(PayloadType.NETTING)
                .storeId(2L)
                .clientId(20L)
                .driverId(2L)
                .groupId(1L)
                .prePaidBillingAmount(BigDecimal.valueOf(40))
                .build();

        //When
        BillingPayment responseBillingPayment = useCaseHandler.handle(billingPaymentPrePaidCreate);


        //Then
        billingPaymentPort.assertContains(BillingPayment.builder()
                        .amount(billingPaymentPrePaidCreate.getPrePaidBillingAmount())
                        .storeId(billingPaymentPrePaidCreate.getStoreId())
                        .clientId(billingPaymentPrePaidCreate.getClientId())
                        .paymentType(billingPaymentPrePaidCreate.getPaymentType())
                        .payloadType(billingPaymentPrePaidCreate.getPayloadType())
                .build());

        driverCashPort.assertContains(DriverCash.builder()
                        .id(1L)
                        .groupId(billingPaymentPrePaidCreate.getGroupId())
                        .driverId(billingPaymentPrePaidCreate.getDriverId())
                        .prepaidCollectionCash(BigDecimal.valueOf(115))
                        .clientId(billingPaymentPrePaidCreate.getClientId())
                        .cash(BigDecimal.valueOf(120))
                .build());


        assertEquals(billingPaymentPrePaidCreate.getPrePaidBillingAmount(), responseBillingPayment.getAmount());
    }
}
