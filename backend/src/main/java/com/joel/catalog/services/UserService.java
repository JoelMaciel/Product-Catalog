package com.joel.catalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joel.catalog.dto.RoleDTO;
import com.joel.catalog.dto.UserDTO;
import com.joel.catalog.dto.UserInsertDTO;
import com.joel.catalog.entities.User;
import com.joel.catalog.repositories.RoleRepository;
import com.joel.catalog.repositories.UserRepository;
import com.joel.catalog.services.exception.DatabaseException;
import com.joel.catalog.services.exception.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository  roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);
		return users.map(user -> new UserDTO(user));
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		var user = userOptional.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new UserDTO(user);
	}
	
	@Transactional
	public UserDTO save(UserInsertDTO userDto) {
		var user = new User();
		copyDtoToUser(userDto, user);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user = userRepository.save(user);
		return new UserDTO(user);
	}
	
	@Transactional
	public UserDTO update(Long id, UserDTO userDto) {
		try {
			var user = userRepository.getReferenceById(id);
			copyDtoToUser(userDto, user);
			user = userRepository.save(user);
			return new UserDTO(user);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found.");
		}
		
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found.");
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private void copyDtoToUser(UserDTO userDto, User user) {
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		
		user.getRoles().clear();
		for(RoleDTO roleDto : userDto.getRoles()) {
			var role = roleRepository.getReferenceById(roleDto.getId());
			user.getRoles().add(role);
		}
	}
	
	
}























