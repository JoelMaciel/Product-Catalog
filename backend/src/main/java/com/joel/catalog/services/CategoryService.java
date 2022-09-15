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
import com.joel.catalog.entities.Category;
import com.joel.catalog.repositories.CategoryRepository;
import com.joel.catalog.services.exception.DatabaseException;
import com.joel.catalog.services.exception.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDto> findAllPaged(Pageable pageable){
		Page<Category> categories = categoryRepository.findAll(pageable);
		return categories.map(category -> new CategoryDto(category));
		
	}
	
	@Transactional(readOnly = true)
	public CategoryDto findById(Long id) {
		Optional<Category> categoryOptional = categoryRepository.findById(id);
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
	public CategoryDto update(Long id, CategoryDto categoryDto) {
		try {
			Category category = categoryRepository.getReferenceById(id);
			category.setName(categoryDto.getName());
			category = categoryRepository.save(category);
			return  new CategoryDto(category);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			categoryRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found ," + id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
		
	}

}








