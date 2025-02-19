package com.systemCatalogo.systemCatalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemCatalogo.systemCatalogo.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
