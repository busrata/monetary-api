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
import com.maxijett.monetary.cashbox.port.CashBoxTransactionPort;
import com.maxijett.monetary.collectionpayment.adapters.StoreCollectionFakeDataAdapter;
import com.maxijett.monetary.store.StorePortFakeDataAdapter;
import com.maxijett.monetary.store.adapters.StorePaymentTransactionFakeDataAdapter;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import com.maxijett.monetary.store.port.StorePaymentTransactionPort;
import com.maxijett.monetary.store.port.StorePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetPaidBillingPaymentFromStoreByStoreChainAdminTest {

    private final static Long CLIENT_ID = 20000L;

    CashBoxFakeDataAdapter cashBoxPort;
    StoreCollectionFakeDataAdapter storeCollectionPort;
    StorePaymentTransactionFakeDataAdapter storePaymentTransactionPort;
    CashBoxTransactionFakeDataAdapter cashBoxTransaction;
    BillingPaymentPortFakeDataAdapter billingPaymentPort;
    StorePort storePort;
    GetPaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler useCaseHandler;

    @BeforeEach
    public void setUp() {
        cashBoxPort = new CashBoxFakeDataAdapter();
        billingPaymentPort = new BillingPaymentPortFakeDataAdapter();
        cashBoxTransaction = new CashBoxTransactionFakeDataAdapter();
        storeCollectionPort = new StoreCollectionFakeDataAdapter();
        storePort = new StorePortFakeDataAdapter();
        storePaymentTransactionPort = new StorePaymentTransactionFakeDataAdapter();
        useCaseHandler = new GetPaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler(
                cashBoxPort, billingPaymentPort, storeCollectionPort, storePaymentTransactionPort, cashBoxTransaction, storePort);
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
    public void shouldBeSaveBillingPaymentWithPayloadTypeNetting() {

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
        BillingPayment responseBillingPayment = useCaseHandler.handle(billingPaymentCreate);
        BillingPayment savedBillingPayment = billingPaymentPort.billings.get(0);

        CashBoxTransaction actualCashBoxTransaction = cashBoxTransaction.getCashBoxTransactions().get(0);
        BigDecimal expectedCashBoxAmountForStoreGroups = BigDecimal.valueOf(202);

        //Then

        billingPaymentPort.assertContains(savedBillingPayment);


        assertEquals(expectedCashBoxAmountForStoreGroups, cashBoxPort.boxes.get(0).getCash());
        assertEquals(billingPaymentCreate.getPaymentType(), responseBillingPayment.getPaymentType());
        assertEquals(billingPaymentCreate.getPayloadType(), responseBillingPayment.getPayloadType());
        assertEquals(billingPaymentCreate.getStoreId(), responseBillingPayment.getStoreId());
        assertEquals(billingPaymentCreate.getClientId(), responseBillingPayment.getClientId());
        assertEquals(billingPaymentCreate.getAmount(), responseBillingPayment.getAmount());
        assertEquals(billingPaymentCreate.getPayingAccount(), responseBillingPayment.getPayingAccount());

        assertEquals(billingPaymentCreate.getAmount(), actualCashBoxTransaction.getAmount());
        assertNull(actualCashBoxTransaction.getDriverId());

        assertEquals(BigDecimal.valueOf(7), storeCollectionPort.storeCollectionList.get(0).getCash());
        assertEquals(BigDecimal.valueOf(48), storePaymentTransactionPort.transactionList.get(0).getCash());
        assertNull(storePaymentTransactionPort.transactionList.get(0).getPos());
        assertEquals(StoreEventType.ADMIN_GET_PAID, storePaymentTransactionPort.transactionList.get(0).getEventType());


    }

    @Test
    public void shouldBeSaveBillingPaymentWithPayloadTypeCollection() {
        //store ve storePaymentTransaction check
    }


}
