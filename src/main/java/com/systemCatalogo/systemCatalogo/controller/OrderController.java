package com.systemCatalogo.systemCatalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systemCatalogo.systemCatalogo.model.Order;
import com.systemCatalogo.systemCatalogo.services.OrderService;
import com.systemCatalogo.systemCatalogo.services.ProductService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order order){
		Order savedOrder = orderService.save(order);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
	}
	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
	        List<Order> orders = orderService.findAll();
	        return ResponseEntity.ok(orders);
	}
	@PostMapping("/approve/{id}") 
	public ResponseEntity<Order> approveOrder(@PathVariable Long id) {
		try {
			Order approvedOrder = orderService.approveOrder(id);
			return ResponseEntity.ok(approvedOrder);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}