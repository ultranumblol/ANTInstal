package wgz.com.antinstal;

import android.app.Application;
import android.content.Intent;

import com.baidu.mapapi.SDKInitializer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import wgz.com.antinstal.service.ApiService;
import wgz.com.antinstal.service.GetGPSService;
import wgz.com.antinstal.service.GetGPSService2;
import wgz.datatom.com.utillibrary.util.LogUtil;
import wgz.datatom.com.utillibrary.util.ToastUtil;

/**
 * Created by wgz on 2016/7/26.
 */

public class app extends Application {
    private static app mApp;
    public static final String BASE_URL = "http://wuliu.chinaant.com/";
    public static  ApiService apiService;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        SDKInitializer.initialize(getApplicationContext());
        ToastUtil.isShow =true;
        LogUtil.isDebug=true;

        //配置程序异常退出处理
        //Thread.setDefaultUncaughtExceptionHandler(new LocalFileHandler(this));

        //startService(new Intent(getApplicationContext(), GetGPSService.class));
        //startService(new Intent(getApplicationContext(), GetGPSService2.class));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())

                .build();

        apiService = retrofit.create(ApiService.class);


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
