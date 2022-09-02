package com.maxijett.monetary.store;

import com.maxijett.monetary.common.DomainComponent;
import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import com.maxijett.monetary.store.useCase.StoreCollectionRetrieve;
import java.util.List;
import lombok.RequiredArgsConstructor;

@DomainComponent
@RequiredArgsConstructor
public class StoreCollectionRetrieveByStoreIdUseCaseHandler implements
        UseCaseHandler<List<StoreCollection>, StoreCollectionRetrieve> {

    private final StoreCollectionPort storeCollectionPort;

    @Override
    public List<StoreCollection> handle(StoreCollectionRetrieve useCase) {

        List<StoreCollection> storeCollectionList = null;

        if (useCase.getClientId() != null) {
            storeCollectionList = storeCollectionPort.getListByClientId(useCase.getClientId());

        } else if (useCase.getGroupId() != null) {
            storeCollectionList = storeCollectionPort.getListByGroupId(useCase.getGroupId());

        } else {
            throw new MonetaryApiBusinessException(
                    "monetaryapi.cashbox.clientIdAndGroupIdCanNotBeNull");
        }

        return storeCollectionList;
    }
}
