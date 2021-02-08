package com.example.UAS.service.datamodel;

import java.util.ArrayList;

public class BookResponse {

    private String nim;
    private String nama;

    private ArrayList<BookItemResponse> products;

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public ArrayList<BookItemResponse> getProducts() {
        return products;
    }
}
