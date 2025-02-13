package com.systemCatalogo.systemCatalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systemCatalogo.systemCatalogo.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
