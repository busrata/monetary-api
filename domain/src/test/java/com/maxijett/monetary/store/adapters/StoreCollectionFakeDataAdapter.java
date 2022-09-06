package com.maxijett.monetary.store.adapters;

import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.model.enumeration.TariffType;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class StoreCollectionFakeDataAdapter implements StoreCollectionPort {

  public List<StoreCollection> storeCollectionList = new ArrayList<>();

  @Override
  public StoreCollection retrieve(Long storeId) {
    return StoreCollection.builder()
        .storeId(storeId)
        .cash(new BigDecimal(55))
        .pos(new BigDecimal(100))
        .clientId(20L)
        .groupId(1L)
        .tariffType(TariffType.TAXIMETER_HOT)
        .build();
  }

  @Override
  public List<StoreCollection> getListByClientId(Long groupId) {
    return List.of(StoreCollection.builder()
            .storeId(1L)
            .cash(new BigDecimal(55))
            .pos(new BigDecimal(100))
            .clientId(20L)
            .groupId(1L)
            .tariffType(TariffType.TAXIMETER_HOT)
            .build(),
        StoreCollection.builder()
            .storeId(2L)
            .cash(new BigDecimal(65))
            .pos(new BigDecimal(90))
            .clientId(20L)
            .groupId(1L)
            .tariffType(TariffType.TAXIMETER_HOT)
            .build(),
        StoreCollection.builder()
            .storeId(3L)
            .cash(new BigDecimal(75))
            .pos(new BigDecimal(85))
            .clientId(20L)
            .groupId(1L)
            .tariffType(TariffType.TAXIMETER_HOT)
            .build());
  }
  @Override
  public List<StoreCollection> getListByGroupId(Long groupId) {
    return List.of(StoreCollection.builder()
            .storeId(1L)
            .cash(new BigDecimal(55))
            .pos(new BigDecimal(100))
            .clientId(20L)
            .groupId(1L)
            .tariffType(TariffType.TAXIMETER_HOT)
            .balanceLimit(new BigDecimal(45))
            .build(),
        StoreCollection.builder()
            .storeId(2L)
            .cash(new BigDecimal(65))
            .pos(new BigDecimal(90))
            .clientId(20L)
            .groupId(1L)
            .tariffType(TariffType.TAXIMETER_HOT)
            .balanceLimit(new BigDecimal(55))
            .build(),
        StoreCollection.builder()
            .storeId(3L)
            .cash(new BigDecimal(75))
            .pos(new BigDecimal(85))
            .clientId(20L)
            .groupId(1L)
            .tariffType(TariffType.TAXIMETER_HOT)
            .balanceLimit(new BigDecimal(65))
            .build());
  }


  @Override
  public StoreCollection update(StoreCollection storeCollection,
      StorePaymentTransaction storePaymentTransaction) {
    storeCollectionList.add(storeCollection);
    return storeCollection;
  }

  public void assertContains(StoreCollection... storeCollections) {
    assertThat(storeCollectionList).containsAnyElementsOf(List.of(storeCollections));
  }

}
