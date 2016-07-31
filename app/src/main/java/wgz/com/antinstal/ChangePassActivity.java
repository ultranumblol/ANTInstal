package wgz.com.antinstal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wgz.com.antinstal.base.BaseActivity;
import wgz.com.antinstal.base.BaseFragment;
import wgz.com.antinstal.util.ChangePass;
import wgz.com.antinstal.util.OnDataFinishedListener;
import wgz.com.antinstal.util.SignMaker;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class ChangePassActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.oldpass)
    EditText oldpass;
    @Bind(R.id.newpass)
    EditText newpass;
    @Bind(R.id.againnewpass)
    EditText againnewpass;
    @Bind(R.id.cp_ok)
    Button cpOk;
    @Bind(R.id.cp_cancel)
    Button cpCancel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pass;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    public void initView() {
        toolbar.setTitle("修改密码");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.cp_ok, R.id.cp_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cp_ok:
                String oldp = oldpass.getText().toString();
                String newp = newpass.getText().toString();
                String newp2 = againnewpass.getText().toString();
                if (!newp.equals(newp2)){
                    Toast.makeText(getApplicationContext(),"两次密码不一致！",Toast.LENGTH_SHORT).show();
                }else{

                    SignMaker sm = new SignMaker();//实例化
                    //通过用户名，密码制作sign
                    String sign =sm.signCP("username="+getsp2(),"oldpassword="+oldp,"newpassword="+newp);

                    Call<String> call = app.apiService.changePass(getsp2(),oldp,newp,sign);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String result = response.body();
                            LogUtil.e("changePass:===RESULT::"+result);
                            if (result.contains("修改成功！")){
                                File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs","autologin.xml");
                                if(file.exists()){
                                    file.delete();
                                    SharedPreferences sp = getSharedPreferences("autologin", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.clear().commit();
                                    SharedPreferences sp2 = getSharedPreferences("username", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor2 = sp2.edit();
                                    editor2.clear().commit();}
                                Toast.makeText(getApplicationContext(),"修改成功，请重新登录！",Toast.LENGTH_LONG).show();
                                ChangePassActivity.this.finish();
                                startActivity(new Intent(ChangePassActivity.this,LoginActivity.class));

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"错误，请检查密码！",Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }


                break;
            case R.id.cp_cancel:
                finish();
                break;
        }
    }
    private String getsp2(){
        SharedPreferences preferences =getSharedPreferences("autologin", Context.MODE_PRIVATE);
        String flag = preferences.getString("username", "false");
        return flag;
    }
}


