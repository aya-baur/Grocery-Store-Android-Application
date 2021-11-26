package com.example.grocery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class Order {
    private int id;
    private String customer_name;
    private int customer_id;
    private long time;
    private boolean ready_pickup;
    private ArrayList<Integer> product_ids;

    public Order() {
    }
    public Order(String customer_name, int customer_id, ArrayList<Integer> product_ids) {
        this.customer_name = customer_name;
        this.customer_id = customer_id;
        this.product_ids = product_ids;
        this.time = new Date().getTime();
        // id is generated by a combination of milliseconds and the customer id (unique to email)
        this.id = (int)(this.time % 100000 + 100000*customer_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isReady_pickup() {
        return ready_pickup;
    }

    public void setReady_pickup(boolean ready_pickup) {
        this.ready_pickup = ready_pickup;
    }

    public ArrayList<Integer> getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(ArrayList<Integer> product_ids) {
        this.product_ids = product_ids;
    }
}
