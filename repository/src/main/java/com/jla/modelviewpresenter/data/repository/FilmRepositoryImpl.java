package com.jla.modelviewpresenter.data.repository;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jla.modelviewpresenter.data.db.DbHelper;
import com.jla.modelviewpresenter.data.model.ConfigurationResponse;
import com.jla.modelviewpresenter.data.model.FilmResponse;
import com.jla.modelviewpresenter.data.net.TheMovieDbAdapter;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FilmRepositoryImpl implements FilmRepository {

    private static final String API_KEY = "20ffea664862269a108e69164352dcd8";
    private static final long CACHE_DURATION_MILLISECONDS = TimeUnit.MINUTES.toMillis(30);
    private Context context;

    public FilmRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public ConfigurationResponse getConfiguration() {
        return new TheMovieDbAdapter().create().getConfiguration(API_KEY);
    }

    @Override
    public List<FilmResponse> getPopularFilms() {
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        Dao filmResponseDao;
        List<FilmResponse> filmResponses = null;
        try {
            filmResponseDao = dbHelper.getFilmResponseDao();
            filmResponses = filmResponseDao.queryForAll();

            if (cacheIsNotValid(filmResponses)) {
                cleanCache(filmResponses, filmResponseDao);
                filmResponses = new TheMovieDbAdapter().create().getPopularMovies(API_KEY, 1).getPopularFilms();
                filmResponses = setCurrentDate(filmResponses);
                saveFilmResponsesInDataBase(filmResponses, filmResponseDao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OpenHelperManager.releaseHelper();
        return filmResponses;
    }

    private boolean cacheIsNotValid(List<FilmResponse> filmResponses) {
        if (filmResponses.size() == 0) {
            return true;
        }
        Date updatedAt = filmResponses.get(0).getUpdatedAt();
        return Calendar.getInstance().getTimeInMillis() - updatedAt.getTime() >= CACHE_DURATION_MILLISECONDS;
    }

    private void cleanCache(List<FilmResponse> filmResponses, Dao filmResponseDao) {
        try {
            filmResponseDao.delete(filmResponses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<FilmResponse> setCurrentDate(List<FilmResponse> filmResponses) {
        for (FilmResponse filmResponse : filmResponses) {
            filmResponse.setUpdatedAt(new Date());
        }
        return filmResponses;
    }

    private void saveFilmResponsesInDataBase(List<FilmResponse> filmResponses, Dao filmResponseDao) {
        for (FilmResponse filmResponse : filmResponses) {
            try {
                filmResponseDao.create(filmResponse);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
