package wgz.com.antinstal;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;

import org.dom4j.DocumentException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wgz.com.antinstal.adapter.ZysxAdapter;
import wgz.com.antinstal.base.BaseActivity;
import wgz.com.antinstal.base.BaseFragment;

import wgz.com.antinstal.util.SignMaker;
import wgz.com.antinstal.xmlpraser.PraserXml;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class MsgActivity extends BaseActivity {
    private ListView msg_lv;
    private ListView zysx_lv;
    private LinearLayout btnlay;
    private TextView title;
    //private TextView wancheng, unwanchang, showinmap;
    private TextView orderID, name, phone, servType, address, money, delivery, azreservation, pilot, pilotphone, shopname;
    private String workID,orderID2, detilID = "";
    private Toolbar toolbar;
    private List<Map<String,Object>> mData;
    private List<Map<String,Object>> mData2 = new ArrayList<>();
    private List<Map<String,Object>> mErrorData;
    private String errorid="1";
    //private EasyRecyclerView zysx_lv;
    //private ZysxAdapter adapter;
    List<Map<String,Object>> test2 = new ArrayList<>();
    List<Map<String,Object>> shibai2 = new ArrayList<>();
    List<Map<String,Object>> buwanzheng2 = new ArrayList<>();
    List<Map<String,Object>> xinxibuquan2 = new ArrayList<>();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.putExtra("endAddress", address.getText().toString());
            intent.setClass(MsgActivity.this,NewMapActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_msg;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    public void initView() {
       // toolbar.setTitle("消息详情");
        Intent intent = getIntent();
        String flag = intent.getStringExtra("order");
        orderID2 = intent.getStringExtra("orderID");

        zysx_lv = (ListView) findViewById(R.id.id_zysx_list);
        //zysx_lv = (EasyRecyclerView) findViewById(R.id.id_zysx_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar_msg);
        orderID = (TextView) findViewById(R.id.id_order_id);
        name = (TextView) findViewById(R.id.id_order_name);
        phone = (TextView) findViewById(R.id.id_order_phone);
        servType = (TextView) findViewById(R.id.id_order_servicetype);
        address = (TextView) findViewById(R.id.id_order_address);
        money = (TextView) findViewById(R.id.id_order_anzhuangfei);
        azreservation = (TextView) findViewById(R.id.id_order_preTime);
        delivery = (TextView) findViewById(R.id.id_order_time);
        pilot = (TextView) findViewById(R.id.id_pilot_name);
        pilotphone = (TextView) findViewById(R.id.id__pilot_phone);
       /* wancheng = (TextView) findViewById(R.id.Tv_wancheng);
        unwanchang = (TextView) findViewById(R.id.Tv_unwancheng);
        showinmap = (TextView) findViewById(R.id.Tv_showInMap);*/
        shopname = (TextView) findViewById(R.id.shopname);
        btnlay = (LinearLayout) findViewById(R.id.btn_layout);
        if (flag.contains("true")){
           // btnlay.setVisibility(View.GONE);
        }else {
          //  btnlay.setVisibility(View.VISIBLE);
        }
        toolbar.setTitle("详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //点击号码拨打电话
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MsgActivity.this);
                dialog.setTitle("请确认").setMessage("是否拨打电话？").setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri
                                        .parse("tel:" + phone.getText().toString()));
                                startActivity(dialIntent);
                            }
                        });
                dialog.show();
            }
        });
        //点击驾驶员号码拨打电话
        pilotphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MsgActivity.this);
                dialog.setTitle("请确认").setMessage("是否拨打电话？").setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri
                                        .parse("tel:" + phone.getText().toString()));
                                startActivity(dialIntent);
                            }
                        });
                dialog.show();


            }
        });
        //点击地址导航
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("endAddress", address.getText().toString());
                /*intent.setClass(MsgActivity.this,NewMapActivity.class);
                startActivity(intent);*/
            }
        });

        msg_lv = (ListView) findViewById(R.id.id_goods_list);
       /* zysx_lv.setLayoutManager(new LinearLayoutManager(this));
        zysx_lv.setAdapter(adapter = new ZysxAdapter(this));*/
        initData();





    }
    private void yiwanchengxiaodan(View v){
        final EditText inputServer = new EditText(MsgActivity.this);
        LayoutInflater layoutInflater = LayoutInflater.from(MsgActivity.this);
        final View view = layoutInflater.inflate(R.layout.dialog_code_remark, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(MsgActivity.this);
        builder.setTitle("确认完成").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText mCode = (EditText) view.findViewById(R.id.id_complate_code);
                EditText mRemark = (EditText) view.findViewById(R.id.id_complate_remark);
                String remark2 = "";
                String code2 = "";
                if (mRemark.getText() == null) {
                    String remark = "---";
                    String code = mCode.getText().toString().replaceAll("\\s", "");
                    remark2 = remark;
                    code2 = code;
                } else {
                    String code = mCode.getText().toString().replaceAll("\\s", "");
                    String remark = mRemark.getText().toString().replaceAll("\\s", "");
                    remark2 = remark;
                    code2 = code;
                }

                SignMaker sm = new SignMaker();
                String sign = sm.getsignCode("type=" + "set", "id=" + detilID, "state=" + 1, "code=" + code2, "remark=" + remark2,"username="+getsp2());
                LogUtil.e("finish :" + "sign :" +sign);
                Call<String> call = app.apiService.finishOrder(detilID, "set", "1", code2, remark2,getsp2(),sign);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        LogUtil.e("resultStr: " + response.body());
                        if (!response.body().isEmpty()&&response.body().contains("true")) {
                            Toast.makeText(MsgActivity.this, "操作完成", Toast.LENGTH_SHORT).show();
                            MsgActivity.this.finish();
                        } else {
                            Toast.makeText(MsgActivity.this, "验证码有误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        LogUtil.e("error");
                    }
                });
            }
        }).setNegativeButton("取消", null);
        builder.show();


    }


    private String getsp2() {
        SharedPreferences preferences = getSharedPreferences("autologin", Context.MODE_PRIVATE);
        String flag = preferences.getString("username", "????");
        return flag;
    }
    private void test2(final List<Map<String,Object>> shibai){
        AlertDialog ad =new AlertDialog.Builder(MsgActivity.this).
                setTitle("请选择未完成原因：")
                .setSingleChoiceItems(new SimpleAdapter(getApplicationContext(), shibai, R.layout.errors_item, new String[]{"reasontext", "id"}
                        , new int[]{R.id.faild_reason, R.id.reason_id}), 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        errorid = shibai.get(which).get("id").toString();
                        //queryError(errorid);


                        LogUtil.e("errorid::"+errorid);
                        DialogForReason();
                    }
                }).show();
    }



    private void test1(final List<Map<String,Object>> shibai){
        AlertDialog ad =new AlertDialog.Builder(MsgActivity.this).
                setTitle("请选择未完成原因：")
                .setSingleChoiceItems(new SimpleAdapter(getApplicationContext(), shibai, R.layout.errors_item, new String[]{"reasontext", "id"}
                        , new int[]{R.id.faild_reason, R.id.reason_id}), 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            errorid = shibai.get(which).get("id").toString();
                            queryError(errorid);


                        LogUtil.e("errorid::"+errorid);
                        //DialogForReason();
                    }
                }).show();
    }
    private void DialogForReason() {
        final EditText inputServer = new EditText(MsgActivity.this);
        inputServer.setMinLines(3);
        AlertDialog.Builder builder = new AlertDialog.Builder(MsgActivity.this);
        builder.setTitle("未完成原因备注：").setView(inputServer)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                String reason = inputServer.getText().toString().replaceAll( "\\s", "");
                SignMaker signMaker = new SignMaker();
                String sign = signMaker.getsignCode2("type="+"set","id="+detilID,"state="+2,"username="+getsp2()
                        ,"remark="+reason,"errorid="+errorid);

                String sign2 = signMaker.getsignCode3("type="+"set","id="+detilID,"state="+2,"username="+getsp2()
                        ,"remark="+reason,"errorid="+errorid,"code=123");
                LogUtil.e("url :: detilID :"+detilID+" reason :"+ reason+" username : "+getsp2() +" errorid :"+errorid +" sign :" +sign );
                Call<String>  call = app.apiService.unFinishOrder(detilID,"set","2",reason,errorid,getsp2(),sign);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        LogUtil.e("MSgCallback :"+response.body());
                        try {
                            if (response.body().contains("true")){
                                Toast.makeText(MsgActivity.this, "操作完成", Toast.LENGTH_SHORT).show();
                                MsgActivity.this.finish();
                            }else  Toast.makeText(MsgActivity.this, "错误！", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MsgActivity.this, "服务器错误！", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        LogUtil.e("MSgerror::"+t.toString());
                    }
                });



            }
        });
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public static String formatDouble4(double d) {
        DecimalFormat df = new DecimalFormat("#.00");


        return df.format(d);
    }
    private void initData() {




        Intent intent = getIntent();
        workID = intent.getStringExtra("workID");
        orderID2 = intent.getStringExtra("orderID");
        SignMaker sm = new SignMaker();
        String sign = sm.getsign("type="+"get","id="+workID);

        String sign2 = sm.getsign("type=set","ordernumber="+orderID2);
        Call<String>  call0 = app.apiService.setMEssageInfo("set",orderID2,sign2);
        call0.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LogUtil.e("msgSET :" +response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });






        Call<ResponseBody>  call = app.apiService.getMSGDetil(workID,"get",sign);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                final PraserXml px = new PraserXml();
                try {

                    mData = px.prase(response.body().byteStream());
                    LogUtil.e("mData=="+mData.toString());

                    try {
                        detilID = mData.get(0).get("id").toString();
                    } catch (Exception e) {
                        detilID="0000";
                        e.printStackTrace();
                    }
                    orderID.setText(mData.get(0).get("aznumber").toString());
                    try {
                        name.setText(mData.get(0).get("name").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        name.setText("---");
                    }
                    phone.setText(mData.get(0).get("phone1").toString());

                    address.setText(mData.get(0).get("address").toString());

                    double money3 = Double.parseDouble(mData.get(0).get("price").toString());
                    String money2 = formatDouble4(money3);
                    money.setText(money2);
                    azreservation.setText(mData.get(0).get("azreservation").toString());
                    Call<ResponseBody> call2 = null;
                    try {
                        call2 = app.apiService.getzysxInfo(mData.get(0).get("number").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        call2 = app.apiService.getzysxInfo(mData.get(0).get("aznumber").toString());
                    }
                    call2.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                mData2 = px.prase(response.body().byteStream());
                                LogUtil.e("mdata2:"+mData2.toString());

                                zysx_lv.setAdapter(new SimpleAdapter(MsgActivity.this,mData2,R.layout.item_zysx,new String[]{"code","selector","text"},new int[]{R.id.id_zysxcode,R.id.id_zysx_select,R.id.id_zysx_text}));

                            } catch (DocumentException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                    try {
                        delivery.setText(mData.get(0).get("delivery").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        String pilophone1 = "---";
                        delivery.setText(pilophone1);
                    }


                    try {
                        servType.setText(mData.get(0).get("servertype").toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        String pilophone1 = mData.get(0).get("servicestype").toString();
                        servType.setText(pilophone1);
                    }
                    try {
                        String pilophone1 = mData.get(0).get("pilot").toString();
                        pilot.setText(pilophone1);
                    } catch (Exception e) {

                        e.printStackTrace();
                        String pilophone1 = "---";
                        pilot.setText(pilophone1);
                    }
                    try {
                        String pilophone1 = mData.get(0).get("pilotphone").toString();
                        pilotphone.setText(pilophone1);
                    } catch (Exception e) {

                        e.printStackTrace();
                        String pilophone1 = "---";
                        pilotphone.setText(pilophone1);
                    }

                    try {
                        String pilophone1 = mData.get(0).get("shopname").toString();
                        shopname.setText(pilophone1);
                    } catch (Exception e) {

                        e.printStackTrace();
                        String pilophone1 = "---";
                        shopname.setText(pilophone1);
                    }





                    msg_lv.setAdapter(new SimpleAdapter(MsgActivity.this, mData, R.layout.goods_lv_item, new String[]{"name1", "quantity", "goodsmoney", "servicestype","id"},

                            new int[]{R.id.id_goods_name, R.id.id_goods_num, R.id.id_goods_price, R.id.id_goods_type,R.id.list_detil_id}));


                    msg_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                            TextView  wancheng = (TextView) view.findViewById(R.id.Tv_wancheng);
                            TextView unwanchang = (TextView)view. findViewById(R.id.Tv_unwancheng);
                            //销单未完成订单
                            unwanchang.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    TextView id = (TextView) view.findViewById(R.id.list_detil_id);
                                    detilID = id.getText().toString();

                                    test1(mErrorData);
                                }
                            });
                            wancheng.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    yiwanchengxiaodan(v);
                                }
                            });


                        }
                        });

                    //adapter.addAll(mData);


                } catch (DocumentException e) {
                    LogUtil.e("213");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        Call<ResponseBody> call2 = app.apiService.getError2("0");
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                PraserXml praserXml = new PraserXml();
                try {
                    mErrorData = praserXml.prase(response.body().byteStream());
                    LogUtil.e("mErrorData=="+mErrorData.toString());
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    private void queryError(String pid){
        Call<ResponseBody> call2 = app.apiService.getError2(pid);
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                PraserXml praserXml = new PraserXml();
                try {
                    mErrorData = praserXml.prase(response.body().byteStream());
                    LogUtil.e("mErrorData=="+mErrorData.toString());
                    test2(mErrorData);




                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



    @Override
    public void finish() {
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("result", "该刷新了");
        //设置返回数据
        setResult(RESULT_OK, intent);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
