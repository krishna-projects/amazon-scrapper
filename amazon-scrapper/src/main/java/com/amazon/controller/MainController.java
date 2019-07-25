package com.amazon.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazon.bean.Product;
import com.amazon.service.ScrapperIMPL;

@Controller
public class MainController {

	@Autowired
	private ScrapperIMPL scrapper;
	private Product product;
	private List<Product> products;

	@ResponseBody
	@PostMapping("/product")
	public Product getProductInfo(@RequestParam String url, @RequestParam(required = false) String tag) {

		try {
			product = scrapper.getData(url, tag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return product;
	}

	@ResponseBody
	@GetMapping("/search")
	public List<Product> getProductList(@RequestParam String keyWord,
			@RequestParam(required = false, defaultValue = "1") String p) {
		try {
			products = scrapper.getProductList(keyWord, p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return products;
	}

	@RequestMapping("")
	public String home(@RequestParam(required = false) String url, @RequestParam(required = false) String tag,
			ModelMap modelMap) {
		if (url != null) {
			try {
				product = scrapper.getData(url, tag);
				modelMap.addAttribute("product", product);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "index";
	}
}
