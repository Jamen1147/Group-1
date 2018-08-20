package com.sep.utsloanapp.activities.utils;

public interface BaseView<T> {

    /**
     * setup the member presenter of that specific activity
     */
    void setPresenter(T presenter);

    /**
     * Means the user has no longer been logged in, so log out the user
     */
    void currentUserNull();
}
