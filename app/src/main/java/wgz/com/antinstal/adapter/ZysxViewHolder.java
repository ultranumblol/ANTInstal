package wgz.com.antinstal.adapter;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.Map;

import wgz.com.antinstal.R;

/**
 * Created by qwerr on 2016/9/11.
 */

public class ZysxViewHolder extends BaseViewHolder<Map<String,Object>> {
        private TextView code,select,mtext;



    public ZysxViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_zysx);
        code = $(R.id.id_zysxcode);
        select = $(R.id.id_zysx_select);
        mtext = $(R.id.id_zysx_text);
    }

    @Override
    public void setData(Map<String, Object> data) {
        code.setText(data.get("code").toString());
        select.setText(data.get("selector").toString());
        //mtext.setText(data.get("text").toString());
    }
}
