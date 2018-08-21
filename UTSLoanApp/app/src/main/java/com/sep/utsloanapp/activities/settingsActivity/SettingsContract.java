package com.sep.utsloanapp.activities.settingsActivity;

import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;

public interface SettingsContract {

    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{
        //logout the user
        void logout();


    }

}
