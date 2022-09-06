package com.maxijett.monetary.adapters.orderpayment.kafka;

import com.maxijett.monetary.adapters.orderpayment.kafka.event.OrderPaymentEvent;
import com.maxijett.monetary.common.usecase.UseCaseHandler;
import com.maxijett.monetary.common.usecase.VoidUseCaseHandler;
import com.maxijett.monetary.orderpayment.useCase.OrderPayment;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
public class OrderPaymentEventConsumer {

    private final VoidUseCaseHandler<OrderPayment> addPaymentToDriverAndStoreVoidUseCaseHandler;

    @KafkaListener(topics = "${kafka.topics.orderPayment.name}")
    public void orderPaymentEventListener(OrderPaymentEvent orderPaymentEvent) {
        addPaymentToDriverAndStoreVoidUseCaseHandler.handle(orderPaymentEvent.toUseCase());
    }

}
