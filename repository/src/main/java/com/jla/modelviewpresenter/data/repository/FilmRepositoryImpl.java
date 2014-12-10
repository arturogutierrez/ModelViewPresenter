package com.jla.modelviewpresenter.data.repository;

import com.jla.modelviewpresenter.data.cache.ConfigurationResponseCache;
import com.jla.modelviewpresenter.data.cache.FilmResponseCache;
import com.jla.modelviewpresenter.data.model.ConfigurationResponse;
import com.jla.modelviewpresenter.data.model.FilmResponse;
import com.jla.modelviewpresenter.data.net.TheMovieDbAdapter;

import java.util.Date;
import java.util.List;

public class FilmRepositoryImpl implements FilmRepository {

    private static final String API_KEY = "20ffea664862269a108e69164352dcd8";

    private FilmResponseCache filmResponseCache;
    private ConfigurationResponseCache configurationResponseCache;

    public FilmRepositoryImpl(FilmResponseCache filmResponseCache, ConfigurationResponseCache configurationResponseCache) {
        this.filmResponseCache = filmResponseCache;
        this.configurationResponseCache = configurationResponseCache;
    }

    @Override
    public ConfigurationResponse getConfiguration() {
        ConfigurationResponse configurationResponse = configurationResponseCache.getCache();
        if (configurationResponse != null){
            return configurationResponse;
        }else{
            configurationResponse = new TheMovieDbAdapter().create().getConfiguration(API_KEY);
            configurationResponse.setUpdatedAt(new Date());
            configurationResponseCache.saveInCache(configurationResponse);
            return configurationResponse;
        }
    }

    @Override
    public List<FilmResponse> getPopularFilms() {
        List<FilmResponse> filmResponses = filmResponseCache.getCache();

        if (filmResponses != null) {
            return filmResponses;
        } else {
            filmResponses = new TheMovieDbAdapter().create().getPopularMovies(API_KEY, 1).getPopularFilms();
            filmResponses = setCurrentDate(filmResponses);
            filmResponseCache.saveInCache(filmResponses);
            return filmResponses;
        }
    }

    private List<FilmResponse> setCurrentDate(List<FilmResponse> filmResponses) {
        for (FilmResponse filmResponse : filmResponses) {
            filmResponse.setUpdatedAt(new Date());
        }
        return filmResponses;
    }
}
