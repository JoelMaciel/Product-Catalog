package com.joel.catalog.dto;

import java.io.Serializable;

import com.joel.catalog.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long categoryId;
	private String name;
	
	public CategoryDto(Category category) {
		this.categoryId = category.getCategoryId();
		this.name = category.getName();
	}

}
