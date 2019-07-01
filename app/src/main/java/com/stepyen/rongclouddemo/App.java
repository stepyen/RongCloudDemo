package com.stepyen.rongclouddemo;

import android.app.Application;

import io.rong.imkit.RongIM;

/**
 * date：2019-07-01
 * author：stepyen
 * description：
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        RongIM.init(this, Constant.KEY_RONG_CLOUD);
    }
}
