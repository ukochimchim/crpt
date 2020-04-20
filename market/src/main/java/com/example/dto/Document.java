package com.example.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Document {

	private Header responseHeader;

	@NotNull
	@Size(min = 9, max = 9)
	private String seller;

	@NotNull
	@Size(min = 9, max = 9)
	private String customer;

	@NotEmpty
	private List<Product> products;

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Header getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(Header responseHeader) {
		this.responseHeader = responseHeader;
	}

}
