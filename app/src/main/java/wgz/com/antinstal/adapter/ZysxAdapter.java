package wgz.com.antinstal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.Map;

/**
 * Created by qwerr on 2016/9/11.
 */

public class ZysxAdapter extends RecyclerArrayAdapter<Map<String, Object>> {
    public ZysxAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZysxViewHolder(parent);
    }


}
