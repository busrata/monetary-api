package com.maxijett.monetary.adapters.collectionpayment.rest;

import com.maxijett.monetary.adapters.collectionpayment.rest.dto.CollectionPaymentDTO;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentDelete;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/collection-payment")
public class CollectionPaymentController {

    private final UseCaseHandler<CollectionPayment, CollectionPaymentCreate> payCollectionPaymentToStoreByDriverUseCaseHandler;

    private final UseCaseHandler<CollectionPayment, CollectionPaymentCreate> payCollectionPaymentToStoreByStoreChainAdminUseCaseHandler;

    private final UseCaseHandler<CollectionPayment, CollectionPaymentDelete> deleteCollectionPaymentByStoreChainAdminUseCaseHandler;

    @PostMapping("/by-driver")
    public ResponseEntity<CollectionPayment> saveCollectionPaymentByDriver(@RequestBody CollectionPaymentDTO collectionPaymentDTO) {
        return new ResponseEntity<>(payCollectionPaymentToStoreByDriverUseCaseHandler.handle(collectionPaymentDTO.toUseCase()),
                HttpStatus.OK);
    }

    @PostMapping("/by-store-chain-admin")
    public ResponseEntity<CollectionPayment> saveCollectionPaymentByStoreChainAdmin(@RequestBody CollectionPaymentDTO collectionPaymentDTO) {
        return new ResponseEntity<>(payCollectionPaymentToStoreByStoreChainAdminUseCaseHandler.handle(collectionPaymentDTO.toUseCase()),
                HttpStatus.OK);
    }

    @PutMapping("/delete")
    public  ResponseEntity<CollectionPayment> deleteCollectionPaymentByStoreChainAdmin(@RequestParam Long id){
        return new ResponseEntity<>(deleteCollectionPaymentByStoreChainAdminUseCaseHandler.handle(CollectionPaymentDelete.fromModel(id)),HttpStatus.OK );
    }

}
