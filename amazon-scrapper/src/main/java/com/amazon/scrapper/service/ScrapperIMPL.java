package com.amazon.scrapper.service;

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

import com.amazon.scrapper.bean.Product;
import com.amazon.scrapper.repository.ProductRepository;

@Service
public class ScrapperIMPL implements Scrapper {

	private Product product;
	private List<Product> products;
	private String BASE_URL = "https://www.amazon.in";
	private String SEARCH_KEYWORD = "/s?k=";
	private int isIndia;
	public static final String indianTag = "?tag=adroitlab0f-21";
	public static final String usTag = "?tag=ksharma-20";

	@Autowired
	private ProductRepository productRepository;

	public Product getData(String url, String affTag, String userAgent) throws IOException {
		Document document = Jsoup.connect(url).userAgent(userAgent).timeout(5000).get();
		product = new Product();
		Pattern p = Pattern.compile("(?:[/dp/]|$)([A-Z0-9]{10})");
		Matcher m = p.matcher(url);
		while (m.find()) {
			product.setPid(m.group().substring(1));
		}
		url = url + "?";
		if (affTag.equals("")) {
			if (url.contains("www.amazon.in")) {
				product.setAffUrl(url.substring(0, url.indexOf("?")) + indianTag);
				isIndia = 1;
			} else {
				product.setAffUrl(url.substring(0, url.indexOf("?")) + usTag);
				isIndia = 0;
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

		product.setMRP(document.select("span.a-text-strike").text());
		product.setPrice(document.select("span#priceblock_ourprice").text());

		product.setRating(document.select("span#acrPopover").attr("title").trim().split(" ")[0]);
		product.setImageUrl(document.select("div.imgTagWrapper img").attr("src").trim());
		System.out.println(document.select("div#imgTagWrapper img").attr("src"));
		product.setIsIndia(isIndia);
		return product;
	}

	@Override
	public List<Product> getProductList(String keyWord, String p, String userAgent, String tag) throws IOException {
		products = new ArrayList<Product>();
		String fullUrl;
		if (tag.equals(indianTag))
			BASE_URL = "https://www.amazon.in";
		else
			BASE_URL = "https://www.amazon.com";

		fullUrl = BASE_URL + SEARCH_KEYWORD + keyWord + "&page=" + p;

		Document document = Jsoup.connect(fullUrl).userAgent(userAgent).timeout(5000).get();

		for (Element element : document.select("div[data-asin]")) {
			product = new Product();
			product.setName(element.select("span.a-size-medium.a-color-base.a-text-normal").text());
			String productUrl = element.select("span a.a-link-normal").attr("href") + "?";
			if (productUrl == "" || product.getName().length() < 5 || productUrl.length() < 10)
				continue;
			product.setAffUrl(BASE_URL + productUrl.substring(0, productUrl.indexOf("?")) + "?tag=adroitlab0f-21");
			product.setCustomerCount(element.select("a span.a-size-base").text());
			product.setImageUrl(element.select("a div.a-section.aok-relative.s-image-fixed-height img").attr("src"));
			String prices[] = element.select("a.a-size-base.a-link-normal.s-no-hover.a-text-normal span.a-offscreen")
					.text().trim().split(" ");
			if (prices.length > 0)
				product.setPrice(prices[0]);
			if (prices.length > 1)
				product.setMRP(prices[1]);

			product.setIsIndia(tag.equals(indianTag) ? 1 : 0);
			product.setPid(element.attr("data-asin"));
			product.setRating(element.select("span.a-icon-alt").text().trim().split(" ")[0]);
			products.add(product);
		}

		return products;
	}

	@Override
	public String save(Product product, Product editedProduct) {

		product.setName(editedProduct.getName());
		product.setMRP(editedProduct.getMRP());
		product.setPrice(editedProduct.getPrice());
		product.setCustomerCount(editedProduct.getCustomerCount());
		product.setRating(editedProduct.getRating());
		product.setDescription(editedProduct.getDescription());
		product.setCat(getCategory(editedProduct.getCat()));

		Product p = productRepository.save(product);
		if (p != null)
			return "Saved Successfully";
		else
			return null;
	}

	private String getCategory(String cat) {
		String category = "";
		switch (cat) {
		case "New Arrival":
			category = "0";
			break;
		case "Popular":
			category = "1";
			break;
		case "Gift For Him":
			category = "2";
			break;
		case "Gift For Her":
			category = "3";
			break;
		case "Best Deal":
			category = "4";
			break;
		default:
			break;
		}
		return category;
	}

	@Override
	public List<Product> findByProductName(String country, String keyword, String start) {
		List<Product> products = productRepository.findByProductName(country, keyword, start);
		return products;
	}

	@Override
	public List<Product> findByProductCat(String country, String cat, String start) {
		List<Product> products = productRepository.findByProductCat(country, getCategory(cat), start);
		return products;
	}

}
