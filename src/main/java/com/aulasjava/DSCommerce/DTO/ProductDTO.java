package com.aulasjava.DSCommerce.DTO;

import java.util.ArrayList;
import java.util.List;

import com.aulasjava.DSCommerce.entities.Category;
import com.aulasjava.DSCommerce.entities.Product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {
	private Long id;
	@Size(min = 3, max = 80, message = "Deve ter entre 3 e 80 caracteres!")
	private String name;
	@Size(min = 10, message = "Deve ter no mínimo 10 caracteres!")
	private String description;
	@NotNull
	@Positive
	private Double price;
	private String imgUrl;
	@NotEmpty(message = "Deve ter ao menos uma categoria!")
	private List<CategoryDTO> categories = new ArrayList<>();

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}

	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
		for (Category cat : entity.getCategories()) {
			categories.add(new CategoryDTO(cat.getId(), cat.getName()));
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getPrice() {
		return price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void addCategory(CategoryDTO category) {
		categories.add(category);
	}

}
