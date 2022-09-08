package com.maxijett.monetary.adapters.store.rest.jpa;

import com.maxijett.monetary.adapters.store.rest.jpa.entity.StorePaymentTransactionEntity;
import com.maxijett.monetary.adapters.store.rest.jpa.repository.StorePaymentTransactionRepository;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.port.StorePaymentTransactionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        entity.setCreateOn(from.getCreateOn());
        entity.setClientId(from.getClientId());
        entity.setStoreId(from.getStoreId());
        entity.setDriverId(from.getDriverId());
        entity.setParentTransactionId(from.getParentTransactionId());
        entity.setEventType(from.getEventType());
        entity.setUserId(from.getUserId());

        return storePaymentTransactionRepository.save(entity).toModel();
    }

    @Override
    public Optional<StorePaymentTransaction> findTransactionForRollback(String orderNumber, List<StoreEventType> eventTypes) {
        return storePaymentTransactionRepository.findByOrderNumberAndEventTypeIn(orderNumber, eventTypes)
                .flatMap(transactions -> transactions.stream().max(Comparator.comparing(StorePaymentTransactionEntity::getCreateOn)))
                .map(StorePaymentTransactionEntity::toModel);
    }
}
