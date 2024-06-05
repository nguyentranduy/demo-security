package com.m2m.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.m2m.entity.Product;
import com.m2m.repository.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepo productRepo;

	@Override
	@Cacheable(value = "findAllProducts")
	public List<Product> findAll() {
		return productRepo.findAll();
	}

	@Override
	@Cacheable(value = "findByQuantity")
	public List<Product> findByQuantityGreaterThan(int quantity) {
		return productRepo.findByQuantityGreaterThan(quantity);
	}

	@Override
	@CacheEvict(value = "findAllProducts", allEntries = true)
	public Product save(Product product) {
		return productRepo.save(product);
	}

	@Scheduled(fixedRate = 10*1000)
	@CacheEvict(value = "findAllProducts", allEntries = true)
	public void clearCacheFindAll() {
		System.out.println("Clear cache");
	}
}
