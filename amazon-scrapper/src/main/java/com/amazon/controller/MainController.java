package com.amazon.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.bean.Product;
import com.amazon.service.Scrapper;

@RestController
public class MainController {

	@Autowired
	private Scrapper scrapper;
	private Product product;

	@GetMapping("/get-info")
	public Product getProductInfo(@RequestParam String url, @RequestParam(required = false) String tag) {

		try {
			product = scrapper.getData(url, tag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return product;
	}
}
