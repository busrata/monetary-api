package com.maxijett.monetary.adapters.store.rest;

import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.usecase.StoreCollectionRetrieve;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store-collection")
public class StoreCollectionController {

    private final UseCaseHandler<StoreCollection, StoreCollectionRetrieve> handler;

    @GetMapping("/by-id/{id}")
    public ResponseEntity<StoreCollection> getStoreCollectionById(@PathVariable("id") Long id) {
        log.info("REST request to get storeCollection by id:{}", id);
        return new ResponseEntity<StoreCollection>(handler.handle(toUseCase(id)), HttpStatus.OK);
    }

    private StoreCollectionRetrieve toUseCase(Long id) {
        return StoreCollectionRetrieve.builder().id(id).build();
    }

}
