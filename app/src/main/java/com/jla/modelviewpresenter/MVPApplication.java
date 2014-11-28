package com.jla.modelviewpresenter;

import android.app.Application;

import com.jla.modelviewpresenter.di.RootModule;

import java.util.List;

import dagger.ObjectGraph;

public class MVPApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeDependencyInjector();
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    public ObjectGraph plus(List<Object> modules) {
        if (modules == null) {
            throw new IllegalArgumentException("You can't plus a null module, review your getModules() implementation");
        }
        return objectGraph.plus(modules.toArray());
    }

    private void initializeDependencyInjector() {
        objectGraph = ObjectGraph.create(new RootModule(this));
        objectGraph.inject(this);
        objectGraph.injectStatics();
    }
}
