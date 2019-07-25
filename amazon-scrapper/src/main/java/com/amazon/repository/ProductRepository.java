package com.amazon.repository;

import org.springframework.data.repository.CrudRepository;

import com.amazon.bean.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
