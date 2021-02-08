package com.example.UAS.service;

import com.example.UAS.service.datamodel.BookResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/staging/book")
    Call<BookResponse> getAllBook(@Query("nim") String nim, @Query("nama") String name);

    @GET("/staging/book/{bookId}")
    Call<BookResponse> getBookById(@Path("bookId") Integer bookId, @Query("nim") String nim, @Query("nama") String name);
}
