package com.aulasjava.DSCommerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aulasjava.DSCommerce.DTO.OrderDTO;
import com.aulasjava.DSCommerce.DTO.OrderItemDTO;
import com.aulasjava.DSCommerce.entities.Order;
import com.aulasjava.DSCommerce.entities.OrderItem;
import com.aulasjava.DSCommerce.entities.OrderStatus;
import com.aulasjava.DSCommerce.entities.Product;
import com.aulasjava.DSCommerce.entities.User;
import com.aulasjava.DSCommerce.exceptions.ResourceNotFoundException;
import com.aulasjava.DSCommerce.repositories.OrderItemRepository;
import com.aulasjava.DSCommerce.repositories.OrderRepository;
import com.aulasjava.DSCommerce.repositories.ProductRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!"));
		authService.validateSelfOrAdmin(order.getClient().getId());
		return new OrderDTO(order);
	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		Order order = new Order();
		User client = userService.authenticated();
		order.setMoment(Instant.now());
		order.setClient(client);
		order.setStatus(OrderStatus.WAITING_PAYMENT);

		for (OrderItemDTO item : dto.getItems()) {
			Product product = productRepository.getReferenceById(item.getProductId());
			OrderItem orderItem = new OrderItem(order, product, item.getQuantity(), product.getPrice());
			order.addItems(orderItem);
		}
		orderRepository.save(order);
		orderItemRepository.saveAll(order.getItems());
		return new OrderDTO(order);
	}
}
