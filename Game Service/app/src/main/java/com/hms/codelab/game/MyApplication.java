package com.hms.codelab.game;

import android.app.Application;

import com.huawei.hms.api.HuaweiMobileServicesUtil;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HuaweiMobileServicesUtil.setApplication(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
