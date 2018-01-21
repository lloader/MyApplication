package com.example.lloader.myapplication.retrofit;

import com.example.lloader.myapplication.mvp.model.FilmsBlock;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Alexander Garkavenko
 */

public interface FilmsApi {

    @GET("discover/movie?api_key=6ccd72a2a8fc239b13f209408fc31c33&language=ru-RU&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    Call<FilmsBlock> getAllFilms();

    @GET("search/movie?api_key=6ccd72a2a8fc239b13f209408fc31c33&language=ru-RU&page=1&include_adult=false")
    Call<FilmsBlock> getFilms(@Query("query") String query);
}
