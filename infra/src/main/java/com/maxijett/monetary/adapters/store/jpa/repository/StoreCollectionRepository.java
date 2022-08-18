package com.maxijett.monetary.adapters.store.jpa.repository;

import com.maxijett.monetary.adapters.store.jpa.entity.StoreCollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreCollectionRepository extends JpaRepository<StoreCollectionEntity, Long> {
    StoreCollectionEntity findByStoreId(Long storeId);
}
