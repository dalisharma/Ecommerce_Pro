package com.ducat.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ducat.entity.Product;
import com.ducat.service.CartService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@RestController
public class PaymentController {

    @Autowired
    private RazorpayClient razorpayClient;

    @Autowired
    private CartService cartService;

    @PostMapping("/create-order")
    @ResponseBody
    public String createOrder() throws Exception {

        double total = cartService.getCart()
                .stream()
                .mapToDouble(Product::getPrice)
                .sum();

        JSONObject options = new JSONObject();

        options.put("amount", (int)(total * 100));
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = razorpayClient.orders.create(options);
        return order.toString();
    }
}