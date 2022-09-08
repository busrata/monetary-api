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
    private Long driverId;
    private BigDecimal cash;
    private BigDecimal pos;
    private ZonedDateTime date;
    private Long groupId;
    private Long clientId;

    public CollectionPaymentCreate toUseCase() {
        return CollectionPaymentCreate.builder()
                .pos(getPos())
                .cash(getCash())
                .storeId(getStoreId())
                .driverId(getDriverId())
                .date(getDate())
                .clientId(getClientId())
                .groupId(getGroupId()).build();
    }

}
