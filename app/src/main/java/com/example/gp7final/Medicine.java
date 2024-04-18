package com.example.gp7final;

public class Medicine {
    private String name;
    private int quantity;
    private int frequency;


    public Medicine(String name, int quantity, int frequency) {
        this.name = name;
        this.quantity = quantity;
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
