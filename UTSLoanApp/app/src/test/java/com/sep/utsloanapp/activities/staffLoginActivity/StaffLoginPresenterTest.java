//package com.sep.utsloanapp.activities.staffLoginActivity;
//
//import android.content.Context;
//
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.sep.utsloanapp.firebaseHelper.AuthHelper;
//import com.sep.utsloanapp.firebaseHelper.DatabaseHelper;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class StaffLoginPresenterTest {
//
//    private StaffLoginContract.Presenter mPresenter;
//    private Context mContext;
//    private StaffLoginContract.View mView;
//
//
//    class MockedView implements StaffLoginContract.View{
//        String text;
//
//        @Override
//        public void loginInputInvalid(String errorMessage) {
//            text = errorMessage;
//        }
//
//        @Override
//        public void onLoginStart() {
//
//        }
//
//        @Override
//        public void onLoginSuccessful() {
//
//        }
//
//        @Override
//        public void onLoginFailed() {
//
//        }
//
//        @Override
//        public void onGetDataSuccessfulUserStudent() {
//
//        }
//
//        @Override
//        public void onGetDataSuccessfulUserStaff() {
//
//        }
//
//        @Override
//        public void onGetDataFailed(DatabaseError databaseError) {
//
//        }
//
//        @Override
//        public void onGetFirstTimeUser() {
//
//        }
//
//        @Override
//        public void setPresenter(StaffLoginContract.Presenter presenter) {
//
//        }
//
//        @Override
//        public void currentUserNull() {
//
//        }
//    }
//
//    @Before
//    public void setUp() throws Exception{
//        mView = new MockedView();
//        mPresenter = new StaffLoginPresenter(mContext, mView);
//    }
//
//    @Test
//    public void doLogin() throws Exception{
//        //Arrange
//        String staffId = "1234567"; //should be 8 digits
//        String password = "12121212"; //should between 6 - 20
//        String expectedErrMsg = "ID must be in 8 digits";
//        //Act
//        mPresenter.doLogin(staffId, password);
//        //Assert
//        assertEquals(expectedErrMsg, ((MockedView)mView).text);
//    }
//
//    @Test
//    public void doSaveUser() throws Exception{
//    }
//
//    @Test
//    public void checkType() throws Exception{
//    }
//}