package com.joel.catalog.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.joel.catalog.repositories.ProductRepository;
import com.joel.catalog.services.ProductService;
import com.joel.catalog.services.exception.DatabaseException;
import com.joel.catalog.services.exception.ResourceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService productService;
	
	@Mock
	private ProductRepository productRepository;
	
	private long existingId;
	private long nonExistsId;
	private long dependetId;
	
	@BeforeEach
	void setUp() {
		existingId = 1L;
		nonExistsId = 100;
		dependetId = 4L;
		
		Mockito.doNothing().when(productRepository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistsId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependetId);
	}
	
	@Test
	public void deleteShouldTDatabaseExceptionWhenDependetId() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			productService.delete(dependetId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependetId);
	}
	
	@Test
	public void deleteShouldNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			productService.delete(existingId);			
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			productService.delete(nonExistsId);
		});
		
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(nonExistsId);
	}

}


















