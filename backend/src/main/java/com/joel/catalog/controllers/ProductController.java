package com.joel.catalog.controllers;

import java.net.URI;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joel.catalog.dto.ProductDTO;
import com.joel.catalog.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(
			@RequestParam(value = "categoryId", defaultValue = "0") Long categoryId,
			@RequestParam(value = "name", defaultValue = "") String name,
			Pageable pageable) {

		Page<ProductDTO> pages = productService.findAllPaged(categoryId, name.trim(), pageable);
		return ResponseEntity.ok().body(pages);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		var product = productService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}

	@PostMapping
	public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO productDto) {
		productDto = productService.save(productDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productDto.getId()).toUri();

		return ResponseEntity.created(uri).body(productDto);

	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO productDto) {
		productDto = productService.update(id, productDto);
		return ResponseEntity.status(HttpStatus.OK).body(productDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		productService.delete(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
