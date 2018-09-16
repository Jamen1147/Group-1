package com.sep.utsloanapp.activities.mainActivity.studentFragment;

import com.sep.utsloanapp.activities.utils.BasePresenter;
import com.sep.utsloanapp.activities.utils.BaseView;

public interface StudentContract {

    interface View extends BaseView<Presenter>{
        void onGetAvailableSuccessful(int available);
    }

    interface Presenter extends BasePresenter{
        void checkAvailable();
    }

}
