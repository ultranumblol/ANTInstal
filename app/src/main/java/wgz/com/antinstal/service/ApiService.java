package wgz.com.antinstal.service;

import android.view.SurfaceHolder;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by qwerr on 2016/7/31.
 */

public interface ApiService {
    //获取工作信息
    @FormUrlEncoded
    @POST("http://erp.chinaant.com/AppDespacthingInfo.aspx")
    Call<ResponseBody> getWorkXML(@Field("username") String username,
                                  @Field("state") String state,
                                  @Field("sign") String sign);

    //更新信息
    @FormUrlEncoded
    @POST("http://erp.chinaant.com/AppInstallationEDI.aspx")
    Call<ResponseBody> getUpdateDetil(@Field("type") String type,
                                      @Field("id") String id,
                                      @Field("state") String state,
                                      @Field("username") String username,
                                      @Field("remark") String remark,
                                      @Field("code") String code);

    //获取信息详情
    @FormUrlEncoded
    @POST("http://erp.chinaant.com/AppInstallationEDI.aspx")
    Call<ResponseBody> getMSGDetil(@Field("id") String id,
                                   @Field("type") String type,
                                   @Field("sign") String sign);

    //登陆验证
    @FormUrlEncoded
    @POST("http://erp.chinaant.com/AppHandler.aspx")
    Call<String> checkUser(@Field("username") String username,
                           @Field("userpassword") String userpassword,
                           @Field("sign") String sign);
    //修改密码
    @FormUrlEncoded
    @POST("http://erp.chinaant.com/AppChangePassword.aspx")
    Call<String> changePass(@Field("username") String username,
                            @Field("oldpassword") String oldpass,
                            @Field("newpassword") String newpass,
                            @Field("sign") String sign);

    //上传gps
    @FormUrlEncoded
    @POST("http://erp.chinaant.com/shangchuanGPS")
    Call<String> updateGPS(@Field("userphone") String userphone,
                           @Field("Longitude") String longitude,
                           @Field("latitude") String latitude);


}
