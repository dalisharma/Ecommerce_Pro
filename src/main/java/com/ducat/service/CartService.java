package com.ducat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ducat.entity.Product;

@Service
public class CartService {
	private List<Product> cart=new ArrayList<>();
	
	public void addToCart(Product product) {
		cart.add(product);
	}
	public List<Product> getCart(){
		return cart;
	}
	
	public void removeFromCart(Long id) {

	    for (int i = 0; i < cart.size(); i++) {

	        if (cart.get(i).getId()==id) {
	            cart.remove(i);
	            break; // sirf ek item remove hoga
	        }
	    }
	}
	
	public void clearCart() {
		cart.clear();
	}

}
