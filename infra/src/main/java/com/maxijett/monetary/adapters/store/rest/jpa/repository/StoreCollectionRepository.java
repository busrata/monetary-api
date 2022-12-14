package com.maxijett.monetary.adapters.store.rest.jpa.repository;

import com.maxijett.monetary.adapters.store.rest.jpa.entity.StoreCollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreCollectionRepository extends JpaRepository<StoreCollectionEntity, Long> {

    Optional<StoreCollectionEntity> findByStoreId(Long storeId);

    List<StoreCollectionEntity> findAllByClientId(Long clientId);

    List<StoreCollectionEntity> findAllByGroupId(Long clientId);
}
