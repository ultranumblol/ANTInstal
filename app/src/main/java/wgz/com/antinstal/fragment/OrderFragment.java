package wgz.com.antinstal.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import wgz.com.antinstal.MsgActivity;
import wgz.com.antinstal.R;
import wgz.com.antinstal.adapter.OrderAdapter;
import wgz.com.antinstal.app;
import wgz.com.antinstal.util.OnDataFinishedListener;
import wgz.com.antinstal.util.SignMaker;
import wgz.com.antinstal.view.RefreshableView;
import wgz.com.antinstal.xmlpraser.InputStreamCallBack;
import wgz.com.antinstal.xmlpraser.ParserWorkerXml;
import wgz.datatom.com.utillibrary.util.LogUtil;

import static java.util.Collections.sort;

/**
 * Created by qwerr on 2016/7/31.
 */

public class OrderFragment extends Fragment {
    private ListView tuotoulv,untuotoulv;
    private RefreshableView refreshableView2,refreshableView3;
    private List<Map<String, Object>> listDATA = new ArrayList<Map<String,Object>>();
    private InputStream inputStream1,inputStream2;

    @Bind(R.id.tab_orderFragment_title)
    TabLayout tabOrderFragmentTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderfragment, null);

        ButterKnife.bind(this, view);
        initview(view);

        return view;
    }


    private void initview(View view) {

        tabOrderFragmentTitle.addTab(tabOrderFragmentTitle.newTab().setText("已妥投"));
        tabOrderFragmentTitle.addTab(tabOrderFragmentTitle.newTab().setText("未妥投"));
        tabOrderFragmentTitle.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    refreshableView3.setVisibility(View.VISIBLE);
                    refreshableView2.setVisibility(View.GONE);

                }if (tab.getPosition()==1){
                    refreshableView2.setVisibility(View.VISIBLE);
                    refreshableView3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        refreshableView2 = (RefreshableView) view.findViewById(R.id.refreshable_view2);
        refreshableView3 = (RefreshableView) view.findViewById(R.id.refreshable_view3);
        tuotoulv = (ListView) view.findViewById(R.id.tuotou_lv);
        untuotoulv = (ListView) view.findViewById(R.id.untuotou_lv);
       initData();
        initData2();
        tuotoulv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView workid = (TextView) view.findViewById(R.id.work_id);

                Intent intent = new Intent();
                intent.putExtra("workID",workid.getText().toString());
                intent.putExtra("order","true");
                intent.setClass(getActivity(),MsgActivity.class);
                startActivityForResult(intent,0);

            }
        });
        untuotoulv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView workid = (TextView) view.findViewById(R.id.work_id);

                Intent intent = new Intent();
                intent.putExtra("order","true");
                intent.putExtra("workID",workid.getText().toString());

               intent.setClass(getActivity(),MsgActivity.class);
                startActivityForResult(intent,0);
            }
        });
        refreshableView2.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(1000);
                    initData2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView2.finishRefreshing();
            }
        }, 2);

        refreshableView3.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(1000);
                    initData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView3.finishRefreshing();
            }
        },3);



    }
    private String getsp2(){
        SharedPreferences preferences = getActivity().getSharedPreferences("autologin", Context.MODE_PRIVATE);
        String flag = preferences.getString("username", "false");
        return flag;
    }
    private void initData2() {
        SignMaker signMaker = new SignMaker();
        String sign =signMaker.getsign(getsp2(),2);

        retrofit2.Call<ResponseBody> call = app.apiService.getWorkXML(getsp2(),"2",sign);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                inputStream2 = response.body().byteStream();
                ParserWorkerXml pw = new ParserWorkerXml(inputStream2);
                pw.execute();
                pw.setOnDataFinishedListener(new OnDataFinishedListener() {
                    @Override
                    public void onDataSuccessfully(Object data) {
                        List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
                        list1 = (List<Map<String, Object>>) data;
                        //Log.i("xml","list222==="+list1.toString());

                        sort(list1);
                        untuotoulv.setAdapter(new OrderAdapter(list1,getActivity(),2));
                    }

                    @Override
                    public void onDataFailed() {
                        untuotoulv.setAdapter(new OrderAdapter(listDATA,getActivity(),2));

                    }
                });
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                LogUtil.e("error"+t.toString());
            }
        });
    }

    private void initData() {
        SignMaker signMaker = new SignMaker();
        String sign =signMaker.getsign(getsp2(),1);

        retrofit2.Call<ResponseBody> call = app.apiService.getWorkXML(getsp2(),"1",sign);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                inputStream1 = response.body().byteStream();
                ParserWorkerXml pw = new ParserWorkerXml(inputStream1);
                pw.execute();
                pw.setOnDataFinishedListener(new OnDataFinishedListener() {
                    @Override
                    public void onDataSuccessfully(Object data) {
                        List<Map<String, Object>> list1 = new ArrayList<Map<String,Object>>();
                        list1 = (List<Map<String, Object>>) data;
                        //Log.i("xml","list1111==="+list1.toString());
                        sort(list1);
                        tuotoulv.setAdapter(new OrderAdapter(list1,getActivity(),1));
                    }

                    @Override
                    public void onDataFailed() {
                        tuotoulv.setAdapter(new OrderAdapter(listDATA,getActivity(),1));
                    }
                });
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    //根据workID对list排序
    public void sort(List<Map<String, Object>> list) {
        Collections.sort(list, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                return Integer.parseInt(o1.get("workID").toString())  > Integer.parseInt(o2
                        .get("workID").toString())  ? (Integer.parseInt(o1.get("workID").toString())  == Integer.parseInt(o2
                        .get("workID").toString())  ? 0 : -1) : 1;
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
