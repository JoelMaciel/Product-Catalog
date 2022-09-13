package com.joel.catalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joel.catalog.dto.CategoryDto;
import com.joel.catalog.entities.Category;
import com.joel.catalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<CategoryDto> findAll(){
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().map(category -> new CategoryDto(category)).collect(Collectors.toList());
		
	}

}
