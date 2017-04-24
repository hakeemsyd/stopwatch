package com.sample.stopwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Presenter.PresenterView {
    private TextView mTime;
    private Button mPlayPause;
    private Button mReset;
    private boolean mIsPlaying;
    private Presenter mPresenter;

    private final View.OnClickListener mPlayPauseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mIsPlaying) {
                mIsPlaying = false;
                mPlayPause.setText(R.string.btn_play);
                mPresenter.pause();
            } else {
                mIsPlaying = true;
                mPlayPause.setText(R.string.btn_pause);
                mPresenter.start();
            }
        }
    };

    private final View.OnClickListener mResetClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mIsPlaying = false;
            mPresenter.reset();
            mPlayPause.setText(R.string.btn_play);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTime = (TextView) findViewById(R.id.time);
        mPlayPause = (Button) findViewById(R.id.play);
        mPlayPause.setOnClickListener(mPlayPauseClickListener);
        mReset = (Button) findViewById(R.id.reset);
        mReset.setOnClickListener(mResetClickListener);
        mIsPlaying = false;
        mPresenter = Presenter.getInstance();
    }

    @Override
    protected void onPause() {
        mPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    public void updateTime(long millis) {
        mTime.setText(millisToStringTime(millis));
    }

    private String millisToStringTime(long millis) {
        return "";
    }
}
