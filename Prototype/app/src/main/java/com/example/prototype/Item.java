package com.example.prototype;

public class Item {
    private String name;
    private Integer cost;
    private String description;

    public Item() {
    }

    public Item(String name, Integer cost, String description, String key) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
