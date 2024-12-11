package com.aulasjava.DSCommerce.DTO;

import com.aulasjava.DSCommerce.entities.OrderItem;

public class OrderItemDTO {
	private Long productId;
	private String name;
	private double price;
	private Integer quantity;
	private String imgUrl;

	public OrderItemDTO(Long id, String name, double price, Integer quantity, String imgUrl) {
		this.productId = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.imgUrl = imgUrl;
	}

	public OrderItemDTO(OrderItem entity) {
		productId = entity.getProduct().getId();
		name = entity.getProduct().getName();
		price = entity.getPrice();
		quantity = entity.getQuantity();
		imgUrl = entity.getProduct().getImgUrl();
	}

	public Long getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public double getSubTotal() {
		return price * quantity;
	}
}
