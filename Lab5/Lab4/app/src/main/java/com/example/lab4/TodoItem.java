package com.example.lab4;

public class TodoItem {
    private int id;
    private String text;
    private boolean isUrgent;

    public TodoItem(int id, String text, boolean isUrgent) {
        this.id = id;
        this.text = text;
        this.isUrgent = isUrgent;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }
}
