package com.m2m.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m2m.entity.Product;
import com.m2m.repository.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepo productRepo;

	@Override
	public List<Product> findAll() {
		return productRepo.findAll();
	}

	@Override
	public Product save(Product product) {
		return productRepo.save(product);
	}
}
