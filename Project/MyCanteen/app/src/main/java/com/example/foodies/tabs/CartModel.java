package com.example.foodies.tabs;

public class CartModel {
    public byte[] img;
    public String name;
    public String price;
    public int quantity;

    public CartModel(byte[] img, String name, String price, int quantity) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
