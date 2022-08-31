package com.maxijett.monetary.collectionpayment.adapters;

import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;

import java.math.BigDecimal;

import org.apache.commons.lang3.RandomUtils;

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


}
