package com.joel.catalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.joel.catalog.entities.Product;
import com.joel.catalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository productRepository;

	private long exitingId;
	private long nonExistingId;
	private long countTotalProducts;

	@BeforeEach
	void setUp() throws Exception {

		exitingId = 1l;
		nonExistingId = 100l;
		countTotalProducts = 25L;
	}

	@Test
	public void deleteShouldDeleteObjectWhenExists() {

		productRepository.deleteById(exitingId);

		Optional<Product> producOptional = productRepository.findById(exitingId);

		Assertions.assertFalse(producOptional.isPresent());
	}

	@Test
	public void deleteShouldEmptyResultDataAccessExceptionWhenIdNotExists() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			productRepository.deleteById(nonExistingId);
		});
	}
	
	@Test
	public void saveShouldPersistentWithAutoincrementWhendIdIsNull() {
		var product = Factory.createProduct();
		product.setId(null);
		
		product = productRepository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}
}








