package com.example.foodies.tabs;

public class CartModel {
    int img;
    String f_name,f_prize;

    @Override
    public String toString() {
        return "CartModel{f_name='" + f_name + "', f_prize='" + f_prize + "', img='" + img + "'}";
    }
    public CartModel(int img, String f_name, String f_prize) {
        this.img = img;
        this.f_name = f_name;
        this.f_prize = f_prize;
    }
}
