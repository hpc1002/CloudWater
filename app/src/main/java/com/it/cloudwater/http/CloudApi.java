package com.it.cloudwater.http;

import android.util.Log;

import com.it.cloudwater.App;
import com.it.cloudwater.R;
import com.it.cloudwater.constant.Constant;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;


/**
 * Created by hpc on 2017/7/6.
 */

public class CloudApi {
    public final static String GET_DATA_FAIL = App.getInstance().getString(R.string.gank_get_data_fail);
    public final static String NET_FAIL = App.getInstance().getString(R.string.gank_net_fail);
    private static final String TAG = "CloudApi";
    public static void getRecommendData(final int what, final MyCallBack myCallBack) {
        OkGo.<String>get(Constant.COURSE_RECOMMEND_URL)
                .tag(App.getInstance())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: "+response);
                        String body = response.body();
                        myCallBack.onSuccess(what, body);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        myCallBack.onFail(what, response);
                    }
                });
    }
}
