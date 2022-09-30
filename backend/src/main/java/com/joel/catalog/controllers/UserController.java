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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joel.catalog.dto.UserDTO;
import com.joel.catalog.dto.UserInsertDTO;
import com.joel.catalog.dto.UserUpdateDTO;
import com.joel.catalog.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAllPaged(Pageable pageable) {
		Page<UserDTO> pages = userService.findAllPaged(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(pages);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		var userDto = userService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> save(@RequestBody @Valid UserInsertDTO userInsertDTO) {
		var userDto = userService.save(userInsertDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(userDto.getLastName()).toUri();
		
		return ResponseEntity.created(uri).body(userDto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid  @RequestBody UserUpdateDTO userDTO){
		var userDto = userService.update(id, userDTO);
		return ResponseEntity.status(HttpStatus.OK).body(userDto);
		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}



























