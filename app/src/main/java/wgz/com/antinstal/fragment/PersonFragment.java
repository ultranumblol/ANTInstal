package wgz.com.antinstal.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wgz.com.antinstal.ChangePassActivity;
import wgz.com.antinstal.LoginActivity;
import wgz.com.antinstal.R;

/**
 * Created by qwerr on 2016/7/31.
 */

public class PersonFragment extends Fragment {


    @Bind(R.id.id_chengePass)
    CardView idChengePass;
    @Bind(R.id.id_yijianfankui)
    CardView idYijianfankui;
    @Bind(R.id.id_logout)
    CardView idLogout;
    @Bind(R.id.id_username)
    TextView idUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personfragment, null);
        ButterKnife.bind(this, view);
        initview(view);
        initMap(view);

        return view;
    }

    private void initMap(View view) {

    }

    private void initview(View view) {
        idUsername.setText(getsp2());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    private String getsp2(){
        SharedPreferences preferences =getActivity().getSharedPreferences("autologin", Context.MODE_PRIVATE);
        String flag = preferences.getString("username", "false");
        if (flag!=null){
            return flag;
        }
        else {
            return "------";
        }

    }
    @OnClick({R.id.id_chengePass, R.id.id_yijianfankui, R.id.id_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_chengePass:
                startActivity(new Intent(getActivity(), ChangePassActivity.class));
                break;
            case R.id.id_yijianfankui:

                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.id_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("注销").setMessage("是否注销登陆，返回登陆页面？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File file= new File("/data/data/"+getActivity().getPackageName().toString()+"/shared_prefs","autologin.xml");
                        if(file.exists()){
                            file.delete();
                            SharedPreferences sp = getActivity().getSharedPreferences("autologin", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.clear().commit();
                            SharedPreferences sp2 = getActivity().getSharedPreferences("username", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = sp2.edit();
                            editor2.clear().commit();

                            Toast.makeText(getActivity().getApplicationContext(), "注销成功！", Toast.LENGTH_LONG).show();
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }else {
                            Toast.makeText(getActivity().getApplicationContext(), "注销成功！", Toast.LENGTH_LONG).show();
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), LoginActivity.class));

                        }
                    }
                }).setNegativeButton("取消",null).show();


                break;
        }
    }
}
