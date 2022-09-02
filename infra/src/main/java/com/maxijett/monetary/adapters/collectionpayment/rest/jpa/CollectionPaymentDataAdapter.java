package com.maxijett.monetary.adapters.collectionpayment.rest.jpa;

import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.entity.CollectionPaymentEntity;
import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.repository.CollectionPaymentRepository;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        entity.setCreateOn(from.getDate());
        entity.setIsDeleted(false);

        return collectionPaymentRepository.save(entity).toModel();
    }

    @Override
    public CollectionPayment retrieve(Long id) {
        return collectionPaymentRepository.findById(id).map(CollectionPaymentEntity::toModel)
                .orElseThrow(() -> new MonetaryApiBusinessException("monetaryapi.collectionpayment.notFound", String.valueOf(id)));
    }

    @Override
    @Transactional
    public CollectionPayment update(Long id){

        collectionPaymentRepository.updateCollectionPaymentIsDeleted(id);
        return retrieve(id);

    }


    @Override
    public List<CollectionPayment> retrieveCollectionPayments(Long driverId, Long groupId, ZonedDateTime startTime, ZonedDateTime endTime) {
        return collectionPaymentRepository.findAllByDriverIdAndGroupIdAndCreateOnBetween(driverId, groupId, startTime, endTime)
                .stream()
                .map(CollectionPaymentEntity::toModel)
                .collect(Collectors.toList());
    }
}
