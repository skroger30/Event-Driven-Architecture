package com.stockservice.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.basedomain.demo.dto.OrderEvent;

@Service
public class OrderConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(OrderConsumer.class);
	
//	The OrderConsumer has subscribed to mentioned topics and belongs to the mentioned groupId.
	@KafkaListener(topics = "${spring.kafka.topic.name}" , groupId = "${spring.kafka.consumer.group-id}")
	public void consumeMessage(OrderEvent ordervent) {
		
		LOG.info(String.format("Order Event recieved in Stock service -  %s", ordervent.toString()));
		
//		Save the order event into DB.
	}
}
