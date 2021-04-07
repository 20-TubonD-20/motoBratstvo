package com.example.motobratstvo.ui.feed;

public class News {
    private String title;
    //private int image;
    private String text;

    public News(String title, String text, int image) {
        this.title = title;
    //    this.image = image;
        this.text = text;
    }
    public News(String title, String text) {
        this.title = title;
        this.text = text;
    }
    public String getTitle() {
        return this.title;
    }
    public String getText() {
        return this.text;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*public int getImage() {
        return this.image;
    }
    public void setImage(int image) {
        this.image = image;
    }
    */

    public void setText(String text) {
        this.text = text;
    }
}