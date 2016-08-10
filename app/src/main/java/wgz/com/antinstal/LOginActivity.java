package wgz.com.antinstal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wgz.com.antinstal.base.BaseActivity;
import wgz.com.antinstal.base.BaseFragment;
import wgz.com.antinstal.util.CheckLogin;
import wgz.com.antinstal.util.OnDataFinishedListener;
import wgz.com.antinstal.util.SignMaker;
import wgz.datatom.com.utillibrary.util.LogUtil;

public class LoginActivity extends BaseActivity {


    @Bind(R.id.login_name)
    EditText loginName;
    @Bind(R.id.login_pass)
    EditText loginPass;
    @Bind(R.id.autologin)
    CheckBox autologin;

    @Bind(R.id.login)
    LinearLayout login;

    @Override
    public int getLayoutId() {
        return R.layout.login;
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
        String flag = getsp();
        String username = getsp2();
        loginName.setFocusable(true);
        loginName.setFocusableInTouchMode(true);
        loginName.requestFocus();

    }

    private String getsp() {
        SharedPreferences preferences = getSharedPreferences("autologin", Context.MODE_PRIVATE);
        String flag = preferences.getString("autologin", "false");
        return flag;
    }

    private String getsp2() {
        SharedPreferences preferences = getSharedPreferences("autologin", Context.MODE_PRIVATE);
        String flag = preferences.getString("username", "false");
        return flag;
    }

    /**
     * 判断网络状态
     *
     * @param context
     * @return
     */
    public static boolean checkNetWorkStatus(Context context) {
        boolean result;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnected()) {
            result = true;
            Log.i("xml", "The net was connected");
        } else {
            result = false;
            Log.i("xml", "The net was bad!");
        }
        return result;
    }

    private void savesp() {
        SharedPreferences preferences = getSharedPreferences("autologin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("autologin", "true");
        editor.commit();

    }

    private void savesp2() {
        SharedPreferences preferences = getSharedPreferences("autologin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", loginName.getText().toString());
        editor.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login)
    public void onClick() {
        String lname = loginName.getText().toString();
        String pass = loginPass.getText().toString();
        SignMaker signMaker = new SignMaker();
        String sign =signMaker.getsign("username="+lname,"userpassword="+pass, null);//通过用户名，密码制作sign


        Call<String> call = app.apiService.checkUser(lname,pass,sign);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                LogUtil.e("Checklogin:: result::"+result);
                if (result.contains("true")){
                    Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                    if (autologin.isChecked()) {
                        savesp();
                    }
                    savesp2();
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "请输入正确的用户名和密码！", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.e("error:"+ t.toString());

            }
        });
    }
}
