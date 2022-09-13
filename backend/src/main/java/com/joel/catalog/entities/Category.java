package com.joel.catalog.entities;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@Entity
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
}
