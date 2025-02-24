package com.example.rabbitmq.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

	
	@Value("${rabbitmq.queue.stock.name}")
	private  String stockQueueName;
	
	@Value("${rabbitmq.queue.email.name}")
	private  String emailQueueName;
	
	@Value("${rabbitmq.exchange.name}")
	private  String exchangeName;
	
	@Value("${rabbitmq.binding.stock.routing.key}")
	private  String stockRoutingKey;
	
	@Value("${rabbitmq.binding.email.routing.key}")
	private  String emailRoutingKey;
	
	
	// Spring bean for rabbitmq queue - to store JSON messages
	@Bean
	public Queue stockQueue() {
		return new Queue(stockQueueName);
	}
	
	// Spring bean for rabbitmq queue - to store JSON messages
	@Bean
	public Queue emailQueue() {
		return new Queue(emailQueueName);
	}
	
	// Spring bean for rabbitmq exchange
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(exchangeName);
	}
	
	//	binding between JSON queue and exchange using JOSN routing key
	@Bean
	public Binding stockBinding() {
		return BindingBuilder
				.bind(stockQueue())
				.to(exchange())
				.with(stockRoutingKey);
	}
	
	//	binding between JSON queue and exchange using JOSN routing key
	@Bean
	public Binding mailBinding() {
		return BindingBuilder
				.bind(emailQueue())
				.to(exchange())
				.with(emailRoutingKey);
	}
	
	@Bean
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());		
		return rabbitTemplate;	
	}
}
