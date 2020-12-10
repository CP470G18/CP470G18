package com.example.prototype;

import java.util.ArrayList;

public class List {
    private String name;
    private ArrayList<Item> items;

    public List() {
    }

    public List(String name, String description, ArrayList<Item> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
