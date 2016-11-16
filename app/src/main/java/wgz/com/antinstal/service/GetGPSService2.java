package wgz.com.antinstal.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wgz.com.antinstal.app;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by qwerr on 2016/8/2.
 */

public class GetGPSService2 extends Service {
    LocationManager locationManager;


    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                LogUtil.e("定位服务222启动！");
                gps();
                while (true){

                    try {
                        getgps();
                        Thread.sleep(1000*5*60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void gps() {
        //通过getSystemService接口获取LocationManager实例
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //实现监听器 LocationListener
        LocationListener locationlisten = new LocationListener() {

            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                // TODO Auto-generated method stub
                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
            }

            public void onProviderEnabled(String arg0) {
                // TODO Auto-generated method stub
                // Provider被enable时触发此函数，比如GPS被打开
            }

            public void onProviderDisabled(String arg0) {
                // TODO Auto-generated method stub
                // Provider被disable时触发此函数，比如GPS被关闭
            }

            //当坐标改变时触发此函数；如果Provider传进相同的坐标，它就不会被触发
            @Override
            public void onLocationChanged(Location arg0) {
                // TODO Auto-generated method stub
                if (arg0 != null) {
                    //Log.i("log", "Location changed : Lat: " + arg0.getLatitude() + " Lng: " + arg0.getLongitude());
                    //editText.setText(arg0.getLatitude() + " Lng: " + arg0.getLongitude() + "\n");
                    LogUtil.e("log", "Location changed : Lat: " + arg0.getLatitude() + " Lng: " + arg0.getLongitude());

                } else {
                   // Log.i("log", "Location changed : Lat: " + "NULL" + " Lng: " + "NULL");
                    LogUtil.e("log", "Location changed : Lat: " + "NULL" + " Lng: " + "NULL");
                    //editText.setText("NULL" + " Lng: " + "NULL" + "\n");
                }
            }
        };
// 注册监听器 locationListener
        //第 2 、 3个参数可以控制接收GPS消息的频度以节省电力。第 2个参数为毫秒， 表示调用 listener的周期，第 3个参数为米 ,表示位置移动指定距离后就调用 listener
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationlisten);


    }

    private void getgps() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location!=null) {
            String strLati = Double.toString(location.getLatitude());//weidu
            String strLong = Double.toString(location.getLongitude());//jingdu
            //LogUtil.e("strLati"+strLati);
            //LogUtil.e("strLong"+strLong);
            Call<String> call = app.apiService.updateGPS(getsp2(),strLong,strLati);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    LogUtil.e("result : " +response.body().toString());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    LogUtil.e("error  : " +t.toString());
                }
            });


            //显示地理位置数据
           /* System.out.println("---------地理位置信息---------");
            System.out.println("---------" + strLati + "/" + strLong + "---------");*/
            //editText.setText(strLati + "/" + strLong + "\n");
        }
        else{
            Log.i("log", "location==NULL");
            //editText.setText("location==NULL");
        }


    };
    private String getsp2() {
        SharedPreferences preferences = getSharedPreferences("autologin", Context.MODE_PRIVATE);
        String flag = preferences.getString("username", "????");
        return flag;
    }
}
