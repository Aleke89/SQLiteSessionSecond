package com.example.sqlitesessionsecond;

import android.graphics.Bitmap;

public class Property {
    private Bitmap image;
    private String date;
    private String price;
    private String rule;

    public Property(Bitmap image, String date, String price, String rule) {
        this.image = image;
        this.date = date;
        this.price = price;
        this.rule = rule;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
