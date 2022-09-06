package com.maxijett.monetary.adapters.store.rest;

import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.store.model.StoreCashAndBalanceLimit;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.useCase.StoreCollectionRetrieve;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store-collection")
public class StoreCollectionController {

    private final UseCaseHandler<List<StoreCollection>, StoreCollectionRetrieve> storeCollectionRetrieveByStoreUseCaseHandler;

    private final UseCaseHandler<List<StoreCashAndBalanceLimit>, StoreCollectionRetrieve> storeCashAndBalanceLimitRetrieveByGroupUseCaseHandler;

    @GetMapping
    public ResponseEntity<List<StoreCollection>> retrieveStoreCollection(
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) Long groupId) {

        log.info("Rest request retrieveStoreCollection list with clientId: {} or groupId: {}",
                clientId,
                groupId);

        return new ResponseEntity<>(
                storeCollectionRetrieveByStoreUseCaseHandler.handle(
                        StoreCollectionRetrieve.builder()
                                .clientId(clientId)
                                .groupId(groupId)
                                .build()),
                HttpStatus.OK);
    }

    @GetMapping("/cash-and-balance-limit")
    public ResponseEntity<List<StoreCashAndBalanceLimit>> retrieveStoreCashAndBalanceLimitByGroup(
            @RequestParam(required = false) Long groupId) {

        log.info("Rest request retrieveStoreCashAndBalanceLimitByGroup list with groupId: {}",
                groupId);

        List<StoreCashAndBalanceLimit> storeCashAndBalanceLimits = storeCashAndBalanceLimitRetrieveByGroupUseCaseHandler.handle(
                StoreCollectionRetrieve.builder()
                        .groupId(groupId)
                        .build());

        return new ResponseEntity<>(storeCashAndBalanceLimits,
                HttpStatus.OK);
    }

}
