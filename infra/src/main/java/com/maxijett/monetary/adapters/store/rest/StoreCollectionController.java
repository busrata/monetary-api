package com.maxijett.monetary.adapters.store.rest;

import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.store.model.StoreCashAndBalanceLimit;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.usecase.StoreCollectionRetrieve;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store-collection")
public class StoreCollectionController {

    private final UseCaseHandler<StoreCollection, StoreCollectionRetrieve> handler;
    private final UseCaseHandler<List<StoreCollection>, StoreCollectionRetrieve> storeCollectionRetrieveByStoreUseCaseHandler;
    private final UseCaseHandler<List<StoreCashAndBalanceLimit>, StoreCollectionRetrieve> storeCashAndBalanceLimitRetrieveByGroupUseCaseHandler;

    @GetMapping("/by-id/{id}")
    public ResponseEntity<StoreCollection> getStoreCollectionById(@PathVariable("id") Long id) {
        log.info("REST request to get storeCollection by id:{}", id);
        return new ResponseEntity<StoreCollection>(handler.handle(toUseCase(id)), HttpStatus.OK);
    }

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

    private StoreCollectionRetrieve toUseCase(Long id) {
        return StoreCollectionRetrieve.builder().id(id).build();
    }

}
