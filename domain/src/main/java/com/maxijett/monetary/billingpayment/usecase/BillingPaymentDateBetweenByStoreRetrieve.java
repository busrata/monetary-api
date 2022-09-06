package com.maxijett.monetary.billingpayment.usecase;

import com.maxijett.monetary.common.model.UseCase;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingPaymentDateBetweenByStoreRetrieve implements UseCase {

    Long storeId;

    LocalDate startDate;

    LocalDate endDate;
}
