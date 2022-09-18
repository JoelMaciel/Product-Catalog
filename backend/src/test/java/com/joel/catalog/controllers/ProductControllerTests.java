package com.joel.catalog.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.joel.catalog.dto.ProductDto;
import com.joel.catalog.services.ProductService;
import com.joel.catalog.services.exception.ResourceNotFoundException;
import com.joel.catalog.tests.Factory;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	private long existingId;
	private long nonExistingId;
	private ProductDto productDto;
	private PageImpl<ProductDto> page;
	
	@BeforeEach
	void setUp() throws Exception {
		
		existingId = 1L;
		nonExistingId = 2L;
		
		productDto = Factory.createProductDto();
		page = new PageImpl<>(List.of(productDto));
		
		when(productService.findAllPaged(any())).thenReturn(page);
		when(productService.findById(existingId)).thenReturn(productDto);
		when(productService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		ResultActions result = mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));
		
		result.andExpectAll(status().isOk());
	}
	@Test
	public void findByIdSouldReturnProductWhenIdExist() throws Exception {
		ResultActions result = mockMvc.perform(get("/products/{id}", existingId)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.description").exists());
				
		
	}
	
	@Test
	public void findBYIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		ResultActions result = mockMvc.perform(get("/products/{id}", nonExistingId)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
		
	}
}













