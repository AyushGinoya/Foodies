package com.example.foodies.tabs;

public class CartModel {
    byte[] img;
    String name;
    String price;
    int quantity;

    public CartModel(byte[] img, String name, String price, int quantity) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
