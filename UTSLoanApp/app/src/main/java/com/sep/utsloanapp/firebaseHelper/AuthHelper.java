package com.sep.utsloanapp.firebaseHelper;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthHelper {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public AuthHelper(FirebaseAuth firebaseAuth) {
        mFirebaseAuth = firebaseAuth;
    }

    /**
     * Gets the user logged in with email and password
     * */
    public void loginUser(String email, String password, final StateListener listener){
        listener.onStart();
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    listener.onSuccessful();
                }else {
                    listener.onFailed();
                }
            }
        });
    }

    /**
     * Signs out the user
     * */
    public void logOutUser(){
        mFirebaseAuth.signOut();
    }

    /**
     * Checks if the user state is changed
     * */
    public void listenAuthState(final AuthStateListener listener){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    listener.onAuthStateChanged();
                }
            }
        };
    }

    /**
     * Add user state listener
     * */
    public void addAuthListener(){
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    /**
     * Remove user state listener
     * */
    public void removeAuthListener(){
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    /**
     * Returns the current Uid
     * */
    public String getUid(){
        return mFirebaseAuth.getCurrentUser().getUid();
    }

    /**
     * Returns the current user
     * */
    public FirebaseUser getCurrentUser(){
        return mFirebaseAuth.getCurrentUser();
    }

    public interface StateListener{
        void onStart();
        void onSuccessful();
        void onFailed();
    }

    /**
     * Listener for auth state
     * */
    public interface AuthStateListener{
        void onAuthStateChanged();
    }
}
