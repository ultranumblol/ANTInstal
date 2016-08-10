package wgz.com.antinstal;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.dom4j.DocumentException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wgz.com.antinstal.base.BaseActivity;
import wgz.com.antinstal.base.BaseFragment;
import wgz.com.antinstal.util.OnDataFinishedListener;
import wgz.com.antinstal.util.SignMaker;
import wgz.com.antinstal.xmlpraser.AsynCallBack;
import wgz.com.antinstal.xmlpraser.PraseXmlBackground;
import wgz.com.antinstal.xmlpraser.PraserXml;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class MsgActivity extends BaseActivity {
    private ListView msg_lv;
    private LinearLayout btnlay;
    private TextView title, wancheng, unwanchang, showinmap;
    private TextView orderID, name, phone, servType, address, money, delivery, azreservation, pilot, pilotphone, shopname;
    private String workID, daohangAdd = "";
    private Toolbar toolbar;
    private List<Map<String,Object>> mData;
    private String errorid="1";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            finish();

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
        wancheng = (TextView) findViewById(R.id.Tv_wancheng);
        unwanchang = (TextView) findViewById(R.id.Tv_unwancheng);
        showinmap = (TextView) findViewById(R.id.Tv_showInMap);
        shopname = (TextView) findViewById(R.id.shopname);
        btnlay = (LinearLayout) findViewById(R.id.btn_layout);
        if (flag.contains("true")){
            btnlay.setVisibility(View.GONE);
        }else {
            btnlay.setVisibility(View.VISIBLE);
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
        showinmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("endAddress", address.getText().toString());
                /*intent.setClass(MsgActivity.this,NewMapActivity.class);
                startActivity(intent);*/
            }
        });
        //销单已完成订单
        wancheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        String sign = sm.getsignCode("type=" + "set", "id=" + workID, "state=" + 1, "code=" + code2, "remark=" + remark2,"username="+getsp2());

                        Call<String> call = app.apiService.finishOrder(workID, "set", "1", code2, remark2,getsp2(),sign);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                LogUtil.e("resultStr: " + response.body());
                                if (response.body().contains("true")) {
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
        });
        //销单未完成订单
        unwanchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] areas = new String[]{"客户不在家","货物不全","能力不足","不适当的服务条件"
                ,"支付问题","客户信息不全","不正确或丢失图纸","信息不全","物品错误","物品损失",
                        "测量不正确","销售错误","安装错误","客户需求的变化","丢失图纸"};
                AlertDialog ad =new AlertDialog.Builder(MsgActivity.this).setTitle("请选择未完成类型：").setSingleChoiceItems(
                        areas, 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                errorid = which+"";
                                LogUtil.e("errorid=="+errorid);
                            }
                        }).setNegativeButton("取消",null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DialogForReason();
                    }
                }).show();
               // DialogForReason();

            }
        });

        msg_lv = (ListView) findViewById(R.id.id_goods_list);

        initData();

    }

    private String getsp2() {
        SharedPreferences preferences = getSharedPreferences("autologin", Context.MODE_PRIVATE);
        String flag = preferences.getString("username", "????");
        return flag;
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
                String sign = signMaker.getsignCode2("type="+"set","id="+workID,"state="+2,"username="+getsp2()
                        ,"remark="+reason,"errorid="+errorid);


                Call<String>  call = app.apiService.unFinishOrder(workID,"set","2",reason,errorid,getsp2(),sign);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        LogUtil.e("MSgerrorid:"+response.body());
                        Toast.makeText(MsgActivity.this, "操作完成", Toast.LENGTH_SHORT).show();
                        MsgActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                LogUtil.e("MSgerror::"+t.toString());
                    }
                });


                /*ParserDetilXml pd = new ParserDetilXml("set",workID,"2",getsp2(),reason,null);
                pd.execute();
                pd.setOnDataFinishedListener(new OnDataFinishedListener() {
                    @Override
                    public void onDataSuccessfully(Object data) {
                        Toast.makeText(MsgActivity.this,"操作完成",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onDataFailed() {
                        Toast.makeText(MsgActivity.this,"操作完成",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });*/


            }
        });
        builder.show();
    }


    public static String formatDouble4(double d) {
        DecimalFormat df = new DecimalFormat("#.00");


        return df.format(d);
    }
    private void initData() {

        Intent intent = getIntent();

        workID = intent.getStringExtra("workID");
        SignMaker sm = new SignMaker();
        String sign = sm.getsign("type="+"get","id="+workID);


        Call<ResponseBody>  call = app.apiService.getMSGDetil(workID,"get",sign);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                PraserXml px = new PraserXml();
                try {

                    mData = px.prase(response.body().byteStream());
                    LogUtil.e("mData=="+mData.toString());
                    orderID.setText(mData.get(0).get("aznumber").toString());
                    name.setText(mData.get(0).get("name").toString());
                    phone.setText(mData.get(0).get("phone1").toString());

                    address.setText(mData.get(0).get("address").toString());

                    double money3 = Double.parseDouble(mData.get(0).get("price").toString());
                    String money2 = formatDouble4(money3);
                    money.setText(money2);
                    azreservation.setText(mData.get(0).get("azreservation").toString());
                    delivery.setText(mData.get(0).get("delivery").toString());
                    servType.setText(mData.get(0).get("servertype").toString());
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


                    msg_lv.setAdapter(new SimpleAdapter(MsgActivity.this, mData, R.layout.goods_lv_item, new String[]{"name1", "quantity", "goodsmoney", "servicestype"},

                            new int[]{R.id.id_goods_name, R.id.id_goods_num, R.id.id_goods_price, R.id.id_goods_type}));



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
}
