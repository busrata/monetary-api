package com.maxijett.monetary.collectionpayment;

import com.maxijett.monetary.collectionpayment.adapters.CollectionPaymentFakeDataAdapter;
import com.maxijett.monetary.collectionpayment.model.CollectionPayment;
import com.maxijett.monetary.collectionpayment.port.CollectionPaymentPort;
import com.maxijett.monetary.collectionpayment.useCase.CollectionPaymentListGet;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAllCollectionPaymentsByGroupIdAndDateUseCaseHandlerTest {

    CollectionPaymentPort collectionPaymentPort = new CollectionPaymentFakeDataAdapter();
    GetAllCollectionPaymentsByGroupIdAndDateUseCaseHandler useCaseHandler = new GetAllCollectionPaymentsByGroupIdAndDateUseCaseHandler(collectionPaymentPort);

    @Test
    public void shouldReturnCollectionPaymentListByGroupIdAndDate() {
        //Given
        CollectionPaymentListGet useCase = CollectionPaymentListGet.builder()
                .groupId(20L)
                .createOn(ZonedDateTime.parse("2022-09-02T00:00:00.000Z")).build();

        //When
        List<CollectionPayment> collectionPaymentList = useCaseHandler.handle(useCase);

        //Then
        assertEquals(useCase.getGroupId(), collectionPaymentList.get(0).getGroupId());
        assertThat(collectionPaymentList.get(0).getCreateOn()).isBetween(useCase.getCreateOn(), useCase.getCreateOn().plusDays(1L));

    }
}
