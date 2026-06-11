package com.ducat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ducat.entity.Order;
import com.ducat.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository repo;
	
	public List<Order> searchOrder(String keyword){
		List<Order> allOrder=repo.findAll();
		List<Order> result=new ArrayList<>();
		for(Order o: allOrder) {
			if(o.getCustomerName().toLowerCase().contains(keyword))
				result.add(o);
		}
		return result;
	}
	public void deleteOrder(Long id) {
		repo.deleteById(id);
		
	}	
	
	public Order getOrderById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public void saveOrder(Order order) {
		repo.save(order);
	}
}
