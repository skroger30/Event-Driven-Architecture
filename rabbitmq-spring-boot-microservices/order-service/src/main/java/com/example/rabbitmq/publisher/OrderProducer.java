package com.example.rabbitmq.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.rabbitmq.dto.OrderEvent;

@Service
public class OrderProducer {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);

	@Value("${rabbitmq.exchange.name}")
	private  String exchangeName;
	
	@Value("${rabbitmq.binding.stock.routing.key}")
	private  String stockRoutingKey;
	
	@Value("${rabbitmq.binding.email.routing.key}")
	private  String emailRoutingKey;
	
	/* RabbitTemplate is used to send the messages.
	Springboot auto-configures the bean. We just need to inject and use it.
 	No need to create the bean explicitly. */
	 
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendJsonMessage(OrderEvent event) {
		LOG.info(String.format("Json Message sent %s", event.toString()));
		
		// Send to stock queue.
		rabbitTemplate.convertAndSend(exchangeName, stockRoutingKey, event);
		
		// Send to email queue.
		rabbitTemplate.convertAndSend(exchangeName, emailRoutingKey, event);
	}
	
}