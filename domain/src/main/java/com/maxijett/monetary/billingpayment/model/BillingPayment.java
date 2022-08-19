package com.maxijett.monetary.billingpayment.model;

import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingPayment {

  private Long id;

  private Long storeId;

  private BigDecimal amount;

  private PaymentType paymentType;

  private String payingAccount;

  private PayloadType payloadType;

  private Long clientId;
}
