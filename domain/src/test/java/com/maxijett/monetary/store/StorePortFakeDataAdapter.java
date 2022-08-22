package com.maxijett.monetary.store;

import com.maxijett.monetary.store.model.Store;
import com.maxijett.monetary.store.port.StorePort;

public class StorePortFakeDataAdapter implements StorePort {
    @Override
    public Store retrieve(Long storeId) {
        return Store.builder().id(storeId).groupId(1L).build();
    }
}
