package wgz.com.antinstal.test;

import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Part;

/**
 * Created by wgz on 2016/7/29.
 */

public interface gitapi {
    @GET("/users/{user}")
    public void getFeed(@Part("user") String user, Callback<gitmodel> response);
}
