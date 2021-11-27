package com.example.grocery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customer {
    private int id;
    private String name;
    private String email;
    private String password;
    // order_data maps index to order_id and store_id
    private ArrayList<Map<String, Integer>> orders_data;
    public Customer() {
    }
    public Customer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = email.hashCode();
        this.orders_data = new ArrayList<>();
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

    public ArrayList<Map<String, Integer>> getOrders_data() {
        return orders_data;
    }

    public void setOrders_data(ArrayList<Map<String, Integer>> orders_data) {
        this.orders_data = orders_data;
    }
}
