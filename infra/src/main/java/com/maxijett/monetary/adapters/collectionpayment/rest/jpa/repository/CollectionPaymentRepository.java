package com.maxijett.monetary.adapters.collectionpayment.rest.jpa.repository;

import com.maxijett.monetary.adapters.collectionpayment.rest.jpa.entity.CollectionPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface CollectionPaymentRepository extends JpaRepository<CollectionPaymentEntity, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update CollectionPaymentEntity c set c.isDeleted = true where c.id =:id")
    int updateCollectionPaymentIsDeleted(@Param("id") Long id);


    List<CollectionPaymentEntity> findAllByDriverIdAndGroupIdAndDateBetween(Long driverId, Long groupId, ZonedDateTime startDateTime, ZonedDateTime endDateTime);

}
