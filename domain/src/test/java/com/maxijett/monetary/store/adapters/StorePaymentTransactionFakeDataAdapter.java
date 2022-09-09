package com.maxijett.monetary.store.adapters;

import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.port.StorePaymentTransactionPort;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StorePaymentTransactionFakeDataAdapter implements StorePaymentTransactionPort {

    public List<StorePaymentTransaction> transactionList = new ArrayList<>();

    @Override
    public StorePaymentTransaction create(StorePaymentTransaction storePaymentTransaction) {
        transactionList.add(storePaymentTransaction);
        return storePaymentTransaction;
    }

    @Override
    public Optional<StorePaymentTransaction> findTransactionForRollback(String orderNumber, List<StoreEventType> eventTypes) {
        String rollbackOrderNumber = "999999999";
        if (orderNumber.equals(rollbackOrderNumber)) {
            return Optional.of(StorePaymentTransaction.builder()
                    .id(1L)
                    .cash(BigDecimal.valueOf(45))
                    .createOn(ZonedDateTime.now(ZoneOffset.UTC).minusHours(2))
                    .eventType(StoreEventType.PACKAGE_DELIVERED)
                    .clientId(20000L)
                    .storeId(20L)
                    .driverId(2L)
                    .pos(BigDecimal.valueOf(88))
                    .orderNumber(orderNumber)
                    .build());

        } else {
            return Optional.empty();
        }
    }

}
