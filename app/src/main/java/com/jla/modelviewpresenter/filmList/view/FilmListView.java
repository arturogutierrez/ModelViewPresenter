package com.jla.modelviewpresenter.filmList.view;

import com.jla.modelviewpresenter.domain.Film;

import java.util.List;

public interface FilmListView {

    public void showProgress();

    public void hideProgress();

    public void setFilms(List<Film> films);
}