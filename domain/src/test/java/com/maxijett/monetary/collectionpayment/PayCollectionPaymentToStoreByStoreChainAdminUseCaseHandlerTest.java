package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.adapters.CollectionPaymentFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.adapters.StoreCollectionFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.adapters.StorePaymentTransactionFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.model.enumeration.TariffType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class PayCollectionPaymentToStoreByStoreChainAdminUseCaseHandlerTest {

    StoreCollectionFakeDataAdapter storeCollectionFakeDataAdapter;

    StorePaymentTransactionFakeDataAdapter storePaymentTransactionFakeDataAdapter;

    CollectionPaymentPort collectionPaymentPort = new CollectionPaymentFakeDataAdapter();
    PayCollectionPaymentToStoreByStoreChainAdminUseCaseHandler handler;


    @BeforeEach
    public void setUp() {
        storeCollectionFakeDataAdapter = new StoreCollectionFakeDataAdapter();
        storePaymentTransactionFakeDataAdapter = new StorePaymentTransactionFakeDataAdapter();
        handler = new PayCollectionPaymentToStoreByStoreChainAdminUseCaseHandler(collectionPaymentPort, storeCollectionFakeDataAdapter, storePaymentTransactionFakeDataAdapter);
    }

    @Test
    public void shouldBeSaveCollectionPayment() {

        //Given
        CollectionPaymentCreate collectionPaymentUseCase = CollectionPaymentCreate.builder()
                .storeId(12L)
                .cash(BigDecimal.ZERO)
                .pos(BigDecimal.valueOf(225))
                .date(ZonedDateTime.now(ZoneId.of("UTC")))
                .clientId(20L)
                .groupId(21L).build();

        //When
        CollectionPayment response = handler.handle(collectionPaymentUseCase);

        //Then
        Assertions.assertEquals(collectionPaymentUseCase.getPos(), response.getPos());
        Assertions.assertEquals(collectionPaymentUseCase.getStoreId(), response.getStoreId());
        Assertions.assertEquals(collectionPaymentUseCase.getGroupId(), response.getGroupId());
        Assertions.assertEquals(collectionPaymentUseCase.getClientId(), response.getClientId());
        Assertions.assertEquals(BigDecimal.ZERO, response.getCash());
        Assertions.assertNull(response.getDriverId());

    }

    @Test
    public void shouldBeUpdateStoreCashes() {

        //Given
        CollectionPaymentCreate collectionPaymentUseCase = CollectionPaymentCreate.builder()
                .storeId(12L)
                .cash(BigDecimal.ZERO)
                .pos(BigDecimal.valueOf(85))
                .date(ZonedDateTime.now(ZoneId.of("UTC")))
                .clientId(20L)
                .groupId(21L).build();

        //When
        CollectionPayment response = handler.handle(collectionPaymentUseCase);

        //Then
        Assertions.assertNotNull(response.getId());

        storeCollectionFakeDataAdapter.assertContains(
                StoreCollection.builder()
                        .storeId(12L)
                        .cash(new BigDecimal(55))
                        .pos(new BigDecimal(15))
                        .clientId(20L)
                        .tariffType(TariffType.TAXIMETER_HOT)
                        .build());

    }

    @Test
    public void shouldBeSaveStoreTransactions() {

        //Given
        CollectionPaymentCreate collectionPaymentUseCase = CollectionPaymentCreate.builder()
                .storeId(12L)
                .cash(BigDecimal.ZERO)
                .pos(BigDecimal.valueOf(85))
                .date(ZonedDateTime.now(ZoneId.of("UTC")))
                .clientId(20L)
                .groupId(21L).build();

        //When
        CollectionPayment response = handler.handle(collectionPaymentUseCase);

        //Then
        Assertions.assertNotNull(response.getId());


        var storePaymentTransaction = storePaymentTransactionFakeDataAdapter.transactionList.get(0);

        Assertions.assertEquals(collectionPaymentUseCase.getPos(), storePaymentTransaction.getPos());
        Assertions.assertEquals(collectionPaymentUseCase.getStoreId(), storePaymentTransaction.getStoreId());
        Assertions.assertEquals(collectionPaymentUseCase.getClientId(), storePaymentTransaction.getClientId());
        Assertions.assertEquals(StoreEventType.ADMIN_GET_PAID, storePaymentTransaction.getEventType());
        Assertions.assertEquals(BigDecimal.ZERO, storePaymentTransaction.getCash());

    }

}