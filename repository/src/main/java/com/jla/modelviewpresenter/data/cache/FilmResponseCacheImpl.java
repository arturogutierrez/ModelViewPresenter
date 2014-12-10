package com.jla.modelviewpresenter.data.cache;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jla.modelviewpresenter.data.db.DbHelper;
import com.jla.modelviewpresenter.data.model.FilmResponse;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FilmResponseCacheImpl implements FilmResponseCache {

    private static final long CACHE_DURATION_MILLISECONDS = TimeUnit.MINUTES.toMillis(30);

    private Context context;

    public FilmResponseCacheImpl(Context context){
        this.context = context;
    }

    @Override
    public List<FilmResponse> getCache() {
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        Dao filmResponseDao;
        List<FilmResponse> filmResponses = null;
        try {
            filmResponseDao = dbHelper.getFilmResponseDao();
            filmResponses = filmResponseDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!cacheIsValid(filmResponses)) {
            cleanCache(filmResponses);
            filmResponses = null;
        }

        OpenHelperManager.releaseHelper();
        return filmResponses;
    }

    @Override
    public void saveInCache(List<FilmResponse> filmResponses) {
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        Dao filmResponseDao;
        try {
            filmResponseDao = dbHelper.getFilmResponseDao();
            for (FilmResponse filmResponse : filmResponses) {
                try {
                    filmResponseDao.create(filmResponse);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OpenHelperManager.releaseHelper();
        }
    }

    private boolean cacheIsValid(List<FilmResponse> filmResponses) {
        if (filmResponses == null || filmResponses.size() == 0) {
            return true;
        }

        Date updatedAt = filmResponses.get(0).getUpdatedAt();
        return Calendar.getInstance().getTimeInMillis() - updatedAt.getTime() < CACHE_DURATION_MILLISECONDS;
    }

    private void cleanCache(List<FilmResponse> filmResponses) {
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        Dao filmResponseDao;
        try {
            filmResponseDao = dbHelper.getFilmResponseDao();
            filmResponseDao.delete(filmResponses);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OpenHelperManager.releaseHelper();
        }
    }
}
