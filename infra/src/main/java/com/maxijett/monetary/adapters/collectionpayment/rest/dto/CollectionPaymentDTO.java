package com.maxijett.monetary.adapters.collectionpayment.rest.dto;

import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionPaymentDTO {

    private Long id;
    private Long storeId;
    private Long courierId;
    private BigDecimal paymentCash;
    private BigDecimal paymentPos;
    private ZonedDateTime date;
    private Long groupId;
    private Long clientId;

    public CollectionPaymentCreate toUseCase() {
        return CollectionPaymentCreate.builder()
                .pos(getPaymentPos())
                .cash(getPaymentCash())
                .storeId(getStoreId())
                .driverId(getCourierId())
                .date(getDate())
                .clientId(getClientId())
                .groupId(getGroupId()).build();
    }

}
