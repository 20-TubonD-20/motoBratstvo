package com.example.motobratstvo.ui.feed;

public class News {
    private String title;
    //private int image;
    private String text;
    private int id;

    public News(String title, String text, int id) {
        this.title = title;
    //    this.image = image;
        this.text = text;
        this.id = id;
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
    public int getId() { return id; }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setId(int id) { this.id = id; }
}