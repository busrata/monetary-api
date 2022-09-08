package com.maxijett.monetary.adapters.collectionpayment.rest;

import com.maxijett.monetary.adapters.collectionpayment.rest.dto.CollectionPaymentDTO;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentListGet;
import com.maxijett.monetary.collectionpayment.useCase.PaidToTheStoreCollectionPaymentRetrieve;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentDelete;
import com.maxijett.monetary.collectionpayment.useCase.StoreCollectionPaymentRetrieve;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentRetrieveByDateRangeAndStore;
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
@RequestMapping("/api/v1/collection-payment")
public class CollectionPaymentController {

    private final UseCaseHandler<CollectionPayment, CollectionPaymentCreate> payCollectionPaymentToStoreByDriverUseCaseHandler;

    private final UseCaseHandler<CollectionPayment, CollectionPaymentCreate> payCollectionPaymentToStoreByStoreChainAdminUseCaseHandler;

    private final UseCaseHandler<CollectionPayment, CollectionPaymentDelete> deleteCollectionPaymentByStoreChainAdminUseCaseHandler;

    private final UseCaseHandler<List<CollectionPayment>, PaidToTheStoreCollectionPaymentRetrieve> paidToTheStoreCollectionPaymentRetrieveUseCaseHandler;

    private final UseCaseHandler<List<CollectionPayment>, StoreCollectionPaymentRetrieve> retrieveCollectionPaymentMonthlyByStoreUseCaseHandler;

    private final UseCaseHandler<List<CollectionPayment>, CollectionPaymentListGet> getAllCollectionPaymentsByGroupIdAndDateUseCaseHandler;


    private final UseCaseHandler<List<CollectionPayment>, CollectionPaymentRetrieveByDateRangeAndStore> getAllCollectionPaymentsByStoreIdAndDateUseCaseHandler;

    @PostMapping("/by-driver")
    public ResponseEntity<CollectionPayment> saveCollectionPaymentByDriver(@RequestBody CollectionPaymentDTO collectionPaymentDTO) {
        log.info("REST request saveCollectionPaymentByDriver collectionPaymentDTO: {}", collectionPaymentDTO);
        return new ResponseEntity<>(payCollectionPaymentToStoreByDriverUseCaseHandler.handle(collectionPaymentDTO.toUseCase()),
                HttpStatus.OK);
    }

    @GetMapping("by-driver/{driverId}")
    public ResponseEntity<List<CollectionPayment>> getPayTheStoreCollectionPaymentByDriver(@PathVariable Long driverId, @RequestParam Long groupId,
                                                                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        log.info("Rest request to get getPayTheStoreCollectionPaymentByDriver by driverId: {}, groupId: {}, startDate: {}, endDate: {}", driverId, groupId, startDate, endDate);
        return ResponseEntity.ok(paidToTheStoreCollectionPaymentRetrieveUseCaseHandler.handle(PaidToTheStoreCollectionPaymentRetrieve.builder()
                .driverId(driverId).groupId(groupId).startDate(startDate).endDate(endDate).build()));

    }

    @PostMapping("/by-store-chain-admin")
    public ResponseEntity<CollectionPayment> saveCollectionPaymentByStoreChainAdmin(@RequestBody CollectionPaymentDTO collectionPaymentDTO) {
        log.info("REST request saveCollectionPaymentByStoreChainAdmin collectionPaymentDTO: {}", collectionPaymentDTO);
        return new ResponseEntity<>(payCollectionPaymentToStoreByStoreChainAdminUseCaseHandler.handle(collectionPaymentDTO.toUseCase()),
                HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<CollectionPayment> deleteCollectionPaymentByStoreChainAdmin(@RequestParam Long id) {
        log.info("REST request delete to deleteCollectionPaymentByStoreChainAdmin with storeChainId: {}", id);
        return new ResponseEntity<>(deleteCollectionPaymentByStoreChainAdminUseCaseHandler.handle(CollectionPaymentDelete.fromModel(id)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CollectionPayment>> retrieveCollectionPaymentsByDateAndGroupId(@RequestParam Long groupId,
                                                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime requestDate) {
        log.info("Rest request to retrieveCollectionPaymentsByDateAndGroupId by groupId: {}, requestDate: {}", groupId, requestDate);
        return new ResponseEntity<>(getAllCollectionPaymentsByGroupIdAndDateUseCaseHandler.handle(CollectionPaymentListGet.builder()
                .groupId(groupId)
                .createOn(requestDate)
                .build()), HttpStatus.OK);
    }

    @GetMapping("by-store/{storeId}/monthly")
    public ResponseEntity<List<CollectionPayment>> getCollectionPaymentMonthlyByStore(@PathVariable Long storeId,
                                                                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate requestDate) {
        log.info("Rest request to getCollectionPaymentMonthlyByStore by storeId: {}, requestDate: {}", storeId, requestDate);

        return new ResponseEntity<>(retrieveCollectionPaymentMonthlyByStoreUseCaseHandler.handle(StoreCollectionPaymentRetrieve.fromModel(storeId, requestDate)), HttpStatus.OK);

    }

    @GetMapping("/all/{storeId}")
    public ResponseEntity<List<CollectionPayment>> getCollectionPaymentByDateAndStoreId(@PathVariable Long storeId,
                                                                                        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstDate, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate lastDate) {
        log.info("Rest request to getCollectionPaymentByDateAndStoreId by storeId: {}, firstDate: {}, lastDate: {}", storeId, firstDate, lastDate);

        return new ResponseEntity<>(getAllCollectionPaymentsByStoreIdAndDateUseCaseHandler.handle(CollectionPaymentRetrieveByDateRangeAndStore.builder()
                .storeId(storeId)
                .firstDate(firstDate)
                .lastDate(lastDate)
                .build()), HttpStatus.OK);
    }

}
