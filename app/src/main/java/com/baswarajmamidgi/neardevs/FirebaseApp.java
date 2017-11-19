package com.baswarajmamidgi.neardevs;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by baswarajmamidgi on 04/03/17.
 */

public class FirebaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
