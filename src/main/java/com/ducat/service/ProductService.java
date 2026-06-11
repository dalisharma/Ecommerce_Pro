package com.ducat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.ducat.entity.Product;
import com.ducat.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repo;
	
	public void saveProduct(Product product) {
		repo.save(product);
	}
	//Linear search implementation
	public List<Product> searchProduct(String keyword){
		List<Product> allproduct=repo.findAll();
		List<Product> result=new ArrayList<>();
		for(Product p: allproduct) {
			if(p.getName().toLowerCase().contains(keyword.toLowerCase())) {
				result.add(p);
			}
		}
		return result;
	}
	public List<Product> getAllproducts(){
		return repo.findAll();
	}
	
	public void deleteProduct(Long id) {
		repo.deleteById(id);
		
	}	
	
	 public Product getProductById(Long id) {
	        return repo.findById(id).orElse(null);
	    }
}
