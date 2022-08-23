package com.maxijett.monetary.billingpayment.usecase;

import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.common.model.UseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingPaymentDelete implements UseCase {

  private Long id;

  private String payingAccount;

  private PayloadType payloadType;

  private Boolean isDeleted;


}
