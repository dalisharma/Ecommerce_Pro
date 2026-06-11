package com.ducat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ducat.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
