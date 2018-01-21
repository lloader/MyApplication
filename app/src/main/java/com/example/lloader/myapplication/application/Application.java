package com.example.lloader.myapplication.application;

import android.content.Context;
import android.widget.Toast;

import it.sephiroth.android.library.picasso.Picasso;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alexander Garkavenko
 */

public class Application extends android.app.Application {

    private static Retrofit mRetrofit;
    public static String BASE_URL_TO_IMAGES = "https://image.tmdb.org/t/p/w500/";

    @Override
    public void onCreate() {
        super.onCreate();
        mRetrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/3/")
                .build();
    }

    public static Retrofit sRetrofit() {
        return mRetrofit;
    }

}
