package wgz.com.antinstal.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import wgz.datatom.com.utillibrary.util.LogUtil;

/**
 * 签名生成器
 * @author qwerr
 *
 */
public class SignMaker {
    /**
     * 获得加密的签名
     * @return  返回md5加密的签名
     */
    public String getsign(String type,String id,String state){
        MD5Util md5Util = new MD5Util();
        ArrayList<String> pass = new ArrayList<String>();
        if (state==null) {
            pass.add(type);
            pass.add(id);
        }
        if (state!=null){
            pass.add(type);
            pass.add(id);
            pass.add(state);

        }
        Log.i("xml", "===========" + pass.toString());
        Collections.sort(pass);//对数组里的元素按首字母排序
        String result = "";
        String seprater = "&";
        if (pass.size()==2) {
            result=pass.get(0)+seprater+pass.get(1);
        }if(pass.size()==3) {
            result=pass.get(0)+seprater+pass.get(1)+seprater+pass.get(2);
        }
        String sign1=md5Util.MD5(result);
        Log.i("xml", "加密内容11111：" + result + "加密后" + sign1);
        return sign1;


    }
    public String getsign(String type,String id,String state,String username,String remark){
        MD5Util md5Util = new MD5Util();
        ArrayList<String> pass = new ArrayList<String>();

        pass.add(type);
        pass.add(id);
        pass.add(username);
        pass.add(state);
        pass.add(remark);

        Log.i("xml", "===========" + pass.toString());
        Collections.sort(pass);//对数组里的元素按首字母排序
        String result = "";
        String seprater = "&";
        result=pass.get(0)+seprater+pass.get(1)+seprater+pass.get(2)+seprater+pass.get(3)+seprater+pass.get(4);
        String sign1=md5Util.MD5(result);
        Log.i("xml", "加密内容：" + result + "加密后" + sign1);
        return sign1;


    }
    public String getsignCode(String type,String id,String state,String code){
        MD5Util md5Util = new MD5Util();
        ArrayList<String> pass = new ArrayList<String>();

        pass.add(type);
        pass.add(id);
        pass.add(state);
        pass.add(code);

        Log.i("xml", "===========" + pass.toString());
        Collections.sort(pass);//对数组里的元素按首字母排序
        String result = "";
        String seprater = "&";
        result=pass.get(0)+seprater+pass.get(1)+seprater+pass.get(2)+seprater+pass.get(3);
        String sign1=md5Util.MD5(result);
        Log.i("xml", "加密内容：" + result + "加密后" + sign1);
        return sign1;


    }
    public String getsignCode(String type,String id,String state,String code,String remark,String username){
        MD5Util md5Util = new MD5Util();
        ArrayList<String> pass = new ArrayList<String>();

        pass.add(type);
        pass.add(id);
        pass.add(state);
        pass.add(code);
        pass.add(remark);
        pass.add(username);

        Log.i("xml", "===========" + pass.toString());
        Collections.sort(pass);//对数组里的元素按首字母排序
        String result = "";
        String seprater = "&";
        result=pass.get(0)+seprater+pass.get(1)+seprater+pass.get(2)+seprater+pass.get(3)+seprater+pass.get(4)+seprater+pass.get(5);
        String sign1=md5Util.MD5(result);
        Log.i("xml", "加密内容：" + result + "加密后" + sign1);
        return sign1;
    }
    public String getsignCode2(String type,String id,String state,String username,String remark,String errorid){
        MD5Util md5Util = new MD5Util();
        ArrayList<String> pass = new ArrayList<String>();

        pass.add(type);
        pass.add(id);
        pass.add(state);
        pass.add(username);
        pass.add(remark);
        pass.add(errorid);

        Log.i("xml", "===========" + pass.toString());
        Collections.sort(pass);//对数组里的元素按首字母排序
        String result = "";
        String seprater = "&";
        result=pass.get(0)+seprater+pass.get(1)+seprater+pass.get(2)+seprater+pass.get(3)+seprater+pass.get(4)+seprater+pass.get(5);
        String sign1=md5Util.MD5(result);
        // Log.i("xml", "加密内容：" + result + "加密后" + sign1);
        LogUtil.e("加密内容：" + result + "加密后" + sign1);
        return sign1;
    }
    public String getsignCode3(String type,String id,String state,String username,String remark,String errorid,String code){
        MD5Util md5Util = new MD5Util();
        ArrayList<String> pass = new ArrayList<String>();

        pass.add(type);
        pass.add(id);
        pass.add(state);
        pass.add(username);
        pass.add(remark);
        pass.add(errorid);
        pass.add(code);
        Log.i("xml", "===========" + pass.toString());
        Collections.sort(pass);//对数组里的元素按首字母排序
        String result = "";
        String seprater = "&";
        result=pass.get(0)+seprater+pass.get(1)+seprater+pass.get(2)+seprater+pass.get(3)+seprater+pass.get(4)+seprater+pass.get(5)+seprater+pass.get(6);
        String sign1=md5Util.MD5(result);
        // Log.i("xml", "加密内容：" + result + "加密后" + sign1);
        LogUtil.e("加密内容：" + result + "加密后" + sign1);
        return sign1;
    }
    public String getsign(String type,String id){
        MD5Util md5Util = new MD5Util();
        ArrayList<String> pass = new ArrayList<String>();

        pass.add(type);
        pass.add(id);
        Log.i("xml", "===========" + pass.toString());
        Collections.sort(pass);//对数组里的元素按首字母排序
        String result = "";
        String seprater = "&";
        if (pass.size()==2) {
            result=pass.get(0)+seprater+pass.get(1);
        }
        String sign1=md5Util.MD5(result);
        LogUtil.e("加密内容：" + result + "加密后" + sign1);
        //Log.i("xml", "加密内容：" + result + "加密后" + sign1);
        return sign1;


    }
    public String getsign(String name,int state){
        MD5Util md5Util = new MD5Util();
        ArrayList<String> pass = new ArrayList<String>();

        pass.add("username="+name);
        pass.add("state="+state);
        LogUtil.e("签名"+pass.toString());
       // Log.i("xml", "===========" + pass.toString());
        Collections.sort(pass);//对数组里的元素按首字母排序
        String result = "";
        String seprater = "&";
        if (pass.size()==2) {
            result=pass.get(0)+seprater+pass.get(1);
        }
        String sign1=md5Util.MD5(result);
       // Log.i("xml", "加密内容：" + result + "加密后" + sign1);
        LogUtil.e("加密内容：" + result + "加密后" + sign1);
        return sign1;


    }
    public String signCP(String username,String oldpassword,String newpassword){
        MD5Util md5Util = new MD5Util();
        ArrayList<String> pass = new ArrayList<String>();

        pass.add(username);
        pass.add(oldpassword);
        pass.add(newpassword);
        Log.i("xml", "===========" + pass.toString());
        Collections.sort(pass);//对数组里的元素按首字母排序
        String result = "";String seprater = "&";

            result=pass.get(0)+seprater+pass.get(1)+seprater+pass.get(2);
        String sign1=md5Util.MD5(result);
        Log.i("xml", "加密内容：" + result + "加密后" + sign1);
        return sign1;


    }
}
