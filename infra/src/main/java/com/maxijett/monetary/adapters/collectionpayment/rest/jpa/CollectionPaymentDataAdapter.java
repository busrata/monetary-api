package com.maxijett.monetary.adapters.collectionpayment.rest.jpa;

import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.entity.CollectionPaymentEntity;
import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.repository.CollectionPaymentRepository;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollectionPaymentDataAdapter implements CollectionPaymentPort {

    private final CollectionPaymentRepository collectionPaymentRepository;

    @Override
    public CollectionPayment create(CollectionPaymentCreate from) {

        var entity = new CollectionPaymentEntity();
        entity.setDriverId(from.getDriverId());
        entity.setGroupId(from.getGroupId());
        entity.setClientId(from.getClientId());
        entity.setCash(from.getCash());
        entity.setStoreId(from.getStoreId());
        entity.setPos(from.getPos());
        entity.setDate(from.getDate());

        return collectionPaymentRepository.save(entity).toModel();
    }
}
