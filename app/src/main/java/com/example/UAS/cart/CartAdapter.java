package com.example.UAS.cart;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.UAS.R;
import com.example.UAS.model.Cart;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    public static Object CartAction;
    private ArrayList<Cart> carts;
    private Context context;
    private CartAction action;

    public CartAdapter(ArrayList<Cart> carts, Context context, CartAction action) {
        this.carts = carts;
        this.context = context;
        this.action = action;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = carts.get(position);
        Glide.with(context).load(cart.getImg()).into(holder.img);
        holder.tvTitle.setText(cart.getBookTitle());
        holder.tvAuthor.setText(cart.getAuthor());
        holder.tvPrice.setText(String.valueOf(cart.getPrice()));
        holder.tvQty.setText(String.valueOf(cart.getQty()));
        holder.btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer currentQty = cart.getQty();
                if (currentQty > 1){
                    cart.setQty(currentQty - 1);
                    notifyItemChanged(position);
                    updateTotalPrice();
                }
            }
        });
        holder.btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.setQty(cart.getQty() + 1);
                notifyItemChanged(position);
                updateTotalPrice();
            }
        });
    }

    private void updateTotalPrice(){
        long total = 0 ;
        for (Cart cart : carts) {
            total += (cart.getPrice() * (float) cart.getQty());
        }
        action.onQuantityChange(total);

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        private TextView tvTitle, tvAuthor, tvPrice, tvQty;
        private ImageButton btnDecrement, btnIncrement;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_img);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvQty = itemView.findViewById(R.id.tv_qty);
            btnDecrement = itemView.findViewById(R.id.btn_decrement);
            btnIncrement = itemView.findViewById(R.id.btn_increment);
        }
    }

    public interface CartAction {
        void onQuantityChange(long totalPrice);
    }
}
