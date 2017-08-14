package com.it.cloudwater.http;

import android.util.Log;

import com.it.cloudwater.App;
import com.it.cloudwater.R;
import com.it.cloudwater.constant.Constant;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;


/**
 * Created by hpc on 2017/7/6.
 */

public class CloudApi {
    public final static String GET_DATA_FAIL = App.getInstance().getString(R.string.gank_get_data_fail);
    public final static String NET_FAIL = App.getInstance().getString(R.string.gank_net_fail);
    private static final String TAG = "CloudApi";

    public static void getSmsCode(final int what, final String phone, final MyCallBack myCallBack) {
        OkGo.<String>post(Constant.SENDSMS_URL)
                .tag(App.getInstance())
                .params("strMobile", phone)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        myCallBack.onSuccess(what, response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        myCallBack.onFail(what, response);
                    }
                });
    }

    public static void Register(final int what, String phone, String password, String smsCode, final MyCallBack myCallBack) {
        OkGo.<String>post(Constant.REGISTER_URL)
                .tag(App.getInstance())
                .params("strMobile", phone)
                .params("strPassword", password)
                .params("strUserSmsCode", smsCode)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        myCallBack.onSuccess(what, response);
                        Log.i(TAG, "onSuccess: " + response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        myCallBack.onFail(what, response);
                    }
                });
    }

    public static void Login(final int what, String phone, String password, final MyCallBack myCallBack) {
        OkGo.<String>post(Constant.LOGIN_URL)
                .tag(App.getInstance())
                .params("strMobile", phone)
                .params("strPassword", password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        myCallBack.onSuccess(what, response);
                        Log.i(TAG, "onSuccess: " + response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        myCallBack.onFail(what, response);
                    }
                });
    }

    public static void ChangePassword(final int what, String phone, String password, final MyCallBack myCallBack) {
        OkGo.<String>post(Constant.CHANGE_PASSWORD_URL)
                .tag(App.getInstance())
                .params("strMobile", phone)
                .params("strPassword", password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        myCallBack.onSuccess(what, response);
                        Log.i(TAG, "onSuccess: " + response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        myCallBack.onFail(what, response);
                    }
                });
    }

    public static void getGoodsListData(final int what, final Integer nPage, final Integer nMaxNum, final MyCallBack myCallBack) {
        OkGo.<String>post(Constant.GOODS_LIST_URL)
                .tag(App.getInstance())
                .params("nPage", nPage)
                .params("nMaxNum", nMaxNum)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response);
                        myCallBack.onSuccess(what, response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        myCallBack.onFail(what, response);
                    }
                });
    }

    public static void getGoodsDetailData(final int what, final Long goodListId, final MyCallBack myCallBack) {
        OkGo.<String>get(Constant.GOODS_DETAIL_URL + goodListId)
                .tag(App.getInstance())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response);
                        myCallBack.onSuccess(what, response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        myCallBack.onFail(what, response);
                    }
                });
    }

    public static void orderSubmit(final int what, final Long goodListId, final MyCallBack myCallBack) {

    }

    public static void orderList(final int what, final Integer nPage, final Integer nMaxNum, long lBuyerid, Integer nState, final MyCallBack myCallBack) {
        OkGo.<String>post(Constant.MY_ORDER_LIST_URL)
                .tag(App.getInstance())
                .params("nPage", nPage)
                .params("nMaxNum", nMaxNum)
                .params("lBuyerid", lBuyerid)
                .params("nState", nState)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        myCallBack.onSuccess(what, response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        myCallBack.onFail(what, response);
                    }
                });
    }

    public static void addAddress(final int what, final JSONObject jsonObject, final MyCallBack myCallBack) {
        OkGo.<String>post(Constant.ADD_ADDRESS_URL)
                .tag(App.getInstance())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        myCallBack.onSuccess(what, response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        myCallBack.onFail(what, response);
                    }
                });
    }

    public static void getAreaList(final int what, final MyCallBack myCallBack) {
        OkGo.<String>get(Constant.AREA_LIST_URL)
                .tag(App.getInstance())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        myCallBack.onSuccess(what, response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        myCallBack.onFail(what, response);
                    }
                });

    }
}
