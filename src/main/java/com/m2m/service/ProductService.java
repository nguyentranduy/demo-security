package com.m2m.service;

import java.util.List;

import com.m2m.entity.Product;

public interface ProductService {

	List<Product> findAll();
	Product save(Product product);
}
