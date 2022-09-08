package com.maxijett.monetary.store.port;

import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;

import java.util.List;
import java.util.Optional;

public interface StorePaymentTransactionPort {

    StorePaymentTransaction create(StorePaymentTransaction storePaymentTransaction);

    Optional<StorePaymentTransaction> findTransactionForRollback(String orderNumber, List<StoreEventType> eventTypes);
}
