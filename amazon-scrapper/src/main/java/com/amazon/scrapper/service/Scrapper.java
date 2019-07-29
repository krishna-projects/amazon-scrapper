package com.amazon.scrapper.service;

import java.io.IOException;
import java.util.List;

import com.amazon.scrapper.bean.Product;

public interface Scrapper {
	Product getData(String url, String affTag, String userAgent) throws IOException;

	List<Product> getProductList(String keyWord, String p, String userAgent, String tag) throws IOException;

	String save(Product product, Product editedProduct);

	List<Product> findByProductName(String country, String keyword, String start);

	List<Product> findByProductCat(String country, String cat, String start);
}
