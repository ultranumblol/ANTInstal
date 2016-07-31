package wgz.com.antinstal.xmlpraser;

import com.zhy.http.okhttp.callback.Callback;

import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by qwerr on 2016/7/31.
 */

public abstract class InputStreamCallBack  extends Callback<InputStream> {

    @Override
    public InputStream parseNetworkResponse(Response response, int id) throws Exception {
        InputStream inputStream = response.body().byteStream();

        return inputStream;
    }

}
