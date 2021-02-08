package com.example.UAS.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.UAS.R;
import com.example.UAS.fragmentdetail.ProductListDetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Integer selectedBookId = getIntent().getIntExtra("bookId", 0);
        ProductListDetailFragment productListDetailFragment = (ProductListDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_fragment);
        productListDetailFragment.setSelectedBookId(selectedBookId);
    }
}