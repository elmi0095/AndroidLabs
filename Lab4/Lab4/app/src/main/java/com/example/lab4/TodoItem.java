package com.example.lab4;
public class TodoItem {
    private String text;
    private boolean isUrgent;

    public TodoItem(String text, boolean isUrgent) {
        this.text = text;
        this.isUrgent = isUrgent;
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
