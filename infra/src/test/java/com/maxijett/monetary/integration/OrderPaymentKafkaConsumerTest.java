package com.maxijett.monetary.integration;

import com.maxijett.monetary.AbstractIT;
import com.maxijett.monetary.IT;
import com.maxijett.monetary.adapters.orderpayment.kafka.OrderPaymentEventConsumer;
import com.maxijett.monetary.adapters.orderpayment.kafka.event.OrderPaymentEvent;
import com.maxijett.monetary.common.usecase.FakeAddPaymentToDriverAndStoreUseCaseHandler;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@IT
public class OrderPaymentKafkaConsumerTest extends AbstractIT {

    FakeAddPaymentToDriverAndStoreUseCaseHandler handler = new FakeAddPaymentToDriverAndStoreUseCaseHandler();
    OrderPaymentEventConsumer orderPaymentEventConsumer = new OrderPaymentEventConsumer(handler);


    @Test
    public void receivePaymentKafkaEventTest() {
        //Given
        OrderPaymentEvent orderPaymentEvent = OrderPaymentEvent.builder()
                .orderNumber("123456789")
                .pos(BigDecimal.valueOf(20))
                .cash(BigDecimal.valueOf(15))
                .groupId(20L)
                .storeId(200L)
                .clientId(20000L)
                .driverId(1L)
                .build();


        //When

        orderPaymentEventConsumer.orderPaymentEventListener(orderPaymentEvent);

        //Then TODO

    }

}
