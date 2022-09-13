package com.joel.catalog.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joel.catalog.dto.CategoryDto;
import com.joel.catalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<Page<CategoryDto>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction
			){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<CategoryDto> pages = categoryService.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(pages);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto>  findById(@PathVariable Long categoryId) {
		var category = categoryService.findById(categoryId);
		return ResponseEntity.status(HttpStatus.CREATED).body(category);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto categoryDto) {
		categoryDto = categoryService.save(categoryDto);
	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{categoryId}")
				.buildAndExpand(categoryDto.getCategoryId()).toUri();
		
		return ResponseEntity.created(uri).body(categoryDto);
				
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> update(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto){
		categoryDto = categoryService.update(categoryId, categoryDto);
		return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<Void> delete(@PathVariable Long categoryId) {
		categoryService.delete(categoryId);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	


}
















