package com.orderservice.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basedomain.demo.dto.Order;
import com.basedomain.demo.dto.OrderEvent;
import com.orderservice.demo.kafka.OrderProducer;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

	@Autowired
	private OrderProducer orderProducer;
	
	@PostMapping("/orders")
	public String placeOrder(@RequestBody Order order) {
		order.setOrderId(UUID.randomUUID().toString());
		
		OrderEvent orderEvent = new OrderEvent();		
		orderEvent.setStatus("PENDING");
		orderEvent.setMessage("Order state is Pending.");
		orderEvent.setOrder(order);
		
		orderProducer.sendMessage(orderEvent);
		return "Order placed successfully.";
	}
}
