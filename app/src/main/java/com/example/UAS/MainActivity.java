package com.example.UAS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.UAS.detail.DetailActivity;
import com.example.UAS.fragmentdetail.ProductListDetailFragment;
import com.example.UAS.productlist.ProductListAction;
import com.example.UAS.productlist.ProductListFragment;
import com.example.UAS.service.ApiClient;
import com.example.UAS.service.datamodel.BookItemResponse;
import com.example.UAS.service.datamodel.BookResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ProductListAction {

    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_categories);
        loadBookData();
        initAdapter();
    }


    private void initAdapter() {
        ICategoryAction action = new ICategoryAction() {
            @Override
            public void onItemClick(String category) {
                ProductListFragment productListFragment = new ProductListFragment();

                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                productListFragment.setArguments(bundle);


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, productListFragment)
                        .commit();
            }
        };
        categoryAdapter = new CategoryAdapter(new ArrayList<>(), action);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(categoryAdapter);
    }

    private void loadBookData() {
        ApiClient.getBookService().getAllBook( "2201803686",  "Arthur")
                .enqueue(new Callback<BookResponse>() {
                    @Override
                    public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                        if (response.isSuccessful() && response.body() != null){
                            ArrayList<String> categories = new ArrayList<>();
                            BookResponse bookResponse = response.body();
                            ArrayList<BookItemResponse> products = bookResponse.getProducts();
                            for (BookItemResponse itemResponse : products) {
                                if (!categories.contains(itemResponse.getCategory())){
                                    categories.add(itemResponse.getCategory());
                                }
                            }
                            categoryAdapter.updateData(categories);

                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, new ProductListFragment())
                                    .commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onItemClick(Integer bookId) {
        if (findViewById(R.id.detail_container) != null){
            ProductListDetailFragment fragment = new ProductListDetailFragment();
            fragment.setSelectedBookId(bookId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .commit();
        }

        else{
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("bookId", bookId);
            startActivity(intent);
        }

    }
}