package com.amazon.scrapper.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.amazon.scrapper.bean.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	@Query(value = "SELECT * FROM product p WHERE p.is_india = ?1 ORDER BY p.name LIKE %?2% DESC LIMIT ?3,5", nativeQuery = true)
	List<Product> findByProductName(String country, String keyword, int start);

	@Query(value = "SELECT * FROM product p WHERE p.is_india = ?1 ORDER BY cat = ?2 DESC LIMIT ?3 , 5", nativeQuery = true)
	List<Product> findByProductCat(@Param(value = "country") String country, @Param(value = "cat") String cat,
			@Param(value = "start") int start);

}
