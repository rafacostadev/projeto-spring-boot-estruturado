package com.aulasjava.DSCommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aulasjava.DSCommerce.DTO.CategoryDTO;
import com.aulasjava.DSCommerce.DTO.ProductDTO;
import com.aulasjava.DSCommerce.DTO.ProductMinDTO;
import com.aulasjava.DSCommerce.entities.Product;
import com.aulasjava.DSCommerce.exceptions.DbException;
import com.aulasjava.DSCommerce.exceptions.ResourceNotFoundException;
import com.aulasjava.DSCommerce.repositories.CategoryRepository;
import com.aulasjava.DSCommerce.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
		return productRepository.findAll(name, pageable).map(x -> new ProductMinDTO(x));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		return new ProductDTO(productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!")));
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product product = new Product();
		DtoToProduct(dto, product);

		return new ProductDTO(productRepository.save(product));
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		if (!productRepository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado!");
		}
		Product product = productRepository.getReferenceById(id);
		DtoToProduct(dto, product);
		return new ProductDTO(productRepository.save(product));
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!productRepository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado!");
		}
		try {
			productRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DbException("Falha na integridade referencial!");
		}
	}

	private void DtoToProduct(ProductDTO dto, Product product) {
		product.setDescription(dto.getDescription());
		product.setImgUrl(dto.getImgUrl());
		product.setName(dto.getName());
		product.setPrice(dto.getPrice());
		product.getCategories().clear();
		for (CategoryDTO category : dto.getCategories()) {
			product.addCategory(categoryRepository.findById(category.getId()).get());
		}
	}
}
