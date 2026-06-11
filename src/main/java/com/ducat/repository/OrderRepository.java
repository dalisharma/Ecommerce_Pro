package com.ducat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ducat.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{

}
