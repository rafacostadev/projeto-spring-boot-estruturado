package com.aulasjava.DSCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aulasjava.DSCommerce.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
