package com.jla.modelviewpresenter.data.db;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jla.modelviewpresenter.data.model.FilmResponse;

import java.sql.SQLException;

public class DbHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "modelviewpresenter.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<FilmResponse, Integer> filmResponseDao;

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, FilmResponse.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    }

    public Dao<FilmResponse, Integer> getFilmResponseDao() throws SQLException {
        if (filmResponseDao == null) {
            filmResponseDao = getDao(FilmResponse.class);
        }
        return filmResponseDao;
    }
}
