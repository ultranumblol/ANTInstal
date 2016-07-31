package wgz.com.antinstal;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

        toolbar.setTitle("订单详情");
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
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    startActivity(dialIntent);
                                    return;
                                }
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
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    startActivity(dialIntent);
                                    return;
                                }
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
                intent.putExtra("endAddress",address.getText().toString());
                /*intent.setClass(MsgActivity.this,NewMapActivity.class);
                startActivity(intent);*/
            }
        });
        showinmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("endAddress",address.getText().toString());
                /*intent.setClass(MsgActivity.this,NewMapActivity.class);
                startActivity(intent);*/
            }
        });
        //销单已完成订单
       /* wancheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputServer = new EditText(MsgActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MsgActivity.this);
                final View view = layoutInflater.inflate(R.layout.dialog_code_remark,null);


                AlertDialog.Builder builder = new AlertDialog.Builder(MsgActivity.this);
                builder.setTitle("确认完成").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText mCode = (EditText) view.findViewById(R.id.id_complate_code);
                        EditText mRemark = (EditText) view.findViewById(R.id.id_complate_remark);
                        String remark2 = "";
                        String code2 = "";
                        if (mRemark.getText()==null){
                            String remark = "---";
                            String code = mCode.getText().toString().replaceAll( "\\s", "");
                            remark2 = remark;
                            code2 = code;
                        }else {
                            String code = mCode.getText().toString().replaceAll( "\\s", "");
                            String remark = mRemark.getText().toString().replaceAll( "\\s", "");
                            remark2 = remark;
                            code2 = code;
                        }



                        ParserDetilXml pd = new ParserDetilXml("set",workID,"1",null,remark2,code2);
                        pd.execute();
                        pd.setOnDataFinishedListener(new OnDataFinishedListener() {
                            @Override
                            public void onDataSuccessfully(Object data) {
                                Toast.makeText(MsgActivity.this,"操作完成",Toast.LENGTH_SHORT).show();
                                MsgActivity.this.finish();
                            }
                            @Override
                            public void onDataFailed() {
                                Toast.makeText(MsgActivity.this,"验证码有误",Toast.LENGTH_SHORT).show();
                                //MsgActivity.this.finish();
                            }
                        });
                    }
                }).setNegativeButton("取消",null);
                builder.show();







            }
        });
*/

        Intent intent = getIntent();
        boolean flag =  intent.getBooleanExtra("order",false);
        msg_lv = (ListView) findViewById(R.id.id_goods_list);

        initData();

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
