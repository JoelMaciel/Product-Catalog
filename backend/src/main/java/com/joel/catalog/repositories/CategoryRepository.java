package com.joel.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joel.catalog.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	

}
