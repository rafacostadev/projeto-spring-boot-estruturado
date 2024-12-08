package com.aulasjava.DSCommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aulasjava.DSCommerce.DTO.ProductDTO;
import com.aulasjava.DSCommerce.entities.Product;
import com.aulasjava.DSCommerce.exceptions.DbException;
import com.aulasjava.DSCommerce.exceptions.ResourceNotFoundException;
import com.aulasjava.DSCommerce.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		return repository.findAll(pageable).map(x -> new ProductDTO(x));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		return new ProductDTO(
				repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!")));
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product product = new Product();
		DtoToProduct(dto, product);
		return new ProductDTO(repository.save(product));
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado!");
		}
		Product product = repository.getReferenceById(id);
		DtoToProduct(dto, product);
		return new ProductDTO(repository.save(product));
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado!");
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DbException("Falha na integridade referencial!");
		}
	}

	private void DtoToProduct(ProductDTO dto, Product product) {
		product.setDescription(dto.getDescription());
		product.setImgUrl(dto.getImgUrl());
		product.setName(dto.getName());
		product.setPrice(dto.getPrice());
	}
}
