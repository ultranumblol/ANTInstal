package wgz.com.antinstal.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import org.dom4j.DocumentException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wgz.com.antinstal.HomeActivity;
import wgz.com.antinstal.MainActivity;
import wgz.com.antinstal.MsgActivity;
import wgz.com.antinstal.R;
import wgz.com.antinstal.adapter.MsgFmtAdapter;
import wgz.com.antinstal.app;
import wgz.com.antinstal.util.OnDataFinishedListener;
import wgz.com.antinstal.util.SignMaker;
import wgz.com.antinstal.view.RefreshableView;
import wgz.com.antinstal.xmlpraser.InputStreamCallBack;
import wgz.com.antinstal.xmlpraser.ParserWorkerXml;
import wgz.com.antinstal.xmlpraser.PraserXml;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by qwerr on 2016/7/31.
 */

public class MsgFragment extends Fragment {

    RefreshableView refreshableView;
    private ListView msglv;
    private List<Map<String, Object>> listDATE = new ArrayList<Map<String,Object>>();
    private List<Map<String, Object>> infolist = new ArrayList<Map<String,Object>>();
    private InputStream inputStream;
    private MsgReceiver receiver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.msgfragment, null);
        initview(view);
        initData();

        //注册广播
        receiver = new MsgReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("service.MsgService");
        getActivity().registerReceiver(receiver, filter);
        LogUtil.e("广播注册成功！");


        return view;
    }

    private void initData() {

        SignMaker signMaker = new SignMaker();
        final String username = getsp2();
        final String sign = signMaker.getsign(username,0);

        LogUtil.e("username:"+username+"sign:"+sign);



        String sign2 = signMaker.getsign("type=get","username="+getsp2());
        LogUtil.e("sign2 : "+sign2);
        Call<ResponseBody> call2  = app.apiService.getMEssageInfo("get",getsp2(),sign2);
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                PraserXml px = new PraserXml();
                try {

                    infolist =   px.prase(response.body().byteStream());
                    LogUtil.e("infolist :" +infolist);
                    Call<ResponseBody> call0 = app.apiService.getWorkXML(username,"0",sign);
                    call0.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            inputStream=response.body().byteStream();
                            ParserWorkerXml pw = new ParserWorkerXml(inputStream);
                            pw.execute();
                            pw.setOnDataFinishedListener(new OnDataFinishedListener() {
                                @Override
                                public void onDataSuccessfully(Object data) {
                                    List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
                                    list1 = (List<Map<String, Object>>) data;
                                    Log.i("xml","list1==="+list1.toString());
                                    msglv.setAdapter(new MsgFmtAdapter(list1,infolist,getContext()));
                                }
                                @Override
                                public void onDataFailed() {
                                    msglv.setAdapter(new MsgFmtAdapter(listDATE,infolist,getContext()));
                                    LogUtil.e("error"+"onDataFailed");
                                }
                            });
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            LogUtil.e("error"+t.toString());
                        }
                    });


                } catch (DocumentException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle  = intent.getExtras();
            String msg = bundle.getString("msg");
            if (msg.equals("newmsg")){


                NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent pendingIntent3 = PendingIntent.getActivity(context, 0,
                        new Intent(context, HomeActivity.class), 0);
                Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                // 通过Notification.Builder来创建通知，注意API Level
                // API16之后才支持
                Notification notify3 = null; // 需要注意build()是在APIlevel16及之后增加的，API11可以使用getNotificatin()来替代
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    notify3 = new Notification.Builder(context)
                           .setSound(ringUri).build();
                }

                notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                manager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
                initData();

            }
        }
    }




    private String getsp2(){
        SharedPreferences preferences = getActivity().getSharedPreferences("autologin", Context.MODE_PRIVATE);
        String flag = preferences.getString("username", "false");
        return flag;
    }
    private void initview(View view) {
        msglv = (ListView) view .findViewById(R.id.msg_lv);
        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);
        msglv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView workid = (TextView) view.findViewById(R.id.order_ID);
                TextView orderID = (TextView) view.findViewById(R.id.msg_workID);

                Intent intent = new Intent();
                intent.putExtra("workID",workid.getText().toString());
                intent.putExtra("orderID",orderID.getText().toString());
                intent.putExtra("order","false");
                intent.setClass(getActivity(),MsgActivity.class);

                startActivityForResult(intent,3);
            }
        });
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(2000);
                    initData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        },0);
    }
}
