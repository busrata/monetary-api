package com.maxijett.monetary.billingpayment;

import com.maxijett.monetary.billingpayment.adapters.BillingPaymentPortFakeDataAdapter;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import com.maxijett.monetary.cashbox.adapters.CashBoxFakeDataAdapter;
import com.maxijett.monetary.store.adapters.StoreCollectionFakeDataAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                .cash(BigDecimal.valueOf(48))
                .pos(BigDecimal.ZERO)
                .payloadType(PayloadType.COLLECTION)
                .storeId(2L)
                .payingAccount("storeChainAdmin")
                .clientId(CLIENT_ID)
                .build();

        //When
        BillingPayment responseBillingPayment = useCaseHandler.handle(billingPaymentCreate);

        //Then
        assertEquals(billingPaymentCreate.getPos(), responseBillingPayment.getPos());
        assertEquals(billingPaymentCreate.getPayloadType(), responseBillingPayment.getPayloadType());
        assertEquals(billingPaymentCreate.getStoreId(), responseBillingPayment.getStoreId());
        assertEquals(billingPaymentCreate.getClientId(), responseBillingPayment.getClientId());
        assertEquals(billingPaymentCreate.getCash(), responseBillingPayment.getCash());
        assertEquals(billingPaymentCreate.getPayingAccount(), responseBillingPayment.getPayingAccount());

    }

    @Test
    public void shouldBeSaveBillingPaymentCashWithPayloadTypeCollection() {

        //Given
        BillingPaymentCreate billingPaymentCreate = BillingPaymentCreate.builder()
                .cash(BigDecimal.valueOf(48))
                .pos(BigDecimal.ZERO)
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
                .cash(BigDecimal.valueOf(48))
                .storeId(billingPaymentCreate.getStoreId())
                .pos(BigDecimal.ZERO)
                .isDeleted(false)
                .build());


        assertEquals(expectedCashBoxAmountForStoreGroups, cashBoxPort.boxes.get(0).getCash());
        assertEquals(billingPaymentCreate.getPos(), responseBillingPayment.getPos());
        assertEquals(billingPaymentCreate.getPayloadType(), responseBillingPayment.getPayloadType());
        assertEquals(billingPaymentCreate.getStoreId(), responseBillingPayment.getStoreId());
        assertEquals(billingPaymentCreate.getClientId(), responseBillingPayment.getClientId());
        assertEquals(billingPaymentCreate.getCash(), responseBillingPayment.getCash());
        assertEquals(billingPaymentCreate.getPayingAccount(), responseBillingPayment.getPayingAccount());


        assertEquals(BigDecimal.valueOf(7), storeCollectionPort.storeCollectionList.get(0).getCash());

    }

    @Test
    public void shouldBeSaveBillingPaymentPosWithPayloadTypeCollection() {

        //Given
        BillingPaymentCreate billingPaymentCreate = BillingPaymentCreate.builder()
                .pos(BigDecimal.valueOf(50))
                .cash(BigDecimal.ZERO)
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
                .pos(BigDecimal.valueOf(50))
                .storeId(billingPaymentCreate.getStoreId())
                .cash(BigDecimal.ZERO)
                .isDeleted(false)
                .build());

        assertEquals(billingPaymentCreate.getCash(), responseBillingPayment.getCash());
        assertEquals(billingPaymentCreate.getPayloadType(), responseBillingPayment.getPayloadType());
        assertEquals(billingPaymentCreate.getStoreId(), responseBillingPayment.getStoreId());
        assertEquals(billingPaymentCreate.getClientId(), responseBillingPayment.getClientId());
        assertEquals(billingPaymentCreate.getPos(), responseBillingPayment.getPos());
        assertEquals(billingPaymentCreate.getPayingAccount(), responseBillingPayment.getPayingAccount());

        assertEquals(BigDecimal.valueOf(50), storeCollectionPort.storeCollectionList.get(0).getPos());

    }

    @Test
    public void shouldBeSaveBillingPaymentCashWithPayloadTypeNetting() {
        //Given
        BillingPaymentCreate billingPaymentCreate = BillingPaymentCreate.builder()
                .cash(BigDecimal.valueOf(48))
                .pos(BigDecimal.ZERO)
                .payloadType(PayloadType.NETTING)
                .storeId(20000L)
                .payingAccount("storeChainAdmin")
                .clientId(CLIENT_ID)
                .build();


        //When
        BillingPayment actualBillingPayment = useCaseHandler.handle(billingPaymentCreate);

        //Then

        assertEquals(billingPaymentCreate.getCash(), actualBillingPayment.getCash());
        assertEquals(billingPaymentCreate.getPayloadType(), actualBillingPayment.getPayloadType());
        assertEquals(billingPaymentCreate.getStoreId(), actualBillingPayment.getStoreId());
        assertEquals(billingPaymentCreate.getClientId(), actualBillingPayment.getClientId());
        assertEquals(billingPaymentCreate.getPos(), actualBillingPayment.getPos());
        assertEquals(billingPaymentCreate.getPayingAccount(), actualBillingPayment.getPayingAccount());
    }


}
