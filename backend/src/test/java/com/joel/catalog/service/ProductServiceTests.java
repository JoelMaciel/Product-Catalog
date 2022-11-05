package com.joel.catalog.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.joel.catalog.dto.ProductDto;
import com.joel.catalog.entities.Category;
import com.joel.catalog.entities.Product;
import com.joel.catalog.repositories.CategoryRepository;
import com.joel.catalog.repositories.ProductRepository;
import com.joel.catalog.services.ProductService;
import com.joel.catalog.services.exception.DatabaseException;
import com.joel.catalog.services.exception.ResourceNotFoundException;
import com.joel.catalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	private long existingId;
	private long nonExistsId;
	private long dependetId;
	private PageImpl<Product> page;
	private Product product;
	private Category category;
	private ProductDto productDto;
	
	@BeforeEach
	void setUp() {
		existingId = 1L;
		nonExistsId = 2L;
		dependetId = 3L;
		product = Factory.createProduct();
		category = Factory.createCategory();
		page = new PageImpl<>(List.of(product));
		productDto = Factory.createProductDto();
		
		Mockito.when(productRepository.findAll((Pageable)any())).thenReturn(page);
		
		Mockito.when(productRepository.save(any())).thenReturn(product);
		
		Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(productRepository.findById(nonExistsId)).thenReturn(Optional.empty());
		
		Mockito.when(productRepository.find(any(), any(), any())).thenReturn(page);
		
		Mockito.when(productRepository.getOne(existingId)).thenReturn(product);
		Mockito.when(productRepository.getOne(nonExistsId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(categoryRepository.getOne(existingId)).thenReturn(category);
		Mockito.when(categoryRepository.getOne(nonExistsId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.doNothing().when(productRepository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistsId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependetId);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.update(nonExistsId, productDto);
		});
	}
	
	
	
	@Test
	public void updateShouldReturnProductDtoWhenIdExists() {
		
		var result = productService.update(existingId, productDto);
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void findByIdShoulThrowResourceNotFoundExceptionWhenIdDoesNotExistes() {
		
		Assertions.assertThrows(ResourceNotFoundException.class,() -> {
			productService.findById(nonExistsId);
		});
	}
	
	
	@Test
	public void findByIdShouldReturnProductDtoWhenIdExists() {
		
		ProductDto productDto = productService.findById(existingId);
		
		Assertions.assertNotNull(productDto);
	}
	
	@Test
	public void findAllPageShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<ProductDto> result = productService.findAllPaged(0L, "", pageable);
		
		Assertions.assertNotNull(result);
		
	}
	
	@Test
	public void deleteShouldTDatabaseExceptionWhenDependetId() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			productService.delete(dependetId);
		});
		
		Mockito.verify(productRepository, times(1)).deleteById(dependetId);
	}
	
	@Test
	public void deleteShouldNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			productService.delete(existingId);			
		});
		
		Mockito.verify(productRepository, times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.delete(nonExistsId);
		});
		
		Mockito.verify(productRepository, times(1)).deleteById(nonExistsId);
	}

}


















