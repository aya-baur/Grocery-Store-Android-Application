package com.example.grocery;

import java.util.Map;

public class Product {
    private int id;
    private double price;
    private String unit;
    private String name;

    public Product() {
    }
    public Product(String name, double price, String unit) {
        this.name = name;
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
