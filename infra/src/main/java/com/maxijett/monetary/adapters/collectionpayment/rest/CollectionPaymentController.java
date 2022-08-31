package com.maxijett.monetary.adapters.collectionpayment.rest;

import com.maxijett.monetary.adapters.collectionpayment.rest.dto.CollectionPaymentDTO;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import com.maxijett.monetary.collectionpayment.useCase.PaidToTheStoreCollectionPaymentRetrieve;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentDelete;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/collection-payment")
public class CollectionPaymentController {

    private final UseCaseHandler<CollectionPayment, CollectionPaymentCreate> payCollectionPaymentToStoreByDriverUseCaseHandler;

    private final UseCaseHandler<CollectionPayment, CollectionPaymentCreate> payCollectionPaymentToStoreByStoreChainAdminUseCaseHandler;

    private final UseCaseHandler<CollectionPayment, CollectionPaymentDelete> deleteCollectionPaymentByStoreChainAdminUseCaseHandler;
    private final UseCaseHandler<List<CollectionPayment>, PaidToTheStoreCollectionPaymentRetrieve> paidToTheStoreCollectionPaymentRetrieveUseCaseHandler;


    @PostMapping("/by-driver")
    public ResponseEntity<CollectionPayment> saveCollectionPaymentByDriver(@RequestBody CollectionPaymentDTO collectionPaymentDTO) {
        return new ResponseEntity<>(payCollectionPaymentToStoreByDriverUseCaseHandler.handle(collectionPaymentDTO.toUseCase()),
                HttpStatus.OK);
    }

    @GetMapping("by-driver/{driverId}")
    public ResponseEntity<List<CollectionPayment>> getPayTheStoreCollectionPaymentByDriver(@PathVariable Long driverId, @RequestParam Long groupId,
                                                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        log.info("Rest request to get getPayTheStoreCollectionPaymentByDriver by driverId: {}, groupId: {}, startDate: {}, endDate: {}", driverId, groupId, startDate, endDate);
        return ResponseEntity.ok(paidToTheStoreCollectionPaymentRetrieveUseCaseHandler.handle(PaidToTheStoreCollectionPaymentRetrieve.builder()
                .driverId(driverId).groupId(groupId).startDate(startDate).endDate(endDate).build()));

    }

    @PostMapping("/by-store-chain-admin")
    public ResponseEntity<CollectionPayment> saveCollectionPaymentByStoreChainAdmin(@RequestBody CollectionPaymentDTO collectionPaymentDTO) {
        return new ResponseEntity<>(payCollectionPaymentToStoreByStoreChainAdminUseCaseHandler.handle(collectionPaymentDTO.toUseCase()),
                HttpStatus.OK);
    }

    @PutMapping("/delete")
    public  ResponseEntity<CollectionPayment> deleteCollectionPaymentByStoreChainAdmin(@RequestParam Long id){
        log.info("REST request delete to deleteCollectionPaymentByStoreChainAdmin with storeChainId : {}", id);
        return new ResponseEntity<>(deleteCollectionPaymentByStoreChainAdminUseCaseHandler.handle(CollectionPaymentDelete.fromModel(id)),HttpStatus.OK );
    }

}
