package com.ducat.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ducat.entity.Product;
import com.ducat.repository.ProductRepository;
import com.ducat.service.ProductService;


@Controller
public class ProductController 
{
	@Autowired
	private ProductService service;
	@Autowired
	private ProductRepository repo;

	    // Show Products
	    @GetMapping("/products")
	    public String products(Model model) {

	        model.addAttribute("products", repo.findAll());

	        return "products";
	    }
	    @GetMapping("/orders")
		public String adminOrderPage()
		{
			return "orders";
		}
	    @GetMapping("/users")
		public String adminUsersPage()
		{
			return "users";
		}
	    
	    //search api
	    @GetMapping("/search")
	    public String search(@RequestParam("keyword")String keyword,Model model) {
	    		model.addAttribute("products",service.searchProduct(keyword));
	    		model.addAttribute("keyword",keyword);
	    		return "products";
	    }
	}


