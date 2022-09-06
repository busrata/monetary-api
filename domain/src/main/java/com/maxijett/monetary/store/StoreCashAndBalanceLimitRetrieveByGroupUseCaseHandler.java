package com.maxijett.monetary.store;

import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.store.model.StoreCashAndBalanceLimit;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import com.maxijett.monetary.store.usecase.StoreCollectionRetrieve;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@DomainComponent
@RequiredArgsConstructor
public class StoreCashAndBalanceLimitRetrieveByGroupUseCaseHandler implements
        UseCaseHandler<List<StoreCashAndBalanceLimit>, StoreCollectionRetrieve> {

    private final StoreCollectionPort storeCollectionPort;

    @Override
    public List<StoreCashAndBalanceLimit> handle(StoreCollectionRetrieve useCase) {

        List<StoreCollection> storeCollectionList = null;

        storeCollectionList = storeCollectionPort.getListByGroupId(useCase.getGroupId());

        List<StoreCashAndBalanceLimit> storeCashAndBalanceLimits = storeCollectionList.stream()
                .map(StoreCollection::toStoreCashAndBalanceLimit)
                .collect(Collectors.toList());

        return storeCashAndBalanceLimits;
    }
}
