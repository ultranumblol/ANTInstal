package wgz.com.antinstal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wgz.com.antinstal.R;

/**
 * Created by qwerr on 2016/7/31.
 */

public class OrderFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderfragment, null);
        initview(view);
        initMap(view);
        return view;
    }

    private void initMap(View view) {

    }

    private void initview(View view) {

    }
}
