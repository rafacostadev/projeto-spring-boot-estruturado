package com.aulasjava.DSCommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aulasjava.DSCommerce.DTO.CategoryDTO;
import com.aulasjava.DSCommerce.repositories.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		return repository.findAll().stream().map(x -> new CategoryDTO(x)).toList();
	}
}
