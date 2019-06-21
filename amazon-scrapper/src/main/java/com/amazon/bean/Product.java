package com.amazon.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Product {
	private String name;
	private String MRP;
	private String rating;
	private String customerCount;
	private String price;
	private String description;
	private String imageUrl;
	private String affUrl;
	private String cat;
	private String pid;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getAffUrl() {
		return affUrl;
	}

	public void setAffUrl(String affUrl) {
		this.affUrl = affUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMRP() {
		return MRP;
	}

	public void setMRP(String mRP) {
		MRP = mRP;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(String customerCount) {
		this.customerCount = customerCount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", MRP=" + MRP + ", rating=" + rating + ", customerCount=" + customerCount
				+ ", price=" + price + ", description=" + description + ", imageUrl=" + imageUrl + ", affUrl=" + affUrl
				+ ", cat=" + cat + ", pid=" + pid + "]";
	}
}
