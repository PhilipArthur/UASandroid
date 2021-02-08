package com.example.UAS.productlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.UAS.R;
import com.example.UAS.service.datamodel.BookItemResponse;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<BookItemResponse> products;
    private Context context;
    private ProductListAction productListAction;


    public ProductAdapter(ArrayList<BookItemResponse> products, Context context, ProductListAction productListAction){
        this.products = products;
        this.context = context;
        this.productListAction = productListAction;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_item_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        BookItemResponse book = products.get(position);
        holder.llBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productListAction.onItemClick(book.getId());
            }
        });
        holder.tvName.setText(book.getNama());
        holder.tvAuthor.setText(book.getAuthor());
        holder.tvPrice.setText(String.valueOf(book.getPrice()));
        Glide.with(context).load(book.getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void updateData(ArrayList<BookItemResponse> newBooks){
        this.products.clear();
        this.products.addAll(newBooks);
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llBook;
        private ImageView img;
        private TextView tvName, tvAuthor, tvPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            llBook = itemView.findViewById(R.id.ll_book);
            img = itemView.findViewById(R.id.img);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }

}
