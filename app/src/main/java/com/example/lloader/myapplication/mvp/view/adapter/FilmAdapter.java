package com.example.lloader.myapplication.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lloader.myapplication.R;
import com.example.lloader.myapplication.application.Application;
import com.example.lloader.myapplication.mvp.model.Film;
import com.example.lloader.myapplication.mvp.presenter.MainPresenterImpl;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Alexander Garkavenko
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private List<Film> mData;
    private Context mContext;

    public FilmAdapter(List<Film> data, Context context) {
        mContext = context;
        mData = data;
    }


    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilmViewHolder(LayoutInflater.from(mContext), parent);
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {
            holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mPoster;
        TextView mName;
        TextView mDescription;
        TextView mDate;
        ImageView mHeart;

        public FilmViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.film_list_item, parent, false));
            itemView.setOnClickListener(this);
            mPoster = itemView.findViewById(R.id.film_poster);
            mName = itemView.findViewById(R.id.film_name);
            mDescription = itemView.findViewById(R.id.film_describe);
            mDate = itemView.findViewById(R.id.film_date);
            mHeart = itemView.findViewById(R.id.heart_btn);
        }

        public void bind(Film film) {
            mName.setText(film.getTitle());
            mDescription.setText(film.getOverview());
            mDate.setText(film.getRelease_date());
            if(mName.getLineCount() == 2) {
                mDescription.setMaxLines(3);
            }
            Picasso.with(itemView.getContext())
                    .load(Application.BASE_URL_TO_IMAGES + film.getPoster_path())
                    .into(mPoster);
            if(film.isFavorite()) {
                mHeart.setImageResource(R.drawable.ic_heart_fill);
            } else {
                mHeart.setImageResource(R.drawable.ic_heart);
            }
            mHeart.setOnClickListener(click -> {
                if (film.isFavorite()) {
                    mHeart.setImageResource(R.drawable.ic_heart);
                    MainPresenterImpl.getInstance(itemView.getContext().getApplicationContext())
                            .deleteFromFavorites(film);
                } else {
                    mHeart.setImageResource(R.drawable.ic_heart_fill);
                    MainPresenterImpl.getInstance(itemView.getContext().getApplicationContext())
                            .addToFavorites(film);
                }
            });
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(itemView.getContext(), mName.getText(), Toast.LENGTH_LONG).show();
        }
    }
}
