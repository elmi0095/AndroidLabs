package com.example.lab4;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.widget.AdapterView;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText todoEditText;
    private Switch urgentSwitch;
    private Button addButton;
    private ListView todoListView;
    private TodoAdapter todoAdapter;
    private List<TodoItem> todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoEditText = findViewById(R.id.todoEditText);
        urgentSwitch = findViewById(R.id.urgentSwitch);
        addButton = findViewById(R.id.addButton);
        todoListView = findViewById(R.id.todoListView);

        // Update button text with localized strings
        addButton.setText(R.string.button_add);

        // Initialize the List of TodoItem and the custom adapter
        todoList = new ArrayList<>();
        todoAdapter = new TodoAdapter(todoList);
        todoListView.setAdapter(todoAdapter);

        // Add Button click listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoText = todoEditText.getText().toString().trim();
                boolean isUrgent = urgentSwitch.isChecked();

                if (!todoText.isEmpty()) {
                    TodoItem todoItem = new TodoItem(todoText, isUrgent);
                    todoList.add(todoItem);
                    todoAdapter.notifyDataSetChanged();
                    todoEditText.getText().clear();
                }
            }
        });

        // Add onItemLongClick listener to the ListView
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // Show AlertDialog for confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialog_title);
                builder.setMessage(getString(R.string.dialog_message, position));
                builder.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete the item at the specified row and refresh the list
                        if (position >= 0 && position < todoList.size()) {
                            todoList.remove(position);
                            todoAdapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, R.string.item_deleted, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton(R.string.dialog_negative_button, null);
                builder.show();
                return true; // Consume the event so that onItemClick is not triggered
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // Override the attachBaseContext method to apply French locale if required
        // You can add more languages here based on user preferences or system settings
        Locale locale = new Locale("fr"); // For French
        Configuration configuration = new Configuration(newBase.getResources().getConfiguration());
        configuration.setLocale(locale);
        super.attachBaseContext(newBase.createConfigurationContext(configuration));
    }

    private class TodoAdapter extends BaseAdapter {

        private List<TodoItem> todoList;

        public TodoAdapter(List<TodoItem> todoList) {
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
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                view = inflater.inflate(R.layout.list_item_todo, parent, false);
            }

            // Get the current TodoItem from the list
            TodoItem todoItem = todoList.get(position);

            // Set the text of the TextView to the "To Do" text
            TextView textViewTodo = view.findViewById(R.id.textViewTodo);
            textViewTodo.setText(todoItem.getText());

            // Check if the item is urgent and update the row background and text color accordingly
            if (todoItem.isUrgent()) {
                view.setBackgroundColor(Color.RED);
                textViewTodo.setTextColor(Color.WHITE);
            } else {
                // Reset the background and text color for non-urgent items
                view.setBackgroundColor(Color.TRANSPARENT);
                textViewTodo.setTextColor(Color.BLACK);
            }

            return view;
        }
    }
}
