package com.it.cloudwater.weixin;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 微信支付
 * Created by tsy on 16/6/1.
 */
public class WXPay {

    private static WXPay mWXPay;
    private IWXAPI mWXApi;
    private String mPayParam;
    private WXPayResultCallBack mCallback;

    public static final int NO_OR_LOW_WX = 1;   //未安装微信或微信版本过低
    public static final int ERROR_PAY_PARAM = 2;  //支付参数错误
    public static final int ERROR_PAY = 3;  //支付失败

    public interface WXPayResultCallBack {
        void onSuccess(); //支付成功
        void onError(int error_code);   //支付失败
        void onCancel();    //支付取消
    }

    public WXPay(Context context, String wx_appid) {
        mWXApi = WXAPIFactory.createWXAPI(context, null);
        mWXApi.registerApp(wx_appid);
    }

    public static void init(Context context, String wx_appid) {
        if(mWXPay == null) {
            mWXPay = new WXPay(context, wx_appid);
        }
    }
    public static WXPay getInstance(){
        return mWXPay;
    }

    public IWXAPI getWXApi() {
        return mWXApi;
    }
    /**
     * 发起微信支付
     */
    public void doPay(String pay_param, WXPayResultCallBack callback) {
        mPayParam = pay_param;
        mCallback = callback;

        if(!check()) {
            if(mCallback != null) {
                mCallback.onError(NO_OR_LOW_WX);
            }
            return;
        }

        JSONObject param = null;
        try {
            param = new JSONObject(mPayParam);
        } catch (JSONException e) {
            e.printStackTrace();
            if(mCallback != null) {
                mCallback.onError(ERROR_PAY_PARAM);
            }
            return;
        }
//        long timeStampSec = System.currentTimeMillis()/1000;
//        String timestamp = String.format("%010d", timeStampSec);
        if(TextUtils.isEmpty(param.optString("appid")) || TextUtils.isEmpty(param.optString("partnerid"))
                || TextUtils.isEmpty(param.optString("prepayid")) || TextUtils.isEmpty(param.optString("package")) ||
                TextUtils.isEmpty(param.optString("noncestr"))||TextUtils.isEmpty(param.optString("timestamp"))||
                TextUtils.isEmpty(param.optString("sign"))) {
            if(mCallback != null) {
                mCallback.onError(ERROR_PAY_PARAM);
            }
            return;
        }
/*IWXAPI api;
PayReq request = new PayReq();
request.appId = "wxd930ea5d5a258f4f";
request.partnerId = "1900000109";
request.prepayId= "1101000000140415649af9fc314aa427",;
request.packageValue = "Sign=WXPay";
request.nonceStr= "1101000000140429eb40476f8896f4c9";
request.timeStamp= "1398746574";
request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
               3B838492122AB6E561176C7D2A5D3D03
api.sendReq(req);*/
//        String sign = param.optString("sign");

        PayReq req = new PayReq();
        req.appId = param.optString("appid");
        req.nonceStr = param.optString("noncestr");
        req.partnerId = param.optString("partnerid");
        req.prepayId = param.optString("prepayid");
        req.packageValue = param.optString("package");
        req.timeStamp = param.optString("timestamp");
        req.sign = param.optString("sign");

        mWXApi.sendReq(req);
    }

    //支付回调响应
    public void onResp(int error_code) {
        Log.i("status====mCallback----",mCallback+"");
        if(mCallback == null) {
            return;
        }

        if(error_code == 0) {   //成功
            mCallback.onSuccess();
        } else if(error_code == -1) {   //错误
            mCallback.onError(ERROR_PAY);
        } else if(error_code == -2) {   //取消
            mCallback.onCancel();
        }

        mCallback = null;
    }

    //检测是否支持微信支付
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }
}
