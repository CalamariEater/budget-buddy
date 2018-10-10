package com.budget_buddy;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

class BBUser {
    // The singleton class object used for referencing throughout the program.
    private static final BBUser ourInstance = new BBUser();
    // Used for signing in and other functions that need to authenticate a user's credentials.
    private FirebaseAuth authentication;
    // This is the user in the database, used directly to make Firebase function calls on a Firebase user.
    private FirebaseUser user;
    // The user's name as entered in the database.
    private String userName;
    // The user's unique ID in the database. ~ if we need it
    private String uid;
    // Current level of the user (NYI)
    public int budgetLevel;
    // Current score of the user (NYI)
    public int budgetScore;

    static BBUser GetInstance() {
        return ourInstance;
    }
    FirebaseUser GetUser() {return user;}
    String GetUserName() {return userName;}

    public Task SignIn(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        return authentication.signInWithCredential(credential);
    }

    public void SignOut() {
        authentication.signOut();
    }

    public void Initialize() {
        user = authentication.getCurrentUser();
        if(user != null) {
            userName = user.getDisplayName();
            // TODO: Add other initialization her as appropriate
        }
    }

    public boolean IsLoggedIn() {
        user = authentication.getInstance().getCurrentUser();
        if(user != null) {
            return true;
        }
        else {
            return false;
        }
    }

    private BBUser() {
        authentication = FirebaseAuth.getInstance();
        userName = "";
    }


    public void createUser(String fName, String lName, double income, int points ){
        // Get database reference in Users
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Users").push();

        // Place new users info into map
        Map<String, String> newUser = new HashMap<>();
        newUser.put("fname", fName);
        newUser.put("lname", lName);
        newUser.put("income", Double.toString(income));
        newUser.put("points", Integer.toString(points));

        // Save reference point to get UID of new user
        DatabaseReference newPushRef =  dbRef.push();
        // Set new users data to database
        dbRef.setValue(newUser);
        // Save UID of new user ~ if we need it
        uid = newPushRef.getKey();
    }

    // messing with write
//    public void write(Map<String, String> map){
//        switch (map.)
//    }
}