package com.example.admin.myapplication.model;

import java.io.Serializable;

import io.realm.RealmObject;

public class Drink extends RealmObject implements Serializable{
    private String name;
    private double price;

    public Drink(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Drink() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
