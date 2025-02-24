package com.example.rabbitmq.controller;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rabbitmq.dto.Order;
import com.example.rabbitmq.dto.OrderEvent;
import com.example.rabbitmq.publisher.OrderProducer;

@RestController
@RequestMapping("/api/v1")
public class MessageController {
	
	@Autowired
	private OrderProducer orderProducer;
	
	
	@PostMapping("/publish")
	public ResponseEntity<String> placeOrder(@RequestBody Order order){
		
			order.setOrderId(UUID.randomUUID().toString());			
			OrderEvent orderEvent = new OrderEvent();		
			orderEvent.setStatus("PENDING");
			orderEvent.setMessage("Order state is Pending.");
			orderEvent.setOrder(order);			
			orderProducer.sendJsonMessage(orderEvent);
		return ResponseEntity.ok("JSON Message sent to rabbitmq broker.");	}

}
