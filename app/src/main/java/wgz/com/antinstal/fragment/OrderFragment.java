package wgz.com.antinstal.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import wgz.com.antinstal.R;

/**
 * Created by qwerr on 2016/7/31.
 */

public class OrderFragment extends Fragment {


    @Bind(R.id.tab_orderFragment_title)
    TabLayout tabOrderFragmentTitle;
    @Bind(R.id.textView3)
    TextView textView3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderfragment, null);

        ButterKnife.bind(this, view);
        initview(view);
        initMap(view);
        return view;
    }

    private void initMap(View view) {

    }

    private void initview(View view) {

    tabOrderFragmentTitle.addTab(tabOrderFragmentTitle.newTab().setText("已妥投"));
        tabOrderFragmentTitle.addTab(tabOrderFragmentTitle.newTab().setText("未妥投"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
