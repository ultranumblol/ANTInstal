package wgz.com.antinstal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personfragment, null);
        initview(view);
        initMap(view);
        ButterKnife.bind(this, view);
        return view;
    }

    private void initMap(View view) {

    }

    private void initview(View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
                break;
        }
    }
}
