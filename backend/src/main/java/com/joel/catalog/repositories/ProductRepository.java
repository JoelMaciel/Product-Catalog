package com.joel.catalog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joel.catalog.entities.Category;
import com.joel.catalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("select distinct p from Product p inner join p.categories cats where "
			+ "(:category is null or :category in cats)")
	Page<Product> find(Category category, Pageable pageable);

}
