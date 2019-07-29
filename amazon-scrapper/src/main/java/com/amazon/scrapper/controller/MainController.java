package com.amazon.scrapper.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazon.scrapper.bean.Product;
import com.amazon.scrapper.service.ScrapperIMPL;

@Controller
public class MainController {

	@Autowired
	private ScrapperIMPL scrapper;
	private Product product;
	private final String userAgent1 = "Googlebot/2.1 (+http://www.googlebot.com/bot.html)";
	private final String userAgent2 = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36";
	private String message;

	@ResponseBody
	@PostMapping("/product")
	public Product getProductInfo(@RequestParam String url, @RequestParam(required = false) String tag) {

		try {
			product = scrapper.getData(url, tag, userAgent1);
		} catch (IOException e) {
			try {
				product = scrapper.getData(url, tag, userAgent2);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return product;
	}

	@RequestMapping("")
	public String home(@RequestParam(required = false) String url, @RequestParam(required = false) String tag,
			ModelMap modelMap) {
		if (url != null) {
			try {
				product = scrapper.getData(url, tag, userAgent1);
				modelMap.addAttribute("product", product);
			} catch (IOException e) {
				try {
					product = scrapper.getData(url, tag, userAgent2);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				modelMap.addAttribute("product", product);
			}
		}
		modelMap.addAttribute("message", message);
		message = "";
		return "index";
	}

	@PostMapping(value = "/save-to-database")
	public String saveToDatabase(Product product, ModelMap modelMap) {
		message = scrapper.save(this.product, product);
		return "redirect:/";
	}

}
