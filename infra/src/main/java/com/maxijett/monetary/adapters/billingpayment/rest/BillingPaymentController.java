package com.maxijett.monetary.adapters.billingpayment.rest;

import com.maxijett.monetary.adapters.billingpayment.rest.dto.BillingPaymentDTO;
import com.maxijett.monetary.adapters.billingpayment.rest.dto.BillingPaymentDeleteDTO;
import com.maxijett.monetary.adapters.billingpayment.rest.dto.BillingPaymentPrePaidDTO;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.usecase.*;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/billing-payment")
public class BillingPaymentController {

    private final UseCaseHandler<BillingPayment, BillingPaymentCreate> getPaidBillingPaymentFromStoreByStoreChainAdmin;

    private final UseCaseHandler<BillingPayment, BillingPaymentDelete> deletePaidBillingPaymentFromStoreByStoreChainAdmin;

    private final UseCaseHandler<List<BillingPayment>, BillingPaymentListGet> getBillingPaymentListByDateAndGroupId;

    private final UseCaseHandler<BillingPayment, BillingPaymentPrePaidCreate> getPaidBillingPaymentFromColdStoreByDriver;

    private final UseCaseHandler<List<BillingPayment>, BillingPaymentMonthlyByStoreRetrieve> retrieveBillingPaymentMonthlyByStore;

    private final UseCaseHandler<List<BillingPayment>, BillingPaymentDateBetweenByStoreRetrieve> retrieveBillingPaymentListByDateAndStoreId;

    @PostMapping("/by-admin")
    public ResponseEntity<BillingPayment> createBillingPaymentByStoreChainAdmin(@RequestBody BillingPaymentDTO billingPaymentDTO) {
        log.info("REST request post to createBillingPaymentByStoreChainAdmin with billingPaymentDTO: {}", billingPaymentDTO);
        return new ResponseEntity<BillingPayment>(getPaidBillingPaymentFromStoreByStoreChainAdmin.handle(billingPaymentDTO.toUseCase()), HttpStatus.CREATED);

    }

    @PostMapping("/cold-store-by-driver")
    public ResponseEntity<BillingPayment> createBillingPaymentFromColdStoreByDriver(@RequestBody BillingPaymentPrePaidDTO billingPaymentPrePaidDTO) {
        log.info("REST request post to createBillingPaymentFromColdStoreByDriver with billingPaymentPrePaidDTO: {}", billingPaymentPrePaidDTO);
        return new ResponseEntity<BillingPayment>(getPaidBillingPaymentFromColdStoreByDriver.handle(billingPaymentPrePaidDTO.toUseCase()), HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<BillingPayment> deleteBillingPaymentByStoreChainAdmin(@RequestBody BillingPaymentDeleteDTO billingPaymentDeleteDTO) {
        log.info("REST request delete to deleteBillingPayment with billingPaymentDeleteDTO: {}", billingPaymentDeleteDTO);
        return new ResponseEntity<BillingPayment>(deletePaidBillingPaymentFromStoreByStoreChainAdmin.handle(billingPaymentDeleteDTO.toUseCase()), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BillingPayment>> retrieveBillingPaymentsByDateAndGroupId(@RequestParam Long groupId,
                                                                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate requestDate) {
        log.info("Rest request to retrieveBillingPaymentsByDateAndGroupId by group id: {} and date: {}", groupId, requestDate);
        return new ResponseEntity<List<BillingPayment>>(getBillingPaymentListByDateAndGroupId.handle(BillingPaymentListGet.builder()
                .groupId(groupId)
                .createOn(requestDate)
                .build()), HttpStatus.OK);
    }

    @GetMapping("/by-store/{storeId}/monthly")
    public ResponseEntity<List<BillingPayment>> retrieveBillingPaymentMonthlyByStore(@PathVariable Long storeId,
                                                                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate requestDate) {
        log.info("Rest request to retrieveBillingPaymentMonthlyByStore by store id: {} and date: {}", storeId, requestDate);
        return new ResponseEntity<List<BillingPayment>>(retrieveBillingPaymentMonthlyByStore.handle(BillingPaymentMonthlyByStoreRetrieve.builder()
                .storeId(storeId)
                .requestDate(requestDate).build()), HttpStatus.OK);
    }

    @GetMapping("/by-store/{storeId}/date-between")
    public ResponseEntity<List<BillingPayment>> retrieveBillingPaymentListByDateBetweenAndStore(@PathVariable Long storeId,
                                                                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        log.info("Rest request to retrieveBillingPaymentListByDateBetweenAndStore by store id: {} and date between: {} - {}", storeId, startDate, endDate);
        return new ResponseEntity<List<BillingPayment>>(retrieveBillingPaymentListByDateAndStoreId.handle(BillingPaymentDateBetweenByStoreRetrieve.builder()
            .storeId(storeId)
            .startDate(startDate)
            .endDate(endDate).build()), HttpStatus.OK);
    }
}
