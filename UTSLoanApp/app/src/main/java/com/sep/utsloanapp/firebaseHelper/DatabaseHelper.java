package com.sep.utsloanapp.firebaseHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sep.utsloanapp.models.User;

public class DatabaseHelper {

    public static final String KEY_DB_USER = "users";

    private DatabaseReference mReference;

    /**
     * Constructor that requires database reference, this reference will be used as a root in firebase
     * database processing
     * */
    public DatabaseHelper(DatabaseReference reference) {
        this.mReference = reference;
    }

    /**
     * Retrieve public profiles from the database, pass them into the OnGetDataListener then send to
     * ExploreFragment.
     * */
    public void retrieveData(final OnGetDataListener listener) {
        listener.onStart();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccessful(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }

    /**
     * Method that takes a User as a parameter then store it into the database in the following
     * directory - users/uid/..
     * */
    public void saveUser(User user) {
        try {
            String uid = user.getUid();
            mReference.child(KEY_DB_USER).child(uid).setValue(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Following are the Listeners
     * */
    public interface OnGetDataListener {
        //this is for callbacks
        void onStart();
        void onSuccessful(DataSnapshot dataSnapshot);
        void onFailed(DatabaseError databaseError);
    }
}
