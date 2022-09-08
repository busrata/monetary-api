package com.maxijett.monetary.store;

import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import com.maxijett.monetary.store.usecase.StoreCollectionRetrieve;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class GetStoreCollectionUseCaseHandler implements
        UseCaseHandler<StoreCollection, StoreCollectionRetrieve> {

    private final StoreCollectionPort storeCollectionPort;

    @Override
    public StoreCollection handle(StoreCollectionRetrieve useCase) {
        return storeCollectionPort.retrieve(useCase.getId());
    }
}
