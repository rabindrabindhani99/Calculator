package com.grabs4buisness.calculator;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryCursorAdapter extends RecyclerView.Adapter<HistoryCursorAdapter.ViewHolder> {
    private Cursor cursor;

    public HistoryCursorAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return; // Failed to move to the position
        }

        String historyItem = cursor.getString(cursor.getColumnIndexOrThrow("historyItem"));

        // Bind data to your item views here
        holder.historyItemTextView.setText(historyItem);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView historyItemTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            historyItemTextView = itemView.findViewById(R.id.historyItemTextView);
        }
    }

    public void changeCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }
}
