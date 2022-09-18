package com.joel.catalog.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import com.joel.catalog.dto.ProductDto;
import com.joel.catalog.services.ProductService;
import com.joel.catalog.tests.Factory;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	private ProductDto productDto;
	private PageImpl<ProductDto> page;
	
	@BeforeEach
	void setUp() throws Exception {
		productDto = Factory.createProductDto();
		page = new PageImpl<>(List.of(productDto));
		
		when(productService.findAllPaged(any())).thenReturn(page);
		
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		mockMvc.perform(get("/products")).andExpectAll(status().isOk());
	}
}













