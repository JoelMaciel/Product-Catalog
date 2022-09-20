package com.joel.catalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joel.catalog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
