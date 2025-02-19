package com.systemCatalogo.systemCatalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemCatalogo.systemCatalogo.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
