package com.example.example_retrofit_recipe.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.example_retrofit_recipe.R;
import com.example.example_retrofit_recipe.entities.Recipe;

import java.net.URI;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<Recipe> list;
    Context context;
    private ItemAdapter.OnClickListener onClickListener;

    public ItemAdapter(Context context, List<Recipe> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_item, parent, false);

        // Passing view to ViewHolder
        ItemAdapter.ViewHolder viewHolder = new ItemAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Recipe item = list.get(position);
        Log.i("taggg", item.getName());
        holder.textName.setText(item.getName());
        holder.imageView.setImageURI(Uri.parse(item.getImage()));
        Glide.with(context).load(item.getImage()).into(holder.imageView);
        holder.ratingBar.setRating(item.getRating());
        // Set click listener on the item view
        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position, item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Setter for the click listener
    public void setOnClickListener(ItemAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    // Interface for the click listener
    public interface OnClickListener {
        void onClick(int position, Recipe recipe);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        RatingBar ratingBar;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.tName);
            ratingBar = itemView.findViewById(R.id.rating);
            imageView = itemView.findViewById(R.id.image);

            // Set click listener on the ViewHolder's item view
            itemView.setOnClickListener(view -> {
                if (onClickListener != null) {
                    onClickListener.onClick(getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
        }
    }
}
