package com.m2m.api;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.m2m.entity.Product;
import com.m2m.service.ProductService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/products")
public class ProductApi {

	@Autowired
	ProductService productService;

	@GetMapping
	@RolesAllowed({"user", "admin"})
	public List<Product> doGetAll() {
		return productService.findAll();
	}

	@GetMapping("/find")
	@RolesAllowed({"user", "admin"})
	public List<Product> doGetByQuantity(@RequestParam("min-quantity") int minQuantity) {
		return productService.findByQuantityGreaterThan(minQuantity);
	}
	
	@PostMapping
	@RolesAllowed("admin")
	public ResponseEntity<Product> doPost(@RequestBody Product product) {
		Product savedProduct = productService.save(product);
		URI productURI = URI.create("/products/" + savedProduct.getId());
		return ResponseEntity.created(productURI).body(savedProduct);
	}
}
