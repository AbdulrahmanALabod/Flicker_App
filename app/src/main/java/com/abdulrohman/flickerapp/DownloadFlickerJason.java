package com.abdulrohman.flickerapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadFlickerJason extends AsyncTask<String, Void, String> {
    private static final String TAG = "DownloadFlickerJason";
    private DownloadStats downloadStats;
    private final IActiviteDownloadFlicker activity;

    interface IActiviteDownloadFlicker {
        void onDownloadComplete(String data, DownloadStats stats);
    }

    public DownloadFlickerJason(IActiviteDownloadFlicker callback) {
        this.downloadStats = DownloadStats.IDEL;
        this.activity = callback;
    }

    public void runInSameThread(String s) {
        Log.i(TAG, "runInSameThread: start ");
        activity.onDownloadComplete(doInBackground(s),downloadStats);
        Log.i(TAG, "runInSameThread: end ");
    }


    @Override
    protected String doInBackground(String... url) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        if (url == null) {
            downloadStats = DownloadStats.UNINTIALED;
            return null;
        }
        try {
            downloadStats = DownloadStats.PROCESSING;
            URL urllCon = new URL(url[0]);
            httpURLConnection = (HttpURLConnection) urllCon.openConnection();
            int response = httpURLConnection.getResponseCode();
            Log.i(TAG, "doInBackground: response is  " + response);
            httpURLConnection.connect();

            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            downloadStats = DownloadStats.OK;
            return stringBuilder.toString();
        } catch (IOException e) {
            Log.i(TAG, "doInBackground: " + e.getMessage());
        } catch (Exception e) {
            Log.i(TAG, "doInBackground: exception " + e.getMessage());
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.i(TAG, "doInBackground: " + e.getMessage());
                }
            }
        }
        downloadStats = DownloadStats.FAILD_OR_EMPTY;
        return null;

    }

    @Override
    protected void onPostExecute(String s) {
        Log.i(TAG, "onPostExecute: " + s);
        if (activity != null) {
            activity.onDownloadComplete(s, downloadStats);
        }
    }


}