package com.aulasjava.DSCommerce.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aulasjava.DSCommerce.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT product FROM Product product WHERE UPPER(product.name) LIKE UPPER(CONCAT('%', :name, '%'))")
	Page<Product> findAll(String name, Pageable pageable);
}
