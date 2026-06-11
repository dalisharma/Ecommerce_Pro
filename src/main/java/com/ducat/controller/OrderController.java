package com.ducat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ducat.entity.Order;
import com.ducat.entity.OrderItem;
import com.ducat.entity.Product;
import com.ducat.repository.OrderRepository;
import com.ducat.repository.ProductRepository;
import com.ducat.service.CartService;
import com.ducat.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController
{
 @Autowired
 private ProductRepository productRepo;
 @Autowired
 private CartService cartService;
 @Autowired
 private OrderRepository orderRepo;
 //Add to cart
 @GetMapping("/add-to-cart/{id}")
 public String addToCart(@PathVariable Long id)
 {
  Product p=productRepo.findById(id).orElse(null);
  cartService.addToCart(p);
  return "redirect:/products";
 }
 //view Cart
 @GetMapping("/cart")
 public String viewCart(Model model) {

     List<Product> cart = cartService.getCart();

     double totalAmount = cart.stream()
                              .mapToDouble(Product::getPrice)
                              .sum();

     model.addAttribute("cart", cart);
     model.addAttribute("totalAmount", totalAmount);

     return "cart";
 }
 // Place Order Page
 @GetMapping("/checkout")
 public String checkout(Model model) {

     List<Product> cart = cartService.getCart();

     double total = cart.stream()
                        .mapToDouble(Product::getPrice)
                        .sum();

     model.addAttribute("cart", cart);
     model.addAttribute("total", total);

     return "checkout";
 }

    // Save Order
 @PostMapping("/place-order")
 public String placeOrder(
 @RequestParam String name,
 @RequestParam String address,
 @RequestParam String paymentId,
 @RequestParam String razorOrderId)
 {

        List<Product> cart = cartService.getCart();

        Order order = new Order();
        order.setCustomerName(name);
        order.setAddress(address);

        double total = 0;
        List<OrderItem> items = new ArrayList<>();

        for (Product p : cart) {

            OrderItem item = new OrderItem();
            item.setProductName(p.getName());
            item.setPrice(p.getPrice());
            item.setQuantity(1);
            item.setOrder(order);

            total += p.getPrice();
            items.add(item);
        }

        order.setItems(items);
        order.setTotalAmount(total);
        
        order.setStatus("PAID");
        order.setRazorpayPaymentId(paymentId);
        order.setRazorpayOrderId(razorOrderId);

        orderRepo.save(order);

        cartService.clearCart();

        return "success";
    }
    @GetMapping("/remove-from-cart/{id}")
    public String removeFromCart(@PathVariable Long id) {

        cartService.removeFromCart(id);

        return "redirect:/cart";
    }
    @GetMapping("/home")
    public String home(Model model) {

        List<Product> products = productRepo.findAll();

        List<Product> topProducts = products.stream()
                                            .limit(3)
                                            .toList();

        model.addAttribute("products", topProducts);

        return "home";
    }
}
