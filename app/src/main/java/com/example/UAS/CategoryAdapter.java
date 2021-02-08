package com.example.UAS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<String> categories;
    private ICategoryAction action;

    public CategoryAdapter(ArrayList<String> categories, ICategoryAction action){
        this.categories = categories;
        this.action = action;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_item_layout, parent, false);
        return new CategoryViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String category = categories.get(position);
        holder.tvCategory.setText(category);
        holder.tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action.onItemClick(category);
            }
        });
    }

    public void updateData(ArrayList<String> newCategories){
        this.categories.clear();
        this.categories.addAll(newCategories);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llLayout;
        private Button tvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            llLayout = itemView.findViewById(R.id.ll_layout);
            tvCategory = itemView.findViewById(R.id.tv_category);
        }
    }
}
