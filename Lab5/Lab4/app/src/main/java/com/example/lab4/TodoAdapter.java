package com.example.lab4;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends BaseAdapter {

    private List<TodoItem> todoList;
    private Context context;

    public TodoAdapter(Context context, List<TodoItem> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_todo, parent, false);
        }

        // Get the current TodoItem from the list
        TodoItem todoItem = todoList.get(position);

        // Set the text of the TextView to the "To Do" text
        TextView textViewTodo = convertView.findViewById(R.id.textViewTodo);
        textViewTodo.setText(todoItem.getText());

        // Check if the item is urgent and update the row background and text color accordingly
        if (todoItem.isUrgent()) {
            convertView.setBackgroundColor(Color.RED);
            textViewTodo.setTextColor(Color.WHITE);
        } else {
            // Reset the background and text color for non-urgent items
            convertView.setBackgroundColor(Color.TRANSPARENT);
            textViewTodo.setTextColor(Color.BLACK);
        }

        return convertView;
    }
}
