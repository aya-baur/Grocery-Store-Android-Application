package com.example.grocery;

import java.util.HashMap;
import java.util.Map;

public class Store {
    private int id;
    private String name;
    private String email;
    private String password;
    // products map product_id to product
    private Map<String, Product> products;
    // orders map order_id to order
    private Map<String, Order> orders;
    public Store() {
        this.orders = new HashMap<>();
        this.products = new HashMap<>();
    }
    public Store(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = email.hashCode();
        this.products = new HashMap<>();
        this.orders = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    public Map<String, Order> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, Order> orders) {
        this.orders = orders;
    }
}
