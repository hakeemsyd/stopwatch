package com.sample.stopwatch;

import android.os.Handler;
import android.widget.TextView;

/**
 * Created by Hakeem on 4/23/17.
 */

public class Presenter implements Watch.WatchListener {
    public interface PresenterView {
        TextView getTextView();
    }

    private PresenterView mView;
    private Watch mWatch;
    private static Presenter mInstance;
    private Thread mWatchThread;

    public static Presenter getInstance() {
        if (mInstance == null) {
            mInstance = new Presenter();
        }
        return mInstance;
    }

    private Presenter() {
        mWatch = Watch.getInstance();
        mWatch.addListener(this);
    }


    public void onPause() {
        mView = null;
    }

    public void attachView(PresenterView view) {
        mView = view;
    }

    public void pause(boolean pause) {
        if (mWatchThread == null) {
            mWatchThread = new Thread(mWatch);
            mWatchThread.start();
        }
        mWatch.pause(pause);
    }

    public void reset() {
        mWatch.stop();
        mWatchThread = null;
    }

    @Override
    public void onTimeUpdate(final long millis) {
        final TextView text = mView.getTextView();
        if (mView != null) {
            text.post(new Runnable() {
                @Override
                public void run() {
                    text.setText(millisToStringTime(millis));
                }
            });
        }
    }

    private String millisToStringTime(long millis) {
        long hours = millis / (1000 * 60 * 60);
        millis = millis - (hours * 1000 * 60 * 60);
        long minutes = millis / (1000 * 60);
        millis = millis - (minutes * 1000 * 60);
        long seconds = millis / 1000;
        millis = millis - seconds * 10000;
        return hours + ":" + minutes + ":" + seconds + ":" + millis;
    }
}
