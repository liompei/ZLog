package com.liompei.zlog;

import android.app.Application;

/**
 * Created by BLM on 2016/8/11.
 */
public class MyApplication extends Application {

    public final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Z.initLog(TAG,true);
    }
}
