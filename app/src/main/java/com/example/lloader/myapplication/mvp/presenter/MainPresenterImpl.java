package com.example.lloader.myapplication.mvp.presenter;

import android.content.Context;

import com.example.lloader.myapplication.application.Application;
import com.example.lloader.myapplication.mvp.model.Film;
import com.example.lloader.myapplication.mvp.model.FilmsBlock;
import com.example.lloader.myapplication.mvp.model.db.FilmsLab;
import com.example.lloader.myapplication.mvp.view.MainView;
import com.example.lloader.myapplication.retrofit.FilmsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Alexander Garkavenko
 */

public class MainPresenterImpl implements MainPresenter, Callback<FilmsBlock> {

    private static MainPresenter sMainPresenter;


    public static MainPresenter getInstance(Context context) {
        if(sMainPresenter == null) {
            sMainPresenter = new MainPresenterImpl(context);
            return sMainPresenter;
        }
        return sMainPresenter;
    }

    public static void destroyInstance() {
        sMainPresenter = null;
    }

    private transient MainView mMainView;
    private List<Film> mFilms;
    private Retrofit mRetrofit;
    private State mState;
    private FilmsLab mFilmsLab;

    private String lastQuery = "";

    private MainPresenterImpl(Context context) {
        mRetrofit = Application.sRetrofit();
        mMainView = null;
        mState = State.BEGIN;
        mFilmsLab = new FilmsLab(context);

        mFilms = new ArrayList<>(20);
    }

    private void getRequestToFilmList() {
        FilmsApi api = mRetrofit.create(FilmsApi.class);
        if(lastQuery.length() == 0) {
            api.getAllFilms().enqueue(this);
        } else {
            api.getFilms(lastQuery).enqueue(this);
        }
        setState(State.LOADING);
    }

    private void setState(State state) {
        mState = state;
        if(mMainView == null) {
            return;
        }
        switch (state) {
            case LOADING:
                mMainView.showProgressBar();
                break;
            case ERROR:
                mMainView.showNetworkErrorToast();
                setState(State.WAITING);
                break;
            case REQUEST_ERROR:
                mMainView.showNetworkError();
            case WAITING:
                mMainView.setDataToList(mFilms);
                break;
            case NOT_FOUND:
                mMainView.showNotFoundError(getLastQuery());
                break;
            case SUCCESSFUL_LOAD:
                mMainView.updateList();
                mMainView.hideAllNotificationComponents();
                mMainView.hideProgressBars();
                mMainView.showList();
                break;
            default:
                onSearch("");
        }
    }

    @Override
    public void addToFavorites(Film film) {
        film.setFavorite(true);
        mFilmsLab.addFavorite(film);
    }

    @Override
    public void deleteFromFavorites(Film film) {
        film.setFavorite(false);
        mFilmsLab.deleteFavorite(film);
    }

    @Override
    public void onSearch(String query) {
        lastQuery = query;
        getRequestToFilmList();
    }

    @Override
    public void update() {
        mMainView.updateList();
    }

    @Override
    public void attachView(MainView mainView) {
        mMainView = mainView;
        mMainView.setDataToList(mFilms);
        setState(mState);
    }

    @Override
    public void detachView() {
        mMainView = null;
    }

    public String getLastQuery() {
        return lastQuery;
    }

    @Override
    public void onResponse(Call<FilmsBlock> call, Response<FilmsBlock> response) {
        FilmsBlock filmsBlock = response.body();
        if(filmsBlock != null) {
            mFilms.clear();
            for(Film film : filmsBlock.getResults()) {
                mFilmsLab.checkForFavorite(film);
            }
            mFilms.addAll(filmsBlock.getResults());
            if(filmsBlock.getResults().size() == 0) {
                setState(State.NOT_FOUND);
                return;
            }
            setState(State.SUCCESSFUL_LOAD);
        }

    }

    @Override
    public void onFailure(Call<FilmsBlock> call, Throwable t) {
        if(mFilms.size() > 0) {
            setState(State.ERROR);
        } else {
            setState(State.REQUEST_ERROR);
        }
    }
}
