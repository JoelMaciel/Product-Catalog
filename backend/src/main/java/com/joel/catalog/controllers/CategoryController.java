package com.joel.catalog.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<List<CategoryDto>> findAll(){
		List<CategoryDto> list = categoryService.findAll();
		return ResponseEntity.ok().body(list);
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

}
















