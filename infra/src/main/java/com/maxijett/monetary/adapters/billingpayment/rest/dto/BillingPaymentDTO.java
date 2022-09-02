package com.maxijett.monetary.adapters.billingpayment.rest.dto;

import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.model.enumeration.PaymentType;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  private ZonedDateTime createOn;

  private Long groupId;

  public BillingPaymentCreate toUseCase(){
      return BillingPaymentCreate.builder()
          .amount(getAmount())
          .paymentType(getPaymentType())
          .payloadType(getPayloadType())
          .payingAccount(getPayingAccount())
          .clientId(getClientId())
          .storeId(getStoreId())
          .createOn(getCreateOn())
          .groupId(getGroupId())
          .build();
  }

}
