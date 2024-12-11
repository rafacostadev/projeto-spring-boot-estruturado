package com.aulasjava.DSCommerce.DTO;

public class CategoryDTO {
	private Long id;
	private String name;

	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
