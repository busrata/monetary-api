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
                .createOn(collectionPaymentCreate.getDate())
                .groupId(collectionPaymentCreate.getGroupId())
                .clientId(collectionPaymentCreate.getClientId())
                .storeId(collectionPaymentCreate.getStoreId())
                .isDeleted(false)
                .driverId(collectionPaymentCreate.getDriverId()).build();
    }

    @Override
    public CollectionPayment retrieve(Long id) {

        return CollectionPayment.builder()
                .id(id)
                .cash(BigDecimal.ZERO)
                .pos(BigDecimal.TEN)
                .clientId(20000L)
                .groupId(20L)
                .storeId(200L)
                .isDeleted(false)
                .build();
    }

    @Override
    public CollectionPayment update(Long id) {

        return CollectionPayment.builder()
                .id(id)
                .cash(BigDecimal.ZERO)
                .pos(BigDecimal.TEN)
                .clientId(20000L)
                .groupId(20L)
                .storeId(200L)
                .isDeleted(true)
                .build();
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
                        .createOn(ZonedDateTime.now())
                        .build(),
                CollectionPayment.builder()
                        .driverId(driverId)
                        .groupId(groupId)
                        .storeId(24L)
                        .clientId(20000L)
                        .pos(BigDecimal.ZERO)
                        .cash(BigDecimal.valueOf(45))
                        .createOn(ZonedDateTime.now())
                        .build()
        );
    }

    @Override
    public List<CollectionPayment> retrieveCollectionPaymentListByGroupIdAndDates(Long groupId, ZonedDateTime startTime, ZonedDateTime endTime) {
        return List.of(
                CollectionPayment.builder()
                        .groupId(groupId)
                        .storeId(23L)
                        .clientId(20000L)
                        .pos(BigDecimal.valueOf(50L))
                        .cash(BigDecimal.valueOf(34))
                        .createOn(ZonedDateTime.parse("2022-09-02T09:00:00.000Z"))
                        .build(),
                CollectionPayment.builder()
                        .groupId(groupId)
                        .storeId(24L)
                        .clientId(20000L)
                        .pos(BigDecimal.valueOf(50L))
                        .cash(BigDecimal.valueOf(45))
                        .createOn(ZonedDateTime.parse("2022-09-02T11:00:00.000Z"))
                        .build()
        );
    }

    @Override
    public List<CollectionPayment> retrieveCollectionPaymentMonthlyByStore(Long storeId, ZonedDateTime startTime) {
        return List.of(
                CollectionPayment.builder()
                        .driverId(315L)
                        .groupId(50L)
                        .storeId(storeId)
                        .clientId(20000L)
                        .pos(BigDecimal.ZERO)
                        .cash(BigDecimal.valueOf(34))
                        .createOn(ZonedDateTime.now())
                        .build(),
                CollectionPayment.builder()
                        .driverId(315L)
                        .groupId(50L)
                        .storeId(storeId)
                        .clientId(20000L)
                        .pos(BigDecimal.ZERO)
                        .cash(BigDecimal.valueOf(45))
                        .createOn(ZonedDateTime.now())
                        .build()
        );
    }


}
