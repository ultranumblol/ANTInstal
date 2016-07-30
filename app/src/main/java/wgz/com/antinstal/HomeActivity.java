package wgz.com.antinstal;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wgz.com.antinstal.adapter.MyFragmentPagerAdapter;
import wgz.com.antinstal.base.BaseActivity;
import wgz.com.antinstal.base.BaseFragment;
import wgz.com.antinstal.fragment.MapFragment;
import wgz.com.antinstal.fragment.MapFragment2;
import wgz.com.antinstal.fragment.MsgFragment;
import wgz.com.antinstal.fragment.OrderFragment;
import wgz.com.antinstal.fragment.PersonFragment;
import wgz.com.antinstal.view.CustomViewPager;

public class HomeActivity extends BaseActivity {
    private MsgFragment msgFragment;
    private MapFragment mapFragment;
    private PersonFragment personFragment;
    private OrderFragment orderFragment;
    private MapFragment2 mapFragment2;
    private ArrayList<Fragment> fragments;//页面列表
    private MyFragmentPagerAdapter adapter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    CustomViewPager viewpager;
    @Bind(R.id.bar1)
    ImageView bar1;
    @Bind(R.id.bar2)
    ImageView bar2;
    @Bind(R.id.bar3)
    ImageView bar3;
    @Bind(R.id.bar4)
    ImageView bar4;
    @Bind(R.id.content_home)
    ConstraintLayout contentHome;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
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
        toolbar.setTitle("业务消息");
        msgFragment = new MsgFragment();
        mapFragment = new MapFragment();
        orderFragment = new OrderFragment();
        personFragment = new PersonFragment();
        mapFragment2 = new MapFragment2();
        fragments = new ArrayList<Fragment>();
        fragments.add(msgFragment);
        fragments.add(orderFragment);
        fragments.add(mapFragment2);
        fragments.add(personFragment);
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);



        viewpager.setAdapter(adapter);


        viewpager.setCurrentItem(0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R.id.bar1, R.id.bar2, R.id.bar3, R.id.bar4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar1:
                viewpager.setCurrentItem(0);
                toolbar.setTitle("业务消息");
                break;
            case R.id.bar2:
                viewpager.setCurrentItem(1);
                toolbar.setTitle("订单查询");
                break;
            case R.id.bar3:
                viewpager.setCurrentItem(2);
                toolbar.setTitle("地图导航");

                break;
            case R.id.bar4:
                viewpager.setCurrentItem(3);
                toolbar.setTitle("个人主页");
                break;
        }
    }
}
