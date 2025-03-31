package com.systemCatalogo.systemCatalogo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.systemCatalogo.systemCatalogo.model.Product;
import com.systemCatalogo.systemCatalogo.services.ProductService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @GetMapping("/imgs/{filename}")
    public ResponseEntity<Resource> exibirImagem(@PathVariable String filename) {
        Path path = Paths.get("uploads").resolve(filename);
        FileSystemResource resource = new FileSystemResource(path);

        if (resource.exists()) {
            String mimeType = null;
            try {
                mimeType = Files.probeContentType(path);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.parseMediaType(mimeType))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> buscarPorId(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("img") MultipartFile img,
                                        @RequestParam("productStock") Integer productStock) {
        try {
            String imgUrl = saveImg(img);

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setImgUrl(imgUrl);
            product.setProductStock(productStock);

            Product savedProduct = productService.save(product);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id,
                                          @RequestParam("name") String name,
                                          @RequestParam("description") String description,
                                          @RequestParam(value = "img", required = false) MultipartFile img,
                                          @RequestParam("productStock") Integer productStock) {
        try {
            Product updatedProduct = productService.update(id, name, description, img, productStock);
            
            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private String saveImg(MultipartFile img) throws IOException {
    	
        Path path = Paths.get("uploads", img.getOriginalFilename());

        if (!Files.exists(Paths.get("uploads"))) {
            Files.createDirectories(Paths.get("uploads"));
        }

        Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return path.toString().replace("\\", "/");
    }
}
