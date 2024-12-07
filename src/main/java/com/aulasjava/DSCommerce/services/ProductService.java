package com.aulasjava.DSCommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aulasjava.DSCommerce.DTO.ProductDTO;
import com.aulasjava.DSCommerce.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repository;

	public Page<ProductDTO> findAll(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new ProductDTO(x));
	}
}
