package com.amazon.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.bean.Product;
import com.amazon.service.ScrapperIMPL;

@RestController
public class MainController {

	@Autowired
	private ScrapperIMPL scrapper;
	private Product product;

	@GetMapping("/product")
	public Product getProductInfo(@RequestParam String url, @RequestParam(required = false) String tag) {

		try {
			product = scrapper.getData(url, tag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return product;
	}

	@GetMapping("/search")
	public List<Product> getProductList(@RequestParam String keyWord) {
		return scrapper.getProductList(keyWord);
	}
}
