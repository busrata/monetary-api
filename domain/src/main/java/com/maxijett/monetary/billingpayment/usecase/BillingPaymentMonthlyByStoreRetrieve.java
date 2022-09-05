package com.maxijett.monetary.billingpayment.usecase;

import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingPaymentMonthlyByStoreRetrieve implements UseCase {

    private Long storeId;

    private LocalDate requestDate;

}
