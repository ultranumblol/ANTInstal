package wgz.com.antinstal.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wgz.com.antinstal.R;
import wgz.com.antinstal.adapter.MsgFmtAdapter;
import wgz.com.antinstal.app;
import wgz.com.antinstal.util.OnDataFinishedListener;
import wgz.com.antinstal.util.SignMaker;
import wgz.com.antinstal.view.RefreshableView;
import wgz.com.antinstal.xmlpraser.InputStreamCallBack;
import wgz.com.antinstal.xmlpraser.ParserWorkerXml;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by qwerr on 2016/7/31.
 */

public class MsgFragment extends Fragment {

    RefreshableView refreshableView;
    private ListView msglv;
    private List<Map<String, Object>> listDATE = new ArrayList<Map<String,Object>>();

    private InputStream inputStream;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.msgfragment, null);
        initview(view);
        initData();
        return view;
    }

    private void initData() {

        SignMaker signMaker = new SignMaker();
        String sign = signMaker.getsign("18108055465",0);

        Call<ResponseBody> call = app.apiService.getWorkXML("18108055465","0",sign);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LogUtil.e("1222123213213213213"+response.body());

                inputStream=response.body().byteStream();
                ParserWorkerXml pw = new ParserWorkerXml(inputStream);
                pw.execute();
                pw.setOnDataFinishedListener(new OnDataFinishedListener() {
                    @Override
                    public void onDataSuccessfully(Object data) {
                        List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
                        list1 = (List<Map<String, Object>>) data;
                        Log.i("xml","list1==="+list1.toString());
                        msglv.setAdapter(new MsgFmtAdapter(list1,getContext()));
                    }
                    @Override
                    public void onDataFailed() {
                        msglv.setAdapter(new MsgFmtAdapter(listDATE,getContext()));
                        Toast.makeText(getActivity(),"没有相关业务!",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });






    }
    private String getsp2(){
        SharedPreferences preferences = getActivity().getSharedPreferences("autologin", Context.MODE_PRIVATE);
        String flag = preferences.getString("username", "false");
        return flag;
    }
    private void initview(View view) {
        msglv = (ListView) view .findViewById(R.id.msg_lv);
        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);

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
