package com.systemCatalogo.systemCatalogo.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	public Product update(Long id, String name, String description, MultipartFile img, Integer productStock) throws IOException{
		
		Optional<Product> optionalProduct = produtcRepository.findById(id);
		
		if (optionalProduct.isPresent()) {
			
			Product product = optionalProduct.get();
			product.setName(name);
			product.setDescription(description);
			product.setProductStock(productStock);
			
			 if (img != null && !img.isEmpty()) {
		            String imgUrl = saveImg(img);
		            product.setImgUrl(imgUrl);
		        }
			 
			 return produtcRepository.save(product);
			
		}
		return null;
	}
	
	public String saveImg(MultipartFile img) throws IOException {
        Path path = Paths.get("uploads", img.getOriginalFilename());

        if (!Files.exists(Paths.get("uploads"))) {
            Files.createDirectories(Paths.get("uploads"));
        }

        Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return path.toString().replace("\\", "/");
    }
}
