package com.maxijett.monetary.billingpayment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.maxijett.monetary.billingpayment.adapters.BillingPaymentPortFakeDataAdapter;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;

import java.math.BigDecimal;

import com.maxijett.monetary.cashbox.adapters.CashBoxFakeDataAdapter;
import com.maxijett.monetary.cashbox.adapters.CashBoxTransactionFakeDataAdapter;
import com.maxijett.monetary.cashbox.model.CashBoxTransaction;
import com.maxijett.monetary.store.adapters.StoreCollectionFakeDataAdapter;
import com.maxijett.monetary.store.adapters.StorePaymentTransactionFakeDataAdapter;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetPaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandlerTest {

    private final static Long CLIENT_ID = 20000L;

    CashBoxFakeDataAdapter cashBoxPort;
    StoreCollectionFakeDataAdapter storeCollectionPort;
    BillingPaymentPortFakeDataAdapter billingPaymentPort;
    GetPaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler useCaseHandler;

    @BeforeEach
    public void setUp() {
        cashBoxPort = new CashBoxFakeDataAdapter();
        billingPaymentPort = new BillingPaymentPortFakeDataAdapter();
        storeCollectionPort = new StoreCollectionFakeDataAdapter();
        useCaseHandler = new GetPaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler(
                cashBoxPort, billingPaymentPort, storeCollectionPort);
    }

    @Test
    public void shouldBeReturnBillingPaymentExistsWhenBillingPaymentCreate() {

        //Given
        BillingPaymentCreate billingPaymentCreate = BillingPaymentCreate.builder()
                .amount(BigDecimal.valueOf(48))
                .paymentType(PaymentType.CASH)
                .payloadType(PayloadType.COLLECTION)
                .storeId(2L)
                .payingAccount("storeChainAdmin")
                .clientId(CLIENT_ID)
                .build();

        //When
        BillingPayment responseBillingPayment = useCaseHandler.handle(billingPaymentCreate);

        //Then
        assertEquals(billingPaymentCreate.getPaymentType(), responseBillingPayment.getPaymentType());
        assertEquals(billingPaymentCreate.getPayloadType(), responseBillingPayment.getPayloadType());
        assertEquals(billingPaymentCreate.getStoreId(), responseBillingPayment.getStoreId());
        assertEquals(billingPaymentCreate.getClientId(), responseBillingPayment.getClientId());
        assertEquals(billingPaymentCreate.getAmount(), responseBillingPayment.getAmount());
        assertEquals(billingPaymentCreate.getPayingAccount(), responseBillingPayment.getPayingAccount());

    }

    @Test
    public void shouldBeSaveBillingPaymentCashWithPayloadTypeCollection() {

        //Given
        BillingPaymentCreate billingPaymentCreate = BillingPaymentCreate.builder()
                .amount(BigDecimal.valueOf(48))
                .paymentType(PaymentType.CASH)
                .payloadType(PayloadType.COLLECTION)
                .storeId(20000L)
                .payingAccount("storeChainAdmin")
                .clientId(CLIENT_ID)
                .build();


        //When
        BillingPayment responseBillingPayment = useCaseHandler.handle(billingPaymentCreate);

        BigDecimal expectedCashBoxAmountForStoreGroups = BigDecimal.valueOf(202);

        //Then

        billingPaymentPort.assertContains(BillingPayment.builder()
                .clientId(CLIENT_ID)
                .payingAccount("storeChainAdmin")
                .payloadType(PayloadType.COLLECTION)
                .amount(BigDecimal.valueOf(48))
                .storeId(billingPaymentCreate.getStoreId())
                .paymentType(PaymentType.CASH)
                .isDeleted(false)
                .build());


        assertEquals(expectedCashBoxAmountForStoreGroups, cashBoxPort.boxes.get(0).getCash());
        assertEquals(billingPaymentCreate.getPaymentType(), responseBillingPayment.getPaymentType());
        assertEquals(billingPaymentCreate.getPayloadType(), responseBillingPayment.getPayloadType());
        assertEquals(billingPaymentCreate.getStoreId(), responseBillingPayment.getStoreId());
        assertEquals(billingPaymentCreate.getClientId(), responseBillingPayment.getClientId());
        assertEquals(billingPaymentCreate.getAmount(), responseBillingPayment.getAmount());
        assertEquals(billingPaymentCreate.getPayingAccount(), responseBillingPayment.getPayingAccount());


        assertEquals(BigDecimal.valueOf(7), storeCollectionPort.storeCollectionList.get(0).getCash());

    }

    @Test
    public void shouldBeSaveBillingPaymentPosWithPayloadTypeCollection() {

        //Given
        BillingPaymentCreate billingPaymentCreate = BillingPaymentCreate.builder()
                .amount(BigDecimal.valueOf(50))
                .paymentType(PaymentType.CREDIT_CARD)
                .payloadType(PayloadType.COLLECTION)
                .storeId(20000L)
                .payingAccount("storeChainAdmin")
                .clientId(CLIENT_ID)
                .build();


        //When
        BillingPayment responseBillingPayment = useCaseHandler.handle(billingPaymentCreate);

        //Then

        billingPaymentPort.assertContains(BillingPayment.builder()
                .clientId(CLIENT_ID)
                .payingAccount("storeChainAdmin")
                .payloadType(PayloadType.COLLECTION)
                .amount(BigDecimal.valueOf(50))
                .storeId(billingPaymentCreate.getStoreId())
                .paymentType(PaymentType.CREDIT_CARD)
                .isDeleted(false)
                .build());

        assertEquals(billingPaymentCreate.getPaymentType(), responseBillingPayment.getPaymentType());
        assertEquals(billingPaymentCreate.getPayloadType(), responseBillingPayment.getPayloadType());
        assertEquals(billingPaymentCreate.getStoreId(), responseBillingPayment.getStoreId());
        assertEquals(billingPaymentCreate.getClientId(), responseBillingPayment.getClientId());
        assertEquals(billingPaymentCreate.getAmount(), responseBillingPayment.getAmount());
        assertEquals(billingPaymentCreate.getPayingAccount(), responseBillingPayment.getPayingAccount());

        assertEquals(BigDecimal.valueOf(50), storeCollectionPort.storeCollectionList.get(0).getPos());

    }

    @Test
    public void shouldBeSaveBillingPaymentCashWithPayloadTypeNetting() {
        //Given
        BillingPaymentCreate billingPaymentCreate = BillingPaymentCreate.builder()
                .amount(BigDecimal.valueOf(48))
                .paymentType(PaymentType.CASH)
                .payloadType(PayloadType.NETTING)
                .storeId(20000L)
                .payingAccount("storeChainAdmin")
                .clientId(CLIENT_ID)
                .build();


        //When
        BillingPayment actualBillingPayment = useCaseHandler.handle(billingPaymentCreate);

        //Then

        assertEquals(billingPaymentCreate.getPaymentType(), actualBillingPayment.getPaymentType());
        assertEquals(billingPaymentCreate.getPayloadType(), actualBillingPayment.getPayloadType());
        assertEquals(billingPaymentCreate.getStoreId(), actualBillingPayment.getStoreId());
        assertEquals(billingPaymentCreate.getClientId(), actualBillingPayment.getClientId());
        assertEquals(billingPaymentCreate.getAmount(), actualBillingPayment.getAmount());
        assertEquals(billingPaymentCreate.getPayingAccount(), actualBillingPayment.getPayingAccount());
    }


}
