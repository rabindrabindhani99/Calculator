package com.grabs4buisness.calculator;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

public class HistoryActivity extends AppCompatActivity {
    MaterialButton backBtn, clearHistoryBtn;
    RecyclerView historyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Views initialization
        backBtn = findViewById(R.id.back_btn);
        clearHistoryBtn = findViewById(R.id.clearHistoryButton);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);

        // Initialize the database helper
        HistoryDatabaseHelper dbHelper = new HistoryDatabaseHelper(this);

        // Retrieve expressions as a Cursor from the database
        Cursor cursor = dbHelper.getAllHistoryItems();

        // Create an instance of your HistoryCursorAdapter with the cursor
        HistoryCursorAdapter adapter = new HistoryCursorAdapter(cursor);

        // Set the adapter for your RecyclerView
        historyRecyclerView.setAdapter(adapter);

        // Set the layout manager to display items from top to bottom
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        historyRecyclerView.setLayoutManager(layoutManager);

        // When Back button is clicked
        backBtn.setOnClickListener(v -> {
            super.onBackPressed();
            finish();
        });

        // When clear button is clicked, the history will get cleared.
        clearHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rowsDeleted = dbHelper.clearHistory();

                if (rowsDeleted > 0) {
                    // Update the adapter's cursor with the new data
                    Cursor newCursor = dbHelper.getAllHistoryItems();
                    adapter.changeCursor(newCursor);

                    Toast.makeText(HistoryActivity.this, "History cleared", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HistoryActivity.this, "No history to clear", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
