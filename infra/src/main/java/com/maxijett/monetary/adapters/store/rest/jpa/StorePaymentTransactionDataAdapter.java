package com.maxijett.monetary.adapters.store.rest.jpa;

import com.maxijett.monetary.adapters.store.rest.jpa.entity.StorePaymentTransactionEntity;
import com.maxijett.monetary.adapters.store.rest.jpa.repository.StorePaymentTransactionRepository;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.port.StorePaymentTransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorePaymentTransactionDataAdapter implements StorePaymentTransactionPort {

    private final StorePaymentTransactionRepository storePaymentTransactionRepository;

    @Override
    public StorePaymentTransaction create(StorePaymentTransaction from) {

        var entity = new StorePaymentTransactionEntity();
        entity.setOrderNumber(from.getOrderNumber());
        entity.setCash(from.getCash());
        entity.setPos(from.getPos());
        entity.setDate(from.getDate());
        entity.setClientId(from.getClientId());
        entity.setStoreId(from.getStoreId());
        entity.setDriverId(from.getDriverId());
        entity.setParentTransactionId(from.getParentTransactionId());
        entity.setEventType(from.getEventType());
        entity.setUserId(from.getUserId());

        return storePaymentTransactionRepository.save(entity).toModel();
    }
}
