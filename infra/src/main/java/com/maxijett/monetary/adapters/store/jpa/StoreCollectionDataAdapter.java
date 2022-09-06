package com.maxijett.monetary.adapters.store.jpa;

import com.maxijett.monetary.adapters.store.jpa.entity.StoreCollectionEntity;
import com.maxijett.monetary.adapters.store.jpa.entity.StorePaymentTransactionEntity;
import com.maxijett.monetary.adapters.store.jpa.repository.StoreCollectionRepository;
import com.maxijett.monetary.adapters.store.jpa.repository.StorePaymentTransactionRepository;
import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreCollectionDataAdapter implements StoreCollectionPort {

    private final StoreCollectionRepository storeCollectionRepository;

    private final StorePaymentTransactionRepository storePaymentTransactionRepository;

    @Override
    public StoreCollection retrieve(Long storeId) {
        return storeCollectionRepository.findByStoreId(storeId).map(StoreCollectionEntity::toModel)
                .orElseThrow(() -> new MonetaryApiBusinessException("monetaryapi.storecollection.notFound", String.valueOf(storeId)));
    }

    @Override
    public List<StoreCollection> getListByClientId(Long clientId) {
        return storeCollectionRepository.findAllByClientId(clientId).stream()
                .map(StoreCollectionEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreCollection> getListByGroupId(Long groupId) {
        return storeCollectionRepository.findAllByGroupId(groupId).stream()
                .map(StoreCollectionEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public StoreCollection update(StoreCollection storeCollection,
            StorePaymentTransaction storePaymentTransaction) {

        StoreCollectionEntity entity = storeCollectionRepository.findById(storeCollection.getId())
                .orElseThrow(()-> new MonetaryApiBusinessException("monetaryapi.storecollection.notFoundById", String.valueOf(storeCollection.getId())));

        entity.setStoreId(storeCollection.getStoreId());
        entity.setCash(storeCollection.getCash());
        entity.setClientId(entity.getClientId());
        entity.setPos(storeCollection.getPos());
        entity.setTariffType(storeCollection.getTariffType());
        entity.setGroupId(storeCollection.getGroupId());

        storePaymentTransactionRepository.save(
                buildStorePaymentTransactionEntity(storePaymentTransaction));

        return storeCollectionRepository.saveAndFlush(entity).toModel();
    }

    private StorePaymentTransactionEntity buildStorePaymentTransactionEntity(
            StorePaymentTransaction storePaymentTransaction) {
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
