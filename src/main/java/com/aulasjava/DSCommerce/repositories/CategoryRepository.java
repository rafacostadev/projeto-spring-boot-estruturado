package com.aulasjava.DSCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aulasjava.DSCommerce.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
