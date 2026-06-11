package com.ducat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ducat.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{

}
