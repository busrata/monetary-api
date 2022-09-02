package com.maxijett.monetary.store.port;

import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import java.util.List;

public interface StoreCollectionPort {

    StoreCollection retrieve(Long storeId);

    List<StoreCollection> getListByClientId(Long clientId);

    List<StoreCollection> getListByGroupId(Long groupId);

    StoreCollection update(StoreCollection storeCollection,
            StorePaymentTransaction storePaymentTransaction);


}
