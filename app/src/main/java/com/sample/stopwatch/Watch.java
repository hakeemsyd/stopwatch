package com.sample.stopwatch;

import android.util.Log;

/**
 * Created by Hakeem on 4/23/17.
 */

public class Watch implements Runnable{

     interface WatchListener{
         void onTimeUpdate(long millis);
    }

    private long mCurrentTime;
    private long mStartTime;
    private boolean mPaused;
    private boolean mStopped;
    public WatchListener mListener;
    private static Watch mInstance;

    private Watch(){
    }

    public static Watch getInstance(){
        if(mInstance == null){
            mInstance = new Watch();
        }
        return mInstance;
    }

    public void addListener(WatchListener listener){
        if(mListener != null){
            mListener = null;
        }
        mListener = listener;
    }

    public void pause(boolean paused){
        mPaused = paused;
        if(mStopped && !paused){
            mStopped = false;
            mPaused = false;
        }
    }

    public void stop(){
        mStopped = true;
        mStartTime = 0;
        mCurrentTime = 0;
    }

    @Override
    public void run() {
        mStartTime = System.currentTimeMillis();
        Watch.this.onUpdate(0);
        long millis = 0 ;
        while (true) {
            try {
                Thread.sleep(1);
                if(mStopped){
                    Watch.this.onUpdate(0);
                    break;
                }

                long now = System.currentTimeMillis();
                if (!mPaused) {
                    mCurrentTime = millis + (now - mStartTime);
                } else {
                    millis = mCurrentTime;
                    mStartTime = System.currentTimeMillis();
                }

                Watch.this.onUpdate(mCurrentTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                onUpdate(0);
            }
        }
    }

    public void onUpdate(long millis){
        if(mListener != null){
            //Log.i("", "OnUpdate: " + millis);
            mListener.onTimeUpdate(millis);
        }
    }
}
