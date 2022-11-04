package com.joel.catalog.repositories;

import java.util.List;

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
			+ "(coalesce(:categories) is null or cats in :categories) and "
			+ "(lower(p.name) like  lower(concat('%',:name,'%')) )")
	Page<Product> find(List<Category> categories, String name, Pageable pageable);

}
