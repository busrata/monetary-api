package com.maxijett.monetary.adapters.orderpayment.kafka.event;

import com.maxijett.monetary.common.model.Event;
import com.maxijett.monetary.orderpayment.useCase.OrderPayment;
import lombok.*;

import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPaymentEvent implements Event {

    private String orderNumber;

    private Long driverId;

    private Long storeId;

    private BigDecimal cash;

    private BigDecimal pos;

    private Long groupId;

    private Long clientId;


    @Override
    public Object toModel() {
        return null;
    }

    public OrderPayment toUseCase() {
        return OrderPayment.builder()
                .orderNumber(getOrderNumber())
                .cash(getCash())
                .pos(getPos())
                .storeId(getStoreId())
                .clientId(getClientId())
                .driverId(getDriverId())
                .groupId(getGroupId())
                .build();
    }
}
