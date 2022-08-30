package com.maxijett.monetary.adapters.collectionpayment.rest.jpa;

import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.entity.CollectionPaymentEntity;
import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.repository.CollectionPaymentRepository;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

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
        entity.setCash(Objects.isNull(from.getCash()) ? BigDecimal.valueOf(0.00) : from.getCash());
        entity.setStoreId(from.getStoreId());
        entity.setPos(Objects.isNull(from.getPos()) ? BigDecimal.valueOf(0.00) : from.getPos());
        entity.setDate(from.getDate());

        return collectionPaymentRepository.save(entity).toModel();
    }

    @Override
    public CollectionPayment retrieve(Long id){

        return collectionPaymentRepository.findById(id).orElseThrow(NullPointerException::new).toModel();

    }

    @Override
    public CollectionPayment update(Long id){

        collectionPaymentRepository.updateCollectionPaymentIsDeleted(id);
        return retrieve(id);

    }

}
