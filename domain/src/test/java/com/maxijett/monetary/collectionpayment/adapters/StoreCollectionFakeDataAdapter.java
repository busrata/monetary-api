package com.maxijett.monetary.collectionpayment.adapters;

import com.maxijett.monetary.store.model.StoreCollection;
import com.maxijett.monetary.store.model.enumeration.TariffType;
import com.maxijett.monetary.store.port.StoreCollectionPort;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class StoreCollectionFakeDataAdapter implements StoreCollectionPort {

  List<StoreCollection> storeList = new ArrayList<>();

  @Override
  public StoreCollection retrieve(Long storeId){
    return StoreCollection.builder()
        .storeId(storeId)
        .cash(new BigDecimal(55))
        .pos(new BigDecimal(100))
        .clientId(20L)
        .tariffType(TariffType.TAXIMETER_HOT)
        .build();
  }

  @Override
  public StoreCollection update(StoreCollection storeCollection){
    storeList.add(storeCollection);
    return storeCollection;
  }

  public void assertContains(StoreCollection... storeCollections){
    assertThat(storeList).containsAnyElementsOf(List.of(storeCollections));
  }

}
