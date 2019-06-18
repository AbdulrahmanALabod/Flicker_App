package com.abdulrohman.flickerapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

enum DownloadStats {IDEL, PROCESSING, OK, FAILD_OR_EMPTY, UNINTIALED}

public class MainActivity extends BaseActivity implements GetFlickerJsonData.IOnDataAvailable, RecycleItemClickListen.IOnRecyclyerClickListener {
    private static final String TAG = "MainActivity";
    GetFlickerRecyclerView flickerRecyclerView;
    private List<Photo> photoList;

    @Override
    public void onDataAvaliabel(List<Photo> data, DownloadStats state) {
        if (state == DownloadStats.OK) {
            Log.i(TAG, "onDataAvaliabel start " + data);
            flickerRecyclerView.loadNewData(data);
        } else {
            Log.i(TAG, "onDataAvaliabel: " + DownloadStats.FAILD_OR_EMPTY);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: start ");
        appBar(false);
//        DownloadFlickerJason flickerJason = new DownloadFlickerJason(this);
//        flickerJason.execute("https://api.flickr.com/services/feeds/photos_public.gne?tags=android,sdk&tagmode=any&format=json&nojsoncallback=1");
        RecyclerView recyclerView = findViewById(R.id.recView);
        flickerRecyclerView = new GetFlickerRecyclerView(getApplicationContext(), new ArrayList<Photo>());
        recyclerView.setAdapter(flickerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.addOnItemTouchListener(new RecycleItemClickListen(this, this, recyclerView));
        Log.i(TAG, "onResume is shown " + recyclerView.isShown());


        Log.d(TAG, "onCreate: end ");

    }


    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: strart");
        super.onResume();
        String strValueSearch = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(FLICKER_QUERY, "");
        if (strValueSearch.length() > 0) {
            GetFlickerJsonData getFlickerJsonData = new GetFlickerJsonData(this, "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);
            getFlickerJsonData.execute(strValueSearch);
        }
        Log.d(TAG, "onResume: end ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.app_bar_main_search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i(TAG, "onItemClick: start ");
        Toast.makeText(MainActivity.this, "onItemClick is " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.i(TAG, "onItemLongClick: start");
        Toast.makeText(MainActivity.this, "onItemLongClick is : " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.putExtra(PHOTO_TRANSFER, flickerRecyclerView.getPhoto(position));
        startActivity(intent);
    }
}

