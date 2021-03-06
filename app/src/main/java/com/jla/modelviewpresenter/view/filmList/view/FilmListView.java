package com.jla.modelviewpresenter.view.filmList.view;

import com.jla.modelviewpresenter.domain.model.Film;

import java.util.List;

public interface FilmListView {

    public void showProgress();

    public void hideProgress();

    public void setFilms(List<Film> films);
}
