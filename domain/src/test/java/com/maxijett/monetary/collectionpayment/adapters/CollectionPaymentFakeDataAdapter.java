package com.maxijett.monetary.collectionpayment.adapters;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public class CollectionPaymentFakeDataAdapter implements CollectionPaymentPort {


    @Override
    public CollectionPayment create(CollectionPaymentCreate collectionPaymentCreate) {
        return CollectionPayment.builder()
                .id(RandomUtils.nextLong())
                .cash(collectionPaymentCreate.getCash())
                .pos(collectionPaymentCreate.getPos())
                .date(collectionPaymentCreate.getDate())
                .groupId(collectionPaymentCreate.getGroupId())
                .clientId(collectionPaymentCreate.getClientId())
                .storeId(collectionPaymentCreate.getStoreId())
                .driverId(collectionPaymentCreate.getDriverId()).build();
    }

    @Override
    public List<CollectionPayment> retrieveCollectionPayments(Long driverId, Long groupId, ZonedDateTime startTime, ZonedDateTime endTime) {
        return List.of(
                CollectionPayment.builder()
                        .driverId(driverId)
                        .groupId(groupId)
                        .storeId(23L)
                        .clientId(20000L)
                        .pos(BigDecimal.ZERO)
                        .cash(BigDecimal.valueOf(34))
                        .date(ZonedDateTime.now())
                        .build(),
                CollectionPayment.builder()
                        .driverId(driverId)
                        .groupId(groupId)
                        .storeId(24L)
                        .clientId(20000L)
                        .pos(BigDecimal.ZERO)
                        .cash(BigDecimal.valueOf(45))
                        .date(ZonedDateTime.now())
                        .build()
        );
    }
}
