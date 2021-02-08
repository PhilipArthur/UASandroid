package com.example.UAS.productlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.UAS.R;
import com.example.UAS.service.ApiClient;
import com.example.UAS.service.datamodel.BookItemResponse;
import com.example.UAS.service.datamodel.BookResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends Fragment {

    private ProductAdapter productAdapter;
    private RecyclerView recyclerView;
    private ProductListAction productListAction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        recyclerView = view.findViewById(R.id.rv_products);
        loadBookData();
        initAdapter();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.productListAction = (ProductListAction) context;
    }

    private void initAdapter() {
        productAdapter = new ProductAdapter(new ArrayList<>(), requireContext(), productListAction);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(productAdapter);
    }

    private void loadBookData() {
        ApiClient.getBookService().getAllBook("2201803686", "Arthur")
                .enqueue(new Callback<BookResponse>() {
                    @Override
                    public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            BookResponse bookResponse = response.body();
                            ArrayList<BookItemResponse> products = bookResponse.getProducts();
                           if (getArguments() == null) {
                               productAdapter.updateData(products);

                           }
                           else {
                               String category = getArguments().getString("category");
                               ArrayList<BookItemResponse> filteredProducts = new ArrayList<>();
                               for (BookItemResponse itemResponse : products) {
                                   if (itemResponse.getCategory().equals(category)){
                                       filteredProducts.add(itemResponse);
                                   }
                               }
                               productAdapter.updateData(filteredProducts);
                           }
                        }
                    }

                    @Override
                    public void onFailure(Call<BookResponse> call, Throwable t) {
                        t.printStackTrace();

                    }

                });
    }
}



