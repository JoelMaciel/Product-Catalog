package com.joel.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joel.catalog.dto.CategoryDto;
import com.joel.catalog.entities.Category;
import com.joel.catalog.repositories.CategoryRepository;
import com.joel.catalog.services.exception.DatabaseException;
import com.joel.catalog.services.exception.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<CategoryDto> findAll(){
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().map(category -> new CategoryDto(category)).collect(Collectors.toList());
		
	}
	
	@Transactional(readOnly = true)
	public CategoryDto findById(Long categoryId) {
		Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
		Category category =  categoryOptional.orElseThrow(
				() -> new ResourceNotFoundException("Entity not found"));
		return new CategoryDto(category);
	}

	@Transactional
	public CategoryDto save(CategoryDto categoryDto) {
		var category = new Category();
		category.setName(categoryDto.getName());
		category =  categoryRepository.save(category);
		return new CategoryDto(category);
	}

	@Transactional
	public CategoryDto update(Long categoryId, CategoryDto categoryDto) {
		try {
			Category category = categoryRepository.getReferenceById(categoryId);
			category.setName(categoryDto.getName());
			category = categoryRepository.save(category);
			return  new CategoryDto(category);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + categoryId);
		}
	}


	public void delete(Long categoryId) {
		try {
			categoryRepository.deleteById(categoryId);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + categoryId);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
		
		
	}
	
	

	
}








