package com.amazon.scrapper.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amazon.scrapper.bean.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
