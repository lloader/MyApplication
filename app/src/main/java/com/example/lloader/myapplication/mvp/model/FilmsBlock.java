package com.example.lloader.myapplication.mvp.model;

import java.util.List;

public class FilmsBlock {
    //TODO нужно изменить названия полей и добавить аннотации для десериализации, но нет времени, простите
    private int page;
    private int total_pages;
    private List<Film> results;
    private int total_results;

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return this.total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Film> getResults() {
        return this.results;
    }

    public void setResults(List<Film> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return this.total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
