package com.maxijett.monetary.adapters.collectionpayment.rest;

import com.maxijett.monetary.adapters.collectionpayment.rest.dto.CollectionPaymentDTO;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentCreate;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/collection-payment")
public class CollectionPaymentController {

  private final UseCaseHandler<CollectionPayment, CollectionPaymentCreate> payCollectionPaymentToStoreByDriverUseCaseHandler;


  @PostMapping("/by-driver")
  public ResponseEntity<CollectionPayment> saveCollectionPaymentByDriver(CollectionPaymentDTO collectionPaymentDTO){
     return new ResponseEntity<>(payCollectionPaymentToStoreByDriverUseCaseHandler.handle(collectionPaymentDTO.toUseCase()),
         HttpStatus.OK);
  }


}
