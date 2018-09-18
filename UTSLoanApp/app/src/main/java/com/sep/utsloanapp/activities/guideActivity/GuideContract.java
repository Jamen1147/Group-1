package com.sep.utsloanapp.activities.guideActivity;

import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;

public interface GuideContract {

    interface View extends BaseView<Presenter>{
        void onGetAvailableSuccessful(int availabilityCode);
    }

    interface Presenter extends BasePresenter{
        void logout();
        void checkAvailable();
    }

}
