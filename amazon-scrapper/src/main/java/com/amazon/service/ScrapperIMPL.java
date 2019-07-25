package com.amazon.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.amazon.bean.Product;

@Service
public class ScrapperIMPL implements Scrapper {

	private Product product;
	private List<Product> products;
	private String BASE_URL = "https://www.amazon.in";
	private String SEARCH_KEYWORD = "/s?k=";
	private int isIndia;
	private String currencySymbol;

	public Product getData(String url, String affTag) throws IOException {
		Document document = Jsoup.connect(url).userAgent("Googlebot/2.1 (+http://www.googlebot.com/bot.html)")
				.timeout(5000).get();
		product = new Product();
		Pattern p = Pattern.compile("(?:[/dp/]|$)([A-Z0-9]{10})");
		Matcher m = p.matcher(url);
		while (m.find()) {
			product.setPid(m.group().substring(1));
		}
		if (affTag.equals("")) {
			if (url.contains("www.amazon.in")) {
				product.setAffUrl(url.substring(0, url.indexOf("?")) + "?tag=adroitlab0f-21");
				isIndia = 1;
				currencySymbol = "&#8377; ";
			} else {
				product.setAffUrl(url.substring(0, url.indexOf("?")) + "?tag=ksharma-20");
				isIndia = 0;
				currencySymbol = "&#36; ";
			}
		} else {
			product.setAffUrl(url.substring(0, url.indexOf("?")) + "&tag=" + affTag);
		}
		product.setName(document.select("span#productTitle").text());
		product.setCustomerCount(document.select("span#acrCustomerReviewText").text().trim().split(" ")[0]);
		StringBuffer description = new StringBuffer();
		for (Element element : document.select("ul.a-unordered-list.a-vertical.a-spacing-none li")) {
			if (description.length() > 200)
				break;
			else
				description.append(element.select("span.a-list-item").text() + ",");
		}
		product.setDescription(description.toString());
		product.setMRP(currencySymbol + document.select("span.a-text-strike").text().trim().split(" ")[1]);
		product.setPrice(currencySymbol + document.select("span#priceblock_ourprice").text().trim().split(" ")[1]);
		product.setRating(document.select("span#acrPopover").attr("title").trim().split(" ")[0]);
		product.setImageUrl(document.select("div#imgTagWrapperId img").attr("src"));
		product.setIsIndia(isIndia);
		return product;
	}

	@Override
	public List<Product> getProductList(String keyWord, String p) throws IOException {
		products = new ArrayList<Product>();
		String fullUrl;
		if (Integer.parseInt(p) < 2)
			fullUrl = BASE_URL + SEARCH_KEYWORD + keyWord;
		else
			fullUrl = BASE_URL + SEARCH_KEYWORD + keyWord + "&page=" + p;

		Document document = Jsoup.connect(fullUrl).userAgent("Googlebot/2.1 (+http://www.googlebot.com/bot.html)")
				.timeout(5000).get();

		for (Element element : document.select("div[data-asin]")) {
			product = new Product();
			product.setName(element.select("span.a-size-medium.a-color-base.a-text-normal").text());
			product.setAffUrl(BASE_URL + element.select("span a.a-link-normal").attr("href") + "?tag=adroitlab0f-21");
			product.setCustomerCount(element.select("a span.a-size-base").text());
			product.setImageUrl(element.select("a div.a-section.aok-relative.s-image-fixed-height img").attr("src"));
			String prices[] = element.select("a.a-size-base.a-link-normal.s-no-hover.a-text-normal span.a-offscreen")
					.text().trim().split(" ");
			if (prices.length > 0)
				product.setPrice(prices[0]);
			if (prices.length > 1)
				product.setMRP(prices[1]);

			product.setPid(element.attr("data-asin"));
			product.setRating(element.select("span.a-icon-alt").text().trim().split(" ")[0]);
			products.add(product);
		}

		return products;
	}
}
