package com.maxijett.monetary.adapters.store.jpa;

import com.maxijett.monetary.adapters.store.jpa.entity.StoreCollectionEntity;
import com.maxijett.monetary.adapters.store.jpa.repository.StoreCollectionRepository;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreCollectionDataAdapter implements StoreCollectionPort {

    private final StoreCollectionRepository storeCollectionRepository;

    @Override
    public StoreCollection retrieve(Long storeId) {
        return storeCollectionRepository.findByStoreId(storeId).toModel();
    }

    @Override
    public StoreCollection update(StoreCollection storeCollection) {

        var entity = new StoreCollectionEntity();

        entity.setId(storeCollection.getId());
        entity.setStoreId(storeCollection.getStoreId());
        entity.setCash(storeCollection.getCash());
        entity.setClientId(entity.getClientId());
        entity.setPos(storeCollection.getPos());
        entity.setTariffType(storeCollection.getTariffType());
        entity.setGroupId(storeCollection.getGroupId());

        return storeCollectionRepository.save(entity).toModel();
    }
}
