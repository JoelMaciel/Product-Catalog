package com.joel.catalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joel.catalog.dto.CategoryDto;
import com.joel.catalog.dto.ProductDto;
import com.joel.catalog.entities.Category;
import com.joel.catalog.entities.Product;
import com.joel.catalog.repositories.CategoryRepository;
import com.joel.catalog.repositories.ProductRepository;
import com.joel.catalog.services.exception.DatabaseException;
import com.joel.catalog.services.exception.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDto> findAllPaged(Pageable pageable){
		Page<Product> products = productRepository.findAll(pageable);
		return products.map(product -> new ProductDto(product));
		
	}
	
	@Transactional(readOnly = true)
	public ProductDto findById(Long id) {
		Optional<Product> productOptional = productRepository.findById(id);
		var product =  productOptional.orElseThrow(
				() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDto(product, product.getCategories());
	}

	@Transactional
	public ProductDto save(ProductDto productDto) {
		var product = new Product();
		copyDtoToProduct(productDto, product);
		product =  productRepository.save(product);
		return new ProductDto(product);
	}


	@Transactional
	public ProductDto update(Long id, ProductDto productDto) {
		try {
			Product product = productRepository.getReferenceById(id);
			copyDtoToProduct(productDto, product);
			product = productRepository.save(product);
			return  new ProductDto(product);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found ," + id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
		
	}
	
	private void copyDtoToProduct(ProductDto productDto, Product product) {
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setDate(productDto.getDate());
		product.setImgUrl(productDto.getImgUrl());
		product.setPrice(productDto.getPrice());
		
		product.getCategories().clear();
		for(CategoryDto categoryDto : productDto.getCategories()) {
			Category category = categoryRepository.getReferenceById(categoryDto.getId());
			product.getCategories().add(category);
		}
		
	}

}








