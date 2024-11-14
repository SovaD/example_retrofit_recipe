package com.example.example_retrofit_recipe.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example_retrofit_recipe.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<String> list;
    Context context;
    private OnClickListener onClickListener;
    public int selectedPosition = -1;

    public CategoryAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_category_item, parent, false);

        // Passing view to ViewHolder
        CategoryAdapter.ViewHolder viewHolder = new CategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        String category = list.get(position);
        Log.i("taggg", category);
        if (position == selectedPosition)
            holder.tCategory.setTextColor(context.getResources().getColor(R.color.selected));
        else
            holder.tCategory.setTextColor(context.getResources().getColor(R.color.non_selected));
        holder.tCategory.setText(category);
        // Set click listener on the item view
        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position, category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Setter for the click listener
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // Interface for the click listener
    public interface OnClickListener {
        void onClick(int position, String category);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tCategory = itemView.findViewById(R.id.tCategory);

            // Set click listener on the ViewHolder's item view
            itemView.setOnClickListener(view -> {
                if (onClickListener != null) {
                    onClickListener.onClick(getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
        }
    }
}