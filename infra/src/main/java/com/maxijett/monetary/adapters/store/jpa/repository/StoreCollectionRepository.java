package com.maxijett.monetary.adapters.store.jpa.repository;

import com.maxijett.monetary.adapters.store.jpa.entity.StoreCollectionEntity;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreCollectionRepository extends JpaRepository<StoreCollectionEntity, Long> {

    Optional<StoreCollectionEntity> findByStoreId(Long storeId);

    List<StoreCollectionEntity> findAllByClientId(Long clientId);

    List<StoreCollectionEntity> findAllByGroupId(Long clientId);
}
