package com.maxijett.monetary.store.port;

import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;

public interface StoreCollectionPort {

  StoreCollection retrieve(Long storeId);

  StoreCollection update(StoreCollection storeCollection, StorePaymentTransaction storePaymentTransaction);




}
