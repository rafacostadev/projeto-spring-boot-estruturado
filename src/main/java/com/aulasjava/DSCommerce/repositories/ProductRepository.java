package com.aulasjava.DSCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aulasjava.DSCommerce.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
