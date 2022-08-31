package com.maxijett.monetary.adapters.collectionpayment.rest.jpa;

import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.entity.CollectionPaymentEntity;
import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.repository.CollectionPaymentRepository;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        entity.setDate(from.getDate());

        return collectionPaymentRepository.save(entity).toModel();
    }

    @Override
    public List<CollectionPayment> retrieveCollectionPayments(Long driverId, Long groupId, ZonedDateTime startTime, ZonedDateTime endTime) {
        return collectionPaymentRepository.findAllByDriverIdAndGroupIdAndDateBetween(driverId, groupId, startTime, endTime)
                .stream()
                .map(CollectionPaymentEntity::toModel)
                .collect(Collectors.toList());
    }
}
