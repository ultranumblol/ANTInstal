package wgz.com.antinstal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.List;
import java.util.Map;

import wgz.com.antinstal.R;

/**
 * Created by qwerr on 2015/12/24.
 */
public class MsgFmtAdapter extends BaseAdapter {
    private List<Map<String,Object>> data;
    private LayoutInflater inflater;
    private Context context;
    private List<Map<String,Object>> Updatedata;

    public MsgFmtAdapter(List<Map<String, Object>> data,List<Map<String,Object>> Updatedata, Context context) {
        this.data = data;
        this.Updatedata =Updatedata;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.msglv_item,null);
            viewHolder.workID = (TextView) convertView.findViewById(R.id.msg_workID);
            viewHolder.workerName = (TextView) convertView.findViewById(R.id.msg_workerName);
            viewHolder.orderID = (TextView) convertView.findViewById(R.id.order_ID);
            viewHolder.rootview = (RelativeLayout) convertView.findViewById(R.id.rootview);
            convertView.setTag(viewHolder);


        }else {
            viewHolder= (ViewHolder) convertView.getTag();

        }


        Map<String,Object> map = data.get(position);


        try{
            for (int i = 0 ; i <Updatedata.size(); i ++){

                if (map.get("aznumber").toString().equals(Updatedata.get(i).get("ordernumber"))){
                    viewHolder.workID.setText(map.get("aznumber").toString());
                    viewHolder.workerName.setText(map.get("workerName").toString());
                    viewHolder.orderID.setText(map.get("workID").toString());
                    viewHolder.rootview.setBackgroundColor(Color.parseColor("#ff5151"));
                }else{
                    viewHolder.workID.setText(map.get("aznumber").toString());
                    viewHolder.workerName.setText(map.get("workerName").toString());
                    viewHolder.orderID.setText(map.get("workID").toString());

                }
            }

        }catch (Exception e){
            viewHolder.workID.setText(map.get("aznumber").toString());
            viewHolder.workerName.setText(map.get("workerName").toString());
            viewHolder.orderID.setText(map.get("workID").toString());
        }


       /* viewHolder.workID.setText(map.get("aznumber").toString());
        viewHolder.workerName.setText(map.get("workerName").toString());
        viewHolder.orderID.setText(map.get("workID").toString());*/



        return convertView;
    }
    class ViewHolder{
        private TextView workID,orderID;
        private TextView workerName;
        private RelativeLayout rootview;


    }


}
