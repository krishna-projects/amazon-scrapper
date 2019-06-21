package com.amazon.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.bean.Product;

@Service
public class ScrapperIMPL implements Scrapper {

	@Autowired
	private Product product;
	private List<Product> products;

	public Product getData(String url, String affTag) throws IOException {
		Document document = Jsoup.connect(url).userAgent("Googlebot/2.1 (+http://www.googlebot.com/bot.html)")
				.timeout(5000).get();
		if (affTag == null) {
			if (url.contains("www.amazon.in"))
				product.setAffUrl(url + "&tag=adroitlab0f-21");
			else
				product.setAffUrl(url + "&tag=ksharma-20");
		} else {
			product.setAffUrl(url + "&tag=" + affTag);
		}
		product.setName(document.select("span#productTitle").text());
		product.setCustomerCount(document.select("span#acrCustomerReviewText").text());
		StringBuffer description = new StringBuffer();
		for (Element element : document.select("ul.a-unordered-list.a-vertical.a-spacing-none li")) {
			description.append(element.select("span.a-list-item").text() + ".");
		}
		product.setDescription(description.toString());
		product.setMRP(document.select("span.a-text-strike").text());
		product.setPrice(document.select("span#priceblock_ourprice").text());
		product.setRating(document.select("span#acrPopover").attr("title"));
		product.setImageUrl(document.select("div#imgTagWrapperId img").attr("src"));

		Pattern p = Pattern.compile("(?:[/dp/]|$)([A-Z0-9]{10})");
		Matcher m = p.matcher(url);
		while (m.find()) {
			product.setPid(m.group().substring(1));
		}

		if (product.getName().length() < 1)
			System.out.println(document);
		return product;
	}

	@Override
	public List<Product> getProductList(String keyWord) {
		products = new ArrayList<Product>();
		return products;
	}
}
