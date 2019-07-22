package com.amazon.service;

import java.io.IOException;
import java.util.List;

import com.amazon.bean.Product;

public interface Scrapper {
	Product getData(String url, String affTag) throws IOException;

	List<Product> getProductList(String keyWord,String p) throws IOException;
}
