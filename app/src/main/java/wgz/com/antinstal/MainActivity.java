package wgz.com.antinstal;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import butterknife.Bind;
import wgz.com.antinstal.base.BaseActivity;
import wgz.com.antinstal.base.BaseFragment;

public class MainActivity extends BaseActivity {


   /* @Bind(R.id.app_bar_image)
    ImageView appBarImage;*/
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.appbar)
    AppBarLayout appbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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
       // toolbar.setTitle("警务APP");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(" ");
        //StatusBarUtil.setColor(this,R.color.colorPrimaryDark);
        // StatusBarUtil.setTranslucent(this,100);
        afterCreat();

    }

    private void afterCreat() {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
