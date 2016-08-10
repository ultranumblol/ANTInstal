package wgz.com.antinstal.xmlpraser;

import android.os.AsyncTask;
import android.util.Log;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wgz.com.antinstal.bean.Worker;
import wgz.com.antinstal.util.OnDataFinishedListener;
import wgz.com.antinstal.util.XmlInputStream;
import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * Created by qwerr on 2015/12/24.
 */
public class ParserWorkerXml extends AsyncTask {
    private List<Worker> mWorker;
    private List<Map<String,Object>> wors;
    OnDataFinishedListener onDataFinishedListener;
    private XmlInputStream xmlInputStream;
    private String username,sign;
    private int state;
    private InputStream in;

    public ParserWorkerXml(String username, String sign, int state) {
        this.username = username;
        this.sign = sign;
        this.state = state;
    }

    public ParserWorkerXml(InputStream in) {
        this.in = in;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        wors = new ArrayList<Map<String,Object>>();

        //InputStream is = xmlInputStream.getStream(username,state+"",sign);
        WorkerParser wparser = new PullPraserWorker();
        try {
            mWorker = wparser.parse(in);
            for (Worker worker:mWorker){
                Map<String, Object> map = new HashMap<String, Object>();
                if (worker.getWorkID()==null){
                    map.put("workID","---");
                }else {
                    map.put("workID",worker.getWorkID());

                }
                if (worker.getAzdispatchingnumber()==null){
                    map.put("aznumber","---");
                }else {

                    map.put("aznumber",worker.getAzdispatchingnumber());
                }
                if (worker.getWorkerName()==null){
                    map.put("workerName","---");
                }
                else {
                    map.put("workerName",worker.getWorkerName());

                }
                wors.add(map);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return wors;
    }
    public void setOnDataFinishedListener(
            OnDataFinishedListener onDataFinishedListener) {
        this.onDataFinishedListener = onDataFinishedListener;
    }

    @Override
    protected void onPostExecute(Object o) {
        List<Map<String, Object>> result = (List<Map<String, Object>>) o;
        //Log.i("xml","result======="+result.size());
        LogUtil.e("result===="+result.toString());

        if(result.size()==0){
            onDataFinishedListener.onDataFailed();

        }else{
            onDataFinishedListener.onDataSuccessfully(result);
        }
    }
}
