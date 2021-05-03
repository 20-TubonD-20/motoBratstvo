package com.example.motobratstvo.ui.feed;

public class News {
    private String title;
    //private int image;
    private String date;
    private String text;
    private int id;

    public News(String title, String text, String date, int id) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public String getText() {
        return this.text;
    }
    public String getDate() { return date; }
    public int getId() { return id; }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setId(int id) { this.id = id; }
    public void setDate(String date) { this.date = date; }
}