package com.example.lloader.myapplication.mvp.presenter;

import com.example.lloader.myapplication.mvp.model.Film;
import com.example.lloader.myapplication.mvp.view.MainView;

/**
 * Created by Alexander Garkavenko
 */

public interface MainPresenter {
    void onSearch(final String query);
    void update();
    void attachView(MainView mainView);
    void detachView();
    void addToFavorites(Film film);
    void deleteFromFavorites(Film film);
    String getLastQuery();
}
