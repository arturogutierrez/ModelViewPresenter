package com.jla.modelviewpresenter.data.cache;

import com.jla.modelviewpresenter.data.model.ConfigurationResponse;

import java.util.List;

public interface ConfigurationResponseCache {

    public ConfigurationResponse getCache();
    public void saveInCache(ConfigurationResponse configurationResponse);
}
