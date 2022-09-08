package com.maxijett.monetary.orderpayment.useCase;

import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayment implements UseCase {

    private String orderNumber;

    private Long driverId;

    private Long storeId;

    private BigDecimal cash;

    private BigDecimal pos;

    private Long groupId;

    private Long clientId;

}
