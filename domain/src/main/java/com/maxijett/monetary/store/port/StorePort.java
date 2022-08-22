package com.maxijett.monetary.store.port;

import com.maxijett.monetary.store.model.Store;

public interface StorePort {
    Store retrieve(Long storeId);
}
