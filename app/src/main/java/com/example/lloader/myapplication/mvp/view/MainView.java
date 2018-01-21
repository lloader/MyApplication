package com.example.lloader.myapplication.mvp.view;

import android.content.Context;

import com.example.lloader.myapplication.mvp.model.Film;

import java.util.List;

/**
 * Created by Alexander Garkavenko
 */

public interface MainView {
    void updateList();
    void showNetworkError();
    void setDataToList(List<Film> data);
    void showErrorWithNoContent();
    void hideAllNotificationComponents();
    void showList();
    void showNetworkErrorToast();
    void showNotFoundError(String query);
    void showProgressBar();
    void hideProgressBars();
    Context getContext();
}
