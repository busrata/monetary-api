package com.maxijett.monetary.adapters.coshbox.rest.jpa.entity;


import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashBoxEntity {
    private Long id;
    private Long storeChainId;
    private Long groupId;
    private BigDecimal cash;
    private Long clientId;
    private Long userId;
}
