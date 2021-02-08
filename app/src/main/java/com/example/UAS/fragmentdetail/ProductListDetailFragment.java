package com.example.UAS.fragmentdetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.UAS.R;
import com.example.UAS.cart.CartActivity;
import com.example.UAS.model.Cart;
import com.example.UAS.service.ApiClient;
import com.example.UAS.service.datamodel.BookItemResponse;
import com.example.UAS.service.datamodel.BookResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListDetailFragment extends Fragment {

    private Integer selectedBookId;
    private ImageView img;
    private TextView tvName, tvDescription, tvPrice, tvAuthor, tvType, tvCategory;
    private Button btnAddToCart;
    private BookItemResponse bookDetail;
    private LinearLayout llDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img = view.findViewById(R.id.iv_img);
        tvName = view.findViewById(R.id.tv_name);
        tvDescription = view.findViewById(R.id.tv_description);
        tvPrice = view.findViewById(R.id.tv_price);
        tvAuthor = view.findViewById(R.id.tv_author);
        tvType = view.findViewById(R.id.tv_type);
        tvCategory = view.findViewById(R.id.tv_category);
        llDetail = view.findViewById(R.id.ll_detail);
        btnAddToCart = view.findViewById(R.id.btn_add_to_cart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart();
                cart.setBookTitle(bookDetail.getNama());
                cart.setAuthor(bookDetail.getAuthor());
                cart.setImg(bookDetail.getImg());
                cart.setQty(1);
                cart.setPrice(bookDetail.getPrice());
                CartActivity.addToCart(cart);
                Toast.makeText(requireContext(), "Add To Cart Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requireContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null){
            getBookDetailData();
        }
    }

    private void getBookDetailData(){
        ApiClient.getBookService().getBookById(selectedBookId, "2201803686", "Arthur")
                .enqueue(new Callback<BookResponse>() {
                    @Override
                    public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                        if (response.isSuccessful() && response.body() != null){
                            llDetail.setVisibility(View.VISIBLE);
                            BookResponse bookResponse = response.body();
                            ArrayList<BookItemResponse> products = bookResponse.getProducts();
                            bookDetail = products.get(0);
                            Glide.with(requireContext()).load(bookDetail.getImg()).into(img);
                            tvName.setText(bookDetail.getNama());
                            tvDescription.setText(bookDetail.getDescription());
                            tvPrice.setText(String.valueOf(bookDetail.getPrice()));
                            tvAuthor.setText(bookDetail.getAuthor());
                            tvType.setText(bookDetail.getType());
                            tvCategory.setText(bookDetail.getCategory());


                        }
                    }

                    @Override
                    public void onFailure(Call<BookResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void setSelectedBookId(Integer selectedBookId) {
        this.selectedBookId = selectedBookId;
    }
}
