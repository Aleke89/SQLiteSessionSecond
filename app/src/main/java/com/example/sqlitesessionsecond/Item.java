package com.example.sqlitesessionsecond;
import android.graphics.Bitmap;

public class Item {
    private Bitmap image;
    private String title;
    private String date;

    public Item(Bitmap image, String title, String date) {
        this.image = image;
        this.title = title;
        this.date = date;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}