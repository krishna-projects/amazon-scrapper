package com.amazon.scrapper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.amazon.scrapper.bean.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	@Query(value = "SELECT * FROM product WHERE is_india = :country ORDER BY name LIKE '%:keyword%' DESC LIMIT :start , 5", nativeQuery = true)
	List<Product> findByProductName(@Param(value = "country") String country, @Param(value = "keyword") String keyword,
			@Param(value = "start") String start);

	@Query(value = "SELECT * FROM product WHERE is_india = :country ORDER BY cat = ':cat' DESC LIMIT :start , 5", nativeQuery = true)
	List<Product> findByProductCat(@Param(value = "country") String country, @Param(value = "cat") String cat,
			@Param(value = "start") String start);

}
