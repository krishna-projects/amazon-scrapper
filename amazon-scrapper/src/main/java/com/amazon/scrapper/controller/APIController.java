package com.amazon.scrapper.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.scrapper.bean.Product;
import com.amazon.scrapper.bean.RequestBodyModel;
import com.amazon.scrapper.service.ScrapperIMPL;

@RequestMapping("/api/best-picked/")
@RestController
public class APIController {
	@Autowired
	private ScrapperIMPL scrapper;

	private List<Product> products;
	private final String userAgent1 = "Googlebot/2.1 (+http://www.googlebot.com/bot.html)";
	private final String userAgent2 = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36";

	@PostMapping("/search-amazon")
	public List<Product> getProductList(@RequestBody RequestBodyModel requestBody) {
		String tag = Integer.parseInt(requestBody.getIsIndia()) == 1 ? ScrapperIMPL.indianTag : ScrapperIMPL.usTag;
		try {
			products = scrapper.getProductList(requestBody.getKeyWord(), requestBody.getPage(), userAgent1, tag);
		} catch (IOException e) {
			try {
				products = scrapper.getProductList(requestBody.getKeyWord(), requestBody.getPage(), userAgent2, tag);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return products;
	}

	@PostMapping("/search-name")
	public List<Product> getProductbyName(@RequestBody RequestBodyModel requestBody) {
		return scrapper.findByProductName(requestBody.getIsIndia(), requestBody.getKeyWord(), requestBody.getStart());
	}

	@PostMapping("/search-cat")
	public List<Product> getProductCategory(@RequestBody RequestBodyModel requestBody) {
		return scrapper.findByProductCat(requestBody.getIsIndia(), requestBody.getKeyWord(), requestBody.getStart());
	}
}
