package com.systemCatalogo.systemCatalogo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.systemCatalogo.systemCatalogo.model.Product;
import com.systemCatalogo.systemCatalogo.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<Product> findAll(){
		return productService.findAll();
	}
	
	 @GetMapping("/{id}")
	 public ResponseEntity<Product> buscarPorId(@PathVariable Long id) {
	        Optional<Product> product = productService.findById(id);
	        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	 }
	 
	 @PostMapping
	    public ResponseEntity<Product> salvar(@RequestBody Product product) {
		 Product productSave = productService.save(product);
	        return ResponseEntity.status(HttpStatus.CREATED).body(productSave);
	 }
	 
	 @DeleteMapping("/{id}")
	 public ResponseEntity<Void> delete(@PathVariable Long id){
		 productService.delete(id);
		 return ResponseEntity.noContent().build();
	 }

}
