package com.sep.utsloanapp.firebaseHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sep.utsloanapp.models.Staff;
import com.sep.utsloanapp.models.Student;
import com.sep.utsloanapp.models.User;

public class DatabaseHelper {

    public static final String KEY_DB_USER = "users";
    public static final String KEY_DB_STAFF = "staff";
    public static final String KEY_DB_STUDENT = "students";

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
    public void retrieveUserData(final OnGetDataListener listener) {
        listener.onStart();
        mReference.child(KEY_DB_USER).addValueEventListener(new ValueEventListener() {
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
    public void saveObject(User user) {
        try {
            String uid = user.getUid();
            mReference.child(KEY_DB_USER).child(uid).setValue(user);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that takes a User as a parameter then store it into the database in the following
     * directory - users/uid/..
     * */
    public void saveObject(Staff staff) {
        try {
            String uid = staff.getUid();
            mReference.child(KEY_DB_STAFF).child(uid).setValue(staff);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that takes a User as a parameter then store it into the database in the following
     * directory - users/uid/..
     * */
    public void saveObject(Student student) {
        try {
            String uid = student.getUid();
            mReference.child(KEY_DB_STUDENT).child(uid).setValue(student);
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
