package com.joel.catalog.tests;

import java.time.Instant;

import com.joel.catalog.dto.ProductDto;
import com.joel.catalog.entities.Category;
import com.joel.catalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		
		var product = new Product(1L , "Phone","Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDto createProductDto() {
		var product = createProduct();
		return new ProductDto( product, product.getCategories());
		
	}
	
	public static Category createCategory() {
		return new Category(1L, "Electronics");
	}

}






