package com.jla.modelviewpresenter.data.cache;

import com.jla.modelviewpresenter.data.model.FilmResponse;

import java.util.List;

public interface FilmResponseCache {

    public List<FilmResponse> getCache();
    public void saveInCache(List<FilmResponse> filmResponses);
}
