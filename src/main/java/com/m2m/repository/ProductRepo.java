package com.m2m.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.m2m.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
