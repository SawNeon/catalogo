package com.systemCatalogo.systemCatalogo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemCatalogo.systemCatalogo.model.Order;
import com.systemCatalogo.systemCatalogo.model.OrderItem;
import com.systemCatalogo.systemCatalogo.model.OrderStatus;
import com.systemCatalogo.systemCatalogo.model.Product;
import com.systemCatalogo.systemCatalogo.repository.OrderItemRepository;
import com.systemCatalogo.systemCatalogo.repository.OrderRepository;
import com.systemCatalogo.systemCatalogo.repository.ProductRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
	private ProductRepository productRepository;
    
    public Order save(Order order) {
        Order savedOrder = orderRepository.save(order);
        
        savedOrder.setStatus(OrderStatus.PENDENTE);

        for (OrderItem item : order.getOrderItems()) {
        	
        	Product product = productRepository.findById(item.getProduct().getId())
        			.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            item.setProduct(product);
            item.setQuantity(item.getQuantity());
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
     }

        return orderRepository.save(order);
    }
    
 
    public Order approveOrder(Long orderId) {
    	
    	Optional<Order> orderOpt = orderRepository.findById(orderId);
    	
    	if(!orderOpt.isPresent()) {
    		throw new RuntimeException("Pedido não encontrado");
    	}
    	
    	Order order = orderOpt.get();
    	
    	for (OrderItem item : order.getOrderItems()) {
    		Product product = item.getProduct();
    		if (product.getProductStock() < item.getQuantity()) {
    			throw new RuntimeException("Estoque insulfiente para o produto " + product.getName());
    		}
    	}
    	
    	for (OrderItem item : order.getOrderItems()) {
    		Product product = item.getProduct();
    		product.setProductStock(product.getProductStock() - item.getQuantity());
    		productRepository.save(product);
    	}
    	order.setStatus(OrderStatus.APROVADO);
    	return orderRepository.save(order);
    }
    
    
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
    
    public void delete(long id) {
        orderRepository.deleteById(id);
    }
}
