package com.example.grocery;

import java.util.Map;

public class Product {
    private int id;
    private int quantity;
    private double price;
    private String unit;
    private String name;

    public Product() {
    }
    public Product(String name, int quantity, double price, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.unit = unit;
        //product id is generated from its name
        this.id = name.hashCode();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
