package com.abdulrohman.flickerapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
     static   final String PHOTO_TRANSFER="photoTransfer";
     static final String  FLICKER_QUERY="flickerQuery";
    Toolbar toolbar ;
    public void appBar(boolean exsist) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
           toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(exsist);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
