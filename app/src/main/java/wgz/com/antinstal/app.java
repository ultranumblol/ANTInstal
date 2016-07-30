package wgz.com.antinstal;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import wgz.com.antinstal.exception.LocalFileHandler;
import wgz.datatom.com.utillibrary.util.LogUtil;
import wgz.datatom.com.utillibrary.util.ToastUtil;

/**
 * Created by wgz on 2016/7/26.
 */

public class app extends Application {
    private static app mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        ToastUtil.isShow =true;

        LogUtil.isDebug=true;

        //配置程序异常退出处理
        Thread.setDefaultUncaughtExceptionHandler(new LocalFileHandler(this));


    }
    public static OkHttpClient defaultOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        return client;
    }
    public static app getApp(){
        return mApp;
    }
}
