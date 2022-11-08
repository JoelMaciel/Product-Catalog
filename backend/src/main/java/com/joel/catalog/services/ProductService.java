package com.joel.catalog.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joel.catalog.dto.CategoryDTO;
import com.joel.catalog.dto.ProductDTO;
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
	public Page<ProductDTO> findAllPaged(Long categoryId, String name, Pageable pageable){
		List<Category> categories = (categoryId == 0) ? null : Arrays.asList(categoryRepository.getOne(categoryId)) ;
		Page<Product> products = productRepository.find(categories, name, pageable);
		productRepository.findProductsWithCategories(products.getContent());
		return products.map(product -> new ProductDTO(product, product.getCategories()));
		
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> productOptional = productRepository.findById(id);
		var product =  productOptional.orElseThrow(
				() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(product, product.getCategories());
	}

	@Transactional
	public ProductDTO save(ProductDTO productDto) {
		var product = new Product();
		copyDtoToProduct(productDto, product);
		product =  productRepository.save(product);
		return new ProductDTO(product);
	}


	@Transactional
	public ProductDTO update(Long id, ProductDTO productDto) {
		try {
			Product product = productRepository.getOne(id);
			copyDtoToProduct(productDto, product);
			product = productRepository.save(product);
			return  new ProductDTO(product);
			
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
	
	private void copyDtoToProduct(ProductDTO productDto, Product product) {
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setDate(productDto.getDate());
		product.setImgUrl(productDto.getImgUrl());
		product.setPrice(productDto.getPrice());
		
		product.getCategories().clear();
		for(CategoryDTO categoryDto : productDto.getCategories()) {
			Category category = categoryRepository.getOne(categoryDto.getId());
			product.getCategories().add(category);
		}
		
	}

}








