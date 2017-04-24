package com.sample.stopwatch;

/**
 * Created by Hakeem on 4/23/17.
 */

public class Presenter implements Watch.WatchListener {
    public interface PresenterView {
        void updateTime(long millis);
    }

    private PresenterView mView;
    private Watch mWatch;
    private static Presenter mInstance;

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

    public void start() {

    }

    public void pause() {

    }

    public void reset() {

    }

    @Override
    public void onTimeUpdate(long millis) {
        if (mView != null) {
            mView.updateTime(millis);
        }
    }
}
