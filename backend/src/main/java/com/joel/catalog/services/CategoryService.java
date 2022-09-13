package com.joel.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joel.catalog.dto.CategoryDto;
import com.joel.catalog.entities.Category;
import com.joel.catalog.repositories.CategoryRepository;
import com.joel.catalog.services.exception.EntityNotFoundException;

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
				() -> new EntityNotFoundException("Entity not found"));
		return new CategoryDto(category);
	}

	@Transactional
	public CategoryDto save(CategoryDto categoryDto) {
		var category = new Category();
		category.setName(categoryDto.getName());
		category =  categoryRepository.save(category);
		return new CategoryDto(category);
	}

	
}








