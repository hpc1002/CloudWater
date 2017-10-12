package com.it.cloudwater.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class PushUtil {
    private static final String TAG = "PushUtil";
    private static CloudPushService pushService;

    public static void initCloudService(Context context) {
        if (pushService == null) {
            synchronized (PushUtil.class) {
                if (pushService == null) {
                    PushServiceFactory.init(context);
                    pushService = PushServiceFactory.getCloudPushService();
//                    LogUtil.d("push device id = " + pushService.getDeviceId());
                    registerPush(context);
                }
            }
        }
    }

    private static void registerPush(final Context context) {
        pushService.register(context, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
//                LogUtil.d("push init push success");
                doBindAccount(context);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
//                LogUtil.d("push init push failed errorCode = " + errorCode + " errMsg =" + errorMessage);
            }
        });
    }

    private static void doBindAccount(Context context) {
        final String userId = StorageUtil.getUserId(context);
        if (!TextUtils.isEmpty(userId)) {
            pushService.bindAccount(userId, new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    Log.d("推送....", "push bind push success = " + s + " userId = " + userId);
                    String deviceId = pushService.getDeviceId();
                    Log.i(TAG, "onSuccess: " + deviceId);
//                    CloudApi.setDevice(0x001, Long.parseLong(userId), 1, deviceId, myCallBack);
                }

                @Override
                public void onFailed(String s, String s1) {
                    Log.d("推送.....", "push bind push failed = " + s + "-" + s1);
                }
            });
        }
    }

    private static MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    try {
                        JSONObject jsonObject = new JSONObject(result.body());
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            ToastManager.show("111");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        @Override
        public void onFail(int what, Response<String> result) {

        }
    };

    public static void bindAccount(Context context) {
        if (pushService == null) {
            initCloudService(context);
        } else {
            doBindAccount(context);
        }
    }

    public static void unBindAccount() {
        if (pushService == null) {
            return;
        }
        pushService.unbindAccount(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
//                LogUtil.d("push unbind success");
            }

            @Override
            public void onFailed(String s, String s1) {
//                LogUtil.d("push unbind failed = " + s + "---" + s1);
            }
        });
    }
}
