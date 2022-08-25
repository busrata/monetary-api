package com.maxijett.monetary.billingpayment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.maxijett.monetary.billingpayment.adapters.BillingPaymentPortFakeDataAdapter;
import com.maxijett.monetary.billingpayment.model.BillingPayment;
import com.maxijett.monetary.billingpayment.model.enumeration.PayloadType;
import com.maxijett.monetary.billingpayment.port.BillingPaymentPort;
import com.maxijett.monetary.billingpayment.usecase.BillingPaymentDelete;
import com.maxijett.monetary.cashbox.adapters.CashBoxFakeDataAdapter;
import com.maxijett.monetary.store.adapters.StoreCollectionFakeDataAdapter;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeletePaidBillingPaymentFromStoreByStoreChainAdminTest {

  DeletePaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler handler ;

  BillingPaymentPort billingPaymentPort = new BillingPaymentPortFakeDataAdapter();

  StoreCollectionFakeDataAdapter storeCollectionPort;

  CashBoxFakeDataAdapter cashBoxPort;


  @BeforeEach
  public void setUp(){

    storeCollectionPort = new StoreCollectionFakeDataAdapter();

    cashBoxPort = new CashBoxFakeDataAdapter();

    handler = new DeletePaidBillingPaymentFromStoreByStoreChainAdminUseCaseHandler(billingPaymentPort, storeCollectionPort,cashBoxPort);
  }


  @Test
  public void shouldBeDeletedRecordTypeBillingPaymentExistWhenBillingPaymentDelete(){

    //Given
    BillingPaymentDelete billingPaymentDelete = BillingPaymentDelete.builder()
        .id(3L)
        .payingAccount("camlikChainAdmin")
        .payloadType(PayloadType.NETTING)
        .build();

    //When
    BillingPayment responseBilling = handler.handle(billingPaymentDelete);

    //Then
    assertEquals(billingPaymentDelete.getId(), responseBilling.getId());
    assertEquals(true, responseBilling.getIsDeleted());
  }

  @Test
  public void shouldBeRollbackBillingPaymentExistWhenBillingPaymentDeleteByPayloadTypeCollection(){

    //Given
    BillingPaymentDelete billingPaymentDelete = BillingPaymentDelete.builder()
        .id(3L)
        .payingAccount("atalarChainAdmin")
        .payloadType(PayloadType.COLLECTION)
        .build();

    //When
    BillingPayment responseBilling = handler.handle(billingPaymentDelete);


    //Then
    assertEquals(Boolean.TRUE,responseBilling.getIsDeleted());
    assertEquals(billingPaymentDelete.getId(), responseBilling.getId());

    assertEquals(BigDecimal.valueOf(65), storeCollectionPort.storeCollectionList.get(0).getCash());

    assertEquals(BigDecimal.valueOf(260), cashBoxPort.boxes.get(0).getCash());

  }

}
