package com.amazon.scrapper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amazon.scrapper.bean.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	@Query(value = "SELECT * FROM product p WHERE p.is_india = ?1 ORDER BY lower(p.name) LIKE %?2% DESC , p.added_on DESC LIMIT ?3,5", nativeQuery = true)
	List<Product> findByProductName(String country, String keyword, int start);

	@Query(value = "SELECT * FROM product p WHERE p.is_india = ?1 ORDER BY p.cat = ?2 DESC , p.added_on DESC LIMIT ?3 , 5", nativeQuery = true)
	List<Product> findByProductCat(String country, String cat, int start);

	@Query(value = "SELECT * FROM product p WHERE p.is_india = ?1 ORDER BY p.added_on DESC LIMIT ?2 , 5", nativeQuery = true)
	List<Product> findNewArrival(String country, int start);

	@Query(value = "SELECT * FROM product p WHERE p.is_india = ?1 ORDER BY p.cat = 2 or p.cat = 3  DESC , p.added_on DESC LIMIT ?2 , 5", nativeQuery = true)
	List<Product> findAllGifts(String country, int start);

}
