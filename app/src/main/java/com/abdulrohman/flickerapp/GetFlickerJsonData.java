package com.abdulrohman.flickerapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class GetFlickerJsonData extends AsyncTask<String, Void, List<Photo>> implements DownloadFlickerJason.IActiviteDownloadFlicker {
    private static final String TAG = "GetFlickerJsonData";
    List<Photo> photos = null;
    private String baseUrl;
    private String language;
    private boolean matchAll;
    private  boolean runInSameThread=false;
    private final IOnDataAvailable callback;

    interface IOnDataAvailable {
        void onDataAvaliabel(List<Photo> data, DownloadStats stats);
    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: strat ");
        DownloadFlickerJason flickerJason = new DownloadFlickerJason(this);
        String destinionUrl = creatUri(params[0],language, matchAll);
        flickerJason.runInSameThread(destinionUrl);
        //error blew here
        return  photos;
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        Log.d(TAG, "onPostExecute: start ");
        if (callback != null) {
            callback.onDataAvaliabel(photos, DownloadStats.OK);
        }
        Log.d(TAG, "onPostExecute: end ");

    }

    public String creatUri(String search, String lang, boolean match) {
        Log.i(TAG, "searchCrateria: start");
        return Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("tags", search)
                .appendQueryParameter("tagmode", match ? "ALL" : "ANY")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();
    }

    void excuteSameThread(String search) {
        Log.d(TAG, "excuteSameThread: start");
        String url = creatUri(search, language, matchAll);
        runInSameThread=true;
        DownloadFlickerJason flickerJason = new DownloadFlickerJason(this);
        flickerJason.execute(url);
        Log.d(TAG, "excuteSameThread: end");
    }

    public GetFlickerJsonData(IOnDataAvailable callback, String baseUrl, String language, boolean matchAll) {
        this.baseUrl = baseUrl;
        this.language = language;
        this.matchAll = matchAll;
        this.callback = callback;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStats stats) {
        if (stats == DownloadStats.OK) {
            photos = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    String title = ob.getString("title");
                    String author = ob.getString("author");
                    String author_id = ob.getString("author_id");
                    String tag = ob.getString("tags");
                    JSONObject media = ob.getJSONObject("media");
                    String photoUrl = media.getString("m");
                    String link = photoUrl.replaceFirst("_m.", "_b.");
                    Photo photo = new Photo(title, link, photoUrl, tag, author, author_id);
                    photos.add(photo);
                    Log.d(TAG, "onDownloadComplete: " + photo.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "onDownloadComplete: " + e.getMessage());
                stats = DownloadStats.FAILD_OR_EMPTY;
            }
        }
        if (runInSameThread &&callback != null) {
            callback.onDataAvaliabel(photos, stats);
        }
    }
}
