package com.aulasjava.DSCommerce.DTO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.aulasjava.DSCommerce.entities.Order;
import com.aulasjava.DSCommerce.entities.OrderItem;
import com.aulasjava.DSCommerce.entities.OrderStatus;

import jakarta.validation.constraints.NotEmpty;

public class OrderDTO {
	private Long id;
	private Instant moment;
	private OrderStatus status;
	private ClientDTO client;
	private PaymentDTO payment;
	@NotEmpty(message = "O pedido deve ter pelo menos um item!")
	private List<OrderItemDTO> items = new ArrayList<>();

	public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO client, PaymentDTO payment) {
		this.id = id;
		this.moment = moment;
		this.status = status;
		this.client = client;
		this.payment = payment;
	}

	public OrderDTO(Order entity) {
		id = entity.getId();
		moment = entity.getMoment();
		status = entity.getStatus();
		client = new ClientDTO(entity.getClient());
		payment = (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment());
		for (OrderItem order : entity.getItems()) {
			items.add(new OrderItemDTO(order));
		}
	}

	public Long getId() {
		return id;
	}

	public Instant getMoment() {
		return moment;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public ClientDTO getClient() {
		return client;
	}

	public PaymentDTO getPayment() {
		return payment;
	}

	public List<OrderItemDTO> getItems() {
		return items;
	}

	public double getTotal() {
		double sum = 0;
		for (OrderItemDTO orders : items) {
			sum += orders.getSubTotal();
		}
		return sum;
	}
}
