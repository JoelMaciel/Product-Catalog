package com.joel.catalog.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public ResponseEntity<Page<CategoryDto>> findAll(Pageable pageable){
				
		Page<CategoryDto> pages = categoryService.findAllPaged(pageable);
		return ResponseEntity.ok().body(pages);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto>  findById(@PathVariable Long id) {
		var category = categoryService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(category);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto categoryDto) {
		categoryDto = categoryService.save(categoryDto);
	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(categoryDto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(categoryDto);
				
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryDto categoryDto){
		categoryDto = categoryService.update(id, categoryDto);
		return ResponseEntity.status(HttpStatus.OK).body(categoryDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		categoryService.delete(id);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	


}
















