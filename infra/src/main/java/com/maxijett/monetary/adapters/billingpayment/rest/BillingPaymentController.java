package com.maxijett.monetary.adapters.billingpayment.rest;

import com.maxijett.monetary.adapters.billingpayment.rest.dto.BillingPaymentDTO;
import com.maxijett.monetary.adapters.billingpayment.rest.dto.BillingPaymentPrePaidDTO;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentCreate;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentPrePaidCreate;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/billing-payment")
public class BillingPaymentController {

  private final UseCaseHandler<BillingPayment, BillingPaymentCreate> getPaidBillingPaymentFromStoreByStoreChainAdmin;

  private final UseCaseHandler<BillingPayment, BillingPaymentPrePaidCreate> getPaidBillingPaymentFromColdStoreByDriver;
  @PostMapping("/by-admin")
  public ResponseEntity<BillingPayment> createBillingPaymentByStoreChainAdmin(@RequestBody BillingPaymentDTO billingPaymentDTO){
    log.info("REST request post to createBillingPaymentByStoreChainAdmin with billingPaymentDTO : {}", billingPaymentDTO);
    return new ResponseEntity<BillingPayment>(getPaidBillingPaymentFromStoreByStoreChainAdmin.handle(billingPaymentDTO.toUseCase()), HttpStatus.CREATED);

  }

  @PostMapping("/cold-store-by-driver")
  public ResponseEntity<BillingPayment> createBillingPaymentFromColdStoreByDriver(@RequestBody BillingPaymentPrePaidDTO billingPaymentPrePaidDTO){
    log.info("REST request post to createBillingPaymentFromColdStoreByDriver with billingPaymentPrePaidDTO : {}", billingPaymentPrePaidDTO);
    return new ResponseEntity<BillingPayment>(getPaidBillingPaymentFromColdStoreByDriver.handle(billingPaymentPrePaidDTO.toUseCase()), HttpStatus.CREATED);
  }

}
