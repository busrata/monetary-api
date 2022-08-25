package com.maxijett.monetary.adapters.store.jpa;

import com.maxijett.monetary.adapters.store.jpa.entity.StoreCollectionEntity;
import com.maxijett.monetary.adapters.store.jpa.entity.StorePaymentTransactionEntity;
import com.maxijett.monetary.adapters.store.jpa.repository.StoreCollectionRepository;
import com.maxijett.monetary.adapters.store.jpa.repository.StorePaymentTransactionRepository;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreCollectionDataAdapter implements StoreCollectionPort {

    private final StoreCollectionRepository storeCollectionRepository;

    private final StorePaymentTransactionRepository storePaymentTransactionRepository;

    @Override
    public StoreCollection retrieve(Long storeId) {
        return storeCollectionRepository.findByStoreId(storeId).toModel();
    }

    @Override
    public StoreCollection update(StoreCollection storeCollection ,StorePaymentTransaction storePaymentTransaction) {

        var entity = new StoreCollectionEntity();

        entity.setId(storeCollection.getId());
        entity.setStoreId(storeCollection.getStoreId());
        entity.setCash(storeCollection.getCash());
        entity.setClientId(entity.getClientId());
        entity.setPos(storeCollection.getPos());
        entity.setTariffType(storeCollection.getTariffType());
        entity.setGroupId(storeCollection.getGroupId());

        storePaymentTransactionRepository.save(buildStorePaymentTransactionEntity(storePaymentTransaction));

        return storeCollectionRepository.save(entity).toModel();
    }

    private StorePaymentTransactionEntity buildStorePaymentTransactionEntity(StorePaymentTransaction storePaymentTransaction){
        StorePaymentTransactionEntity storePaymentTransactionEntity = new StorePaymentTransactionEntity();

        storePaymentTransactionEntity.setStoreId(storePaymentTransaction.getStoreId());
        storePaymentTransactionEntity.setCash(storePaymentTransaction.getCash());
        storePaymentTransactionEntity.setClientId(storePaymentTransaction.getClientId());
        storePaymentTransactionEntity.setDriverId(storePaymentTransaction.getDriverId());
        storePaymentTransactionEntity.setDate(storePaymentTransaction.getDate());
        storePaymentTransactionEntity.setEventType(storePaymentTransaction.getEventType());
        storePaymentTransactionEntity.setOrderNumber(storePaymentTransaction.getOrderNumber());
        storePaymentTransactionEntity.setPos(storePaymentTransaction.getPos());

        return storePaymentTransactionEntity;
    }
}
