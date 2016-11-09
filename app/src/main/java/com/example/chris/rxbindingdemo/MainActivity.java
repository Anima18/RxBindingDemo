package com.example.chris.rxbindingdemo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

public class MainActivity extends RxAppCompatActivity {

    private static final String TAG = "Anima";
    private Button rxbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rxbt = (Button) findViewById(R.id.mainAct_rx_bt);

        Observable
                .interval(1, TimeUnit.SECONDS)
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.i(TAG, "Unsubscribing subscription from onCreate()");
                    }
                })
                .compose(this.<Long>bindUntilEvent(ActivityEvent.PAUSE))
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long num) {
                        Log.i(TAG, "Started in onCreate(), running until onPause(): " + num);
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }
}
