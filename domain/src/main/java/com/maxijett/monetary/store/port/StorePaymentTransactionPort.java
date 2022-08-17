package com.maxijett.monetary.store.port;

import com.maxijett.monetary.store.model.StorePaymentTransaction;

public interface StorePaymentTransactionPort {

  StorePaymentTransaction create(StorePaymentTransaction storePaymentTransaction);

}
