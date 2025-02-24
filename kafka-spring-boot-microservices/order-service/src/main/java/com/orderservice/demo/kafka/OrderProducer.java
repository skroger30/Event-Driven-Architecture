package com.orderservice.demo.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.basedomain.demo.dto.OrderEvent;

@Service
public class OrderProducer {

	private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);
	
	@Autowired
	private NewTopic newTopic;
	
	@Autowired
	private KafkaTemplate<String, OrderEvent> kafkaTemplate;
	
	public void sendMessage(OrderEvent ordervent) {
		
		LOG.info(String.format("Order Event -  %s", ordervent.toString()));
		
		Message<OrderEvent> message = MessageBuilder
				.withPayload(ordervent)
				.setHeader(KafkaHeaders.TOPIC, newTopic.name())
				.build();		
		
		kafkaTemplate.send(message);
	}
}
