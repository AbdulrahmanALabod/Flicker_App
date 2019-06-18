package com.abdulrohman.flickerapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class RecycleItemClickListen extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecycleItemClickListen";
    interface IOnRecyclyerClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view , int position);
    }
    private final IOnRecyclyerClickListener mIOnRecyclyerClickListener;
    private  final GestureDetectorCompat mGestureDetector;
    public RecycleItemClickListen(Context context, final IOnRecyclyerClickListener mIOnRecyclyerClickListener , final RecyclerView recyclerView) {
        this.mIOnRecyclyerClickListener = mIOnRecyclyerClickListener;
        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i(TAG, "onSingleTapUp: start ");
              View view=  recyclerView.findChildViewUnder(e.getX(),e.getY());
               if(view != null &&mIOnRecyclyerClickListener !=null ){
                   Log.i(TAG, "onSingleTapUp:  start");
                   mIOnRecyclyerClickListener.onItemClick(view ,recyclerView.getChildAdapterPosition(view));
               }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View view=  recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(view != null &&mIOnRecyclyerClickListener !=null ){
                    Log.i(TAG, "onLongPress: start");
                    mIOnRecyclyerClickListener.onItemLongClick(view ,recyclerView.getChildAdapterPosition(view));
                }
                Log.i(TAG, "onLongPress: end ");

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        Log.i(TAG, "onInterceptTouchEvent: ");
        if(rv != null){
         boolean result=   mGestureDetector.onTouchEvent(e);
            Log.i(TAG, "onInterceptTouchEvent: result is  "+result);
            return true;
        }else {
            Log.i(TAG, "onInterceptTouchEvent:  result is false");
            return false;
        }

    }
}
