package com.maxijett.monetary.collectionpayment.adapters;

import com.maxijett.monetary.store.model.StorePaymentTransaction;
import com.maxijett.monetary.store.port.StorePaymentTransactionPort;
import java.util.ArrayList;
import java.util.List;

public class StorePaymentTransactionFakeDataAdapter implements StorePaymentTransactionPort {

  public List<StorePaymentTransaction> transactionList = new ArrayList<>();

  @Override
  public StorePaymentTransaction create(StorePaymentTransaction storePaymentTransaction){
    transactionList.add(storePaymentTransaction);
    return  storePaymentTransaction;
  }

}
