package com.example.lloader.myapplication.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lloader.myapplication.R;
import com.example.lloader.myapplication.mvp.view.adapter.FilmAdapter;
import com.example.lloader.myapplication.mvp.model.Film;
import com.example.lloader.myapplication.mvp.presenter.MainPresenter;
import com.example.lloader.myapplication.mvp.presenter.MainPresenterImpl;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements MainView {

    private SearchView mSearchView;
    private LinearLayout mRootView;
    private Toolbar mToolbar;
    private FilmAdapter mFilmAdapter;
    private MainPresenter mMainPresenter;
    private RecyclerView mFilmList;
    private ImageView mWarnImageView;
    private TextView mWarnTextView;
    private ProgressBar mProgressBar;
    private ProgressBar mHorizontalProgressBar;
    private ImageButton mUpdateButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainPresenter.detachView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* mSearchView.setQuery("", false); */
        mMainPresenter.attachView(this);
        mRootView.requestFocus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchView = findViewById(R.id.search_panel);
        mRootView = findViewById(R.id.root_layout);
        mToolbar = findViewById(R.id.tool_bar);
        mFilmList = findViewById(R.id.film_list);
        mWarnImageView = findViewById(R.id.warn_image);
        mWarnTextView = findViewById(R.id.warn_text);
        mProgressBar = findViewById(R.id.progress_bar_without_content);
        mHorizontalProgressBar = findViewById(R.id.horizontal_progress_bar);
        mUpdateButton = findViewById(R.id.update_button);
        mSwipeRefreshLayout = findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mMainPresenter.onSearch(mSearchView.getQuery().toString());
        });

        mMainPresenter = MainPresenterImpl.getInstance(getApplicationContext());

        mUpdateButton.setOnClickListener(
                clickEvent -> mMainPresenter.onSearch(mSearchView.getQuery().toString())
        );

        setSupportActionBar(mToolbar);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMainPresenter.onSearch(query);
                mRootView.requestFocus();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mFilmList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mMainPresenter.attachView(this);
    }

    @Override
    public void updateList() {
        mFilmAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNetworkError() {
        showErrorWithNoContent();
    }

    @Override
    public void setDataToList(List<Film> data) {
        mFilmAdapter = new FilmAdapter(data, this);
        mFilmList.setAdapter(mFilmAdapter);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }


    public void showProgressBar(){
        if (mFilmAdapter.getItemCount() == 0) {
            mFilmList.setVisibility(GONE);
            hideAllNotificationComponents();
            mHorizontalProgressBar.setVisibility(GONE);
            mProgressBar.setVisibility(VISIBLE);
        } else {
            mHorizontalProgressBar.setVisibility(VISIBLE);
        }
    }

    @Override
    public void hideProgressBars() {
        mProgressBar.setVisibility(View.GONE);
        mHorizontalProgressBar.setVisibility(GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void showErrorWithNoContent() {
        mSwipeRefreshLayout.setRefreshing(false);
        mFilmList.setVisibility(GONE);
        mProgressBar.setVisibility(GONE);
        mWarnImageView.setImageResource(R.drawable.ic_alert_triangle);
        mWarnImageView.setVisibility(VISIBLE);
        mWarnTextView.setText(R.string.request_error);
        mWarnTextView.setVisibility(VISIBLE);
        mUpdateButton.setVisibility(VISIBLE);
    }

    public void showNotFoundError(String query) {
        mSwipeRefreshLayout.setRefreshing(false);
        mHorizontalProgressBar.setVisibility(View.GONE);
        mProgressBar.setVisibility(GONE);
        mFilmList.setVisibility(GONE);
        mWarnImageView.setImageResource(R.drawable.ic_big_search);
        mWarnImageView.setVisibility(VISIBLE);
        if(query.length() > 50) {
            query = query.substring(0, 50);
            query += "...";
        }
        //TODO лучше бы конечно сделать это через string.xml
        mWarnTextView.setText("По запросу \"" + query + "\" ничего не найдено");
        mWarnTextView.setVisibility(VISIBLE);
    }

    public void hideAllNotificationComponents() {
        mWarnTextView.setVisibility(GONE);
        mWarnImageView.setVisibility(GONE);
        mUpdateButton.setVisibility(GONE);
    }

    public void showList() {
        mFilmList.setVisibility(VISIBLE);
    }

    @Override
    public void showNetworkErrorToast() {
        Snackbar.make(mRootView, R.string.network_error, Snackbar.LENGTH_LONG).show();
        mHorizontalProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }


}
