package com.ducat.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ducat.entity.Order;
import com.ducat.entity.Product;
import com.ducat.repository.OrderRepository;
import com.ducat.repository.ProductRepository;
import com.ducat.repository.UserRepository;
import com.ducat.service.OrderService;
import com.ducat.service.ProductService;
import com.ducat.service.UserService;

@Controller
public class AdminController {
	@Autowired
	private ProductRepository repo;
	@Autowired
	private ProductService service;
	@Autowired
	private UserRepository urepo;
	@Autowired
	private UserService uservice;
	@Autowired
	private OrderRepository orepo;
	@Autowired
	private OrderService oservice;
	
	@GetMapping("/admin")
	public String dashboard(Model model) {
		model.addAttribute("totalProducts",repo.count());
		
		model.addAttribute("totalUsers",urepo.count());
		
		model.addAttribute("totalOrders",orepo.count());
		
		Double revenue=orepo.findAll()
				.stream()
				.mapToDouble(Order::getTotalAmount)
				.sum();
		model.addAttribute("revenue",revenue);
		
		List<Order> orders=orepo.findAll();
		List<Order> toporder=orders.stream().limit(5).toList();
		model.addAttribute("orders",toporder);
		return "admin";
	}
	//open form
		@GetMapping("/add-products")
		public String addProductPage(Model model)
		{
			model.addAttribute("product",new Product());
			return "add-products";
		}
		@PostMapping("/saveProduct")
		public String saveProduct(
		        @RequestParam(required = false) Long id,
		        @RequestParam("name") String name,
		        @RequestParam("price") double price,
		        @RequestParam("description") String description,
		        @RequestParam("image") MultipartFile file
		) throws IOException {

		    Product product;

		    // Update case
		    if (id != null) {
		        product = service.getProductById(id);
		    } else {
		        product = new Product();
		    }

		    product.setName(name);
		    product.setPrice(price);
		    product.setDescription(description);

		    if (!file.isEmpty()) {
		        String uploadDir = "src/main/resources/static/uploads/";

		        String fileName = file.getOriginalFilename();

		        File dir = new File(uploadDir);
		        if (!dir.exists()) {
		            dir.mkdirs();
		        }

		        Path path = Paths.get(uploadDir + fileName);
		        Files.write(path, file.getBytes());

		        product.setImgName(fileName);
		    }

		    service.saveProduct(product);

		    return "redirect:/adminpnl/products";
		}
	//search api
    @GetMapping("/adsearch")
    public String search(@RequestParam("keyword")String keyword,Model model) {
    		model.addAttribute("products",service.searchProduct(keyword));
    		model.addAttribute("keyword",keyword);
    		return "adminpnl/products";
    }
    
    @GetMapping("/usersearch")
    public String searchUser(@RequestParam("keyword")String keyword,Model model) {
    		model.addAttribute("users",uservice.searchUser(keyword));
    		model.addAttribute("keyword",keyword);
    		return "adminpnl/users";
    }
    
	@GetMapping("/adminpnl/products")
	public String allProducts(Model model) {
		model.addAttribute("products",repo.findAll());
		return "adminpnl/products";
	}
	
	@GetMapping("/adminpnl/users")
	public String userpnl(Model model) {
		model.addAttribute("users",urepo.findAll());
		return "adminpnl/users";
	}
	
	@GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {

        service.deleteProduct(id);

        return "redirect:/adminpnl/products";
    }
	
	@PostMapping("/delete-user/{id}")
	public String deleteUser(@PathVariable Long id) {
		uservice.deleteUser(id);
		return "redirect:/adminpnl/users";
	}
	
	@GetMapping("/edit-product/{id}")
	public String editProduct(@PathVariable Long id, Model model) {

	    Product product = service.getProductById(id);

	    model.addAttribute("product", product);

	    return "add-products"; // same page
	}
	
	@GetMapping("/adminpnl/orders")
	public String orderpnl(Model model) {
		model.addAttribute("orders",orepo.findAll());
		model.addAttribute("totalorders",orepo.count());
		return "adminpnl/orders";
	}
	@GetMapping("/ordersearch")
    public String searchOrder(@RequestParam("keyword")String keyword,Model model) {
    		model.addAttribute("orders",oservice.searchOrder(keyword));
    		model.addAttribute("keyword",keyword);
    		return "adminpnl/orders";
    }
	@GetMapping("/delete-order/{id}")
	public String deleteOrder(@PathVariable Long id) {
		oservice.deleteOrder(id);
		return "redirect:/adminpnl/orders";
	}
	@GetMapping("/view-order/{id}")
	public String viewOrder(@PathVariable Long id,Model model) {
		Order order=orepo.findById(id).orElse(null);
		model.addAttribute("order",order);
		return "adminpnl/order-details";
	}
	@GetMapping("/update-order-status/{id}")
	public String updateOrStatus(@PathVariable Long id) {
		Order order=oservice.getOrderById(id);
		if(order!=null) {
			if(order.getStatus().equalsIgnoreCase("Pending"))
				order.setStatus("Shipped");
			else if(order.getStatus().equalsIgnoreCase("Shipped"))
				order.setStatus("Delivered");
			else if(order.getStatus().equalsIgnoreCase("Delivered"))
				order.setStatus("Pending");
			oservice.saveOrder(order);
		}
		return "redirect:/adminpnl/orders";
	}
}
