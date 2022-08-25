package com.maxijett.monetary.integration;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.store.jpa.StoreCollectionDataAdapter;
import com.maxijett.monetary.adapters.store.jpa.repository.StoreCollectionRepository;

import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.StoreEventType;
import com.maxijett.monetary.store.model.enumeration.TariffType;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@IT
@Sql(scripts = "classpath:sql/store-collection.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StoreCollectionDataAdapterTest extends AbstractIT {

  @Autowired
  StoreCollectionDataAdapter storeCollectionDataAdapter;

  @Autowired
  StoreCollectionRepository storeCollectionRepository;

  @Test
  public void shouldRetrieveStoreCollectionPayment(){

    StoreCollection storeCollection = storeCollectionDataAdapter.retrieve(200L);

    Assertions.assertNotNull(storeCollection);
    assertEquals(200L, storeCollection.getStoreId());
    assertEquals(20000, storeCollection.getClientId());
    assertEquals(20, storeCollection.getGroupId());
  }

  @Test
  public void shouldUpdateStoreCollectionPayment(){

    StoreCollection storeCollection = StoreCollection.builder()
        .storeId(200L)
        .groupId(20L)
        .cash(BigDecimal.valueOf(30))
        .pos(BigDecimal.valueOf(150))
        .tariffType(TariffType.TAXIMETER_HOT)
        .clientId(20000L)
        .build();

    StorePaymentTransaction storePaymentTransaction = StorePaymentTransaction.builder()
        .storeId(200L)
        .clientId(20000L)
        .eventType(StoreEventType.PACKAGE_DELIVERED)
        .cash(BigDecimal.valueOf(30))
        .pos(BigDecimal.valueOf(10))
        .date(ZonedDateTime.now(ZoneId.of("UTC")))
        .build();

    StoreCollection response = storeCollectionDataAdapter.update(storeCollection, storePaymentTransaction);

    assertEquals(storeCollection.getStoreId(), response.getStoreId());
    assertEquals(storeCollection.getCash(), response.getCash());
    assertEquals(storeCollection.getPos(), response.getPos());
  }

}
