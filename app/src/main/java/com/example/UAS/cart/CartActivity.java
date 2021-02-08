package com.example.UAS.cart;

import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.UAS.R;
import com.example.UAS.model.Cart;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private static ArrayList<Cart> carts;
    private CartAdapter cartAdapter;
    private RecyclerView rvCarts;
    private TextView tvTotalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        setContentView(R.layout.activity_cart);
        if (carts == null) carts = new ArrayList<>();
//        setTotalPrice();
        initAdapter();
    }

    private void setTotalPrice(){
            long totalPrice = 0 ;
            for (Cart cart : carts) {
                totalPrice += (cart.getPrice() * (float) cart.getQty());
            }
//            tvTotalPrice.setText(String.valueOf(totalPrice));
        }


    private  void initAdapter(){
        CartAdapter.CartAction cartAction = new CartAdapter.CartAction() {
            @Override
            public void onQuantityChange(long totalPrice) {
                tvTotalPrice.setText(String.valueOf(totalPrice));
            }
        };
        rvCarts = findViewById(R.id.rv_carts);
        cartAdapter = new CartAdapter(carts,this, (CartAdapter.CartAction) CartAdapter.CartAction);
        rvCarts.setLayoutManager(new LinearLayoutManager(this));
        rvCarts.setAdapter(cartAdapter);
    }

    public static void addToCart(Cart cart){
        if (carts == null) {
            carts = new ArrayList<>();
            carts.add(cart);
        }


    }
}
