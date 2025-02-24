package com.example.rabbitmq.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.rabbitmq.dto.OrderEvent;


@Service
public class OrderConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(OrderConsumer.class);
		
	@RabbitListener(queues = {"${rabbitmq.queue.stock.name}"})
	public void consume(OrderEvent event) {
		LOG.info(String.format("Json Message consumed : %s", event.toString()));
		
		
		// DB base operations logic.
	}
}
