package com.systemCatalogo.systemCatalogo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemCatalogo.systemCatalogo.model.Product;
import com.systemCatalogo.systemCatalogo.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository produtcRepository;
	
	public List<Product> findAll(){
		return produtcRepository.findAll();
	}
	
	public Optional<Product> findById(Long id){
		return produtcRepository.findById(id);
	}
	
	public Product save(Product product) {
		return produtcRepository.save(product);
	}
	
	public void delete(Long id) {
		produtcRepository.deleteById(id);
	}
}
