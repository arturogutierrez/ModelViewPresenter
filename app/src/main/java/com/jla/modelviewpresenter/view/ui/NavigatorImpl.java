package com.jla.modelviewpresenter.view.ui;

import android.app.Activity;

public class NavigatorImpl implements Navigator {

    private Activity activityContext;

    public NavigatorImpl(Activity activity) {
        activityContext = activity;
    }

    @Override
    public void navigateToDetail() {

    }
}
