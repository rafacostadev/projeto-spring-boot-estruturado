package com.aulasjava.DSCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aulasjava.DSCommerce.entities.OrderItem;
import com.aulasjava.DSCommerce.entities.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
