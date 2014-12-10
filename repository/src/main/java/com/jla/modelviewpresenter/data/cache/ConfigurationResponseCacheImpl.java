package com.jla.modelviewpresenter.data.cache;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jla.modelviewpresenter.data.db.DbHelper;
import com.jla.modelviewpresenter.data.model.ConfigurationResponse;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConfigurationResponseCacheImpl implements ConfigurationResponseCache {

    private static final long CACHE_DURATION_MILLISECONDS = TimeUnit.DAYS.toMillis(1);

    private Context context;

    public ConfigurationResponseCacheImpl(Context context) {
        this.context = context;
    }

    @Override
    public ConfigurationResponse getCache() {
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        Dao configurationResponseDao;
        ConfigurationResponse configurationResponse = null;
        try {
            configurationResponseDao = dbHelper.getConfigurationResponseDao();
            configurationResponse = (ConfigurationResponse) configurationResponseDao.queryForAll().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!cacheIsValid(configurationResponse)) {
            cleanCache(configurationResponse);
            configurationResponse = null;
        }

        OpenHelperManager.releaseHelper();
        return configurationResponse;
    }

    @Override
    public void saveInCache(ConfigurationResponse configurationResponse) {
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        Dao configurationResponseDao;
        try {
            configurationResponseDao = dbHelper.getConfigurationResponseDao();
            configurationResponseDao.create(configurationResponse);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OpenHelperManager.releaseHelper();
        }
    }

    private boolean cacheIsValid(ConfigurationResponse configurationResponse) {
        if (configurationResponse == null) {
            return true;
        }

        Date updatedAt = configurationResponse.getUpdatedAt();
        return Calendar.getInstance().getTimeInMillis() - updatedAt.getTime() < CACHE_DURATION_MILLISECONDS;
    }

    private void cleanCache(ConfigurationResponse configurationResponse) {
        DbHelper dbHelper = OpenHelperManager.getHelper(context, DbHelper.class);
        Dao configurationResponseDao;
        try {
            configurationResponseDao = dbHelper.getConfigurationResponseDao();
            configurationResponseDao.delete(configurationResponse);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            OpenHelperManager.releaseHelper();
        }
    }
}
