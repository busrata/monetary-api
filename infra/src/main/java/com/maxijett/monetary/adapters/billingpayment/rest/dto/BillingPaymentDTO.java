package com.maxijett.monetary.adapters.billingpayment.rest.dto;

import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillingPaymentDTO {

  private Long storeId;

  private BigDecimal amount;

  private PaymentType paymentType;

  private String payingAccount;

  private PayloadType payloadType;

  private Long clientId;

  public BillingPaymentCreate toUseCase(){
      return BillingPaymentCreate.builder()
          .amount(getAmount())
          .paymentType(getPaymentType())
          .payloadType(getPayloadType())
          .payingAccount(getPayingAccount())
          .clientId(getClientId())
          .storeId(getStoreId())
          .build();
  }

}
