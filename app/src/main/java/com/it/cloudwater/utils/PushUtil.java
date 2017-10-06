package com.it.cloudwater.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

public class PushUtil {

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
//                    LogUtil.d("push bind push success = " + s + " userId = " + userId);
                }

                @Override
                public void onFailed(String s, String s1) {
//                    LogUtil.d("push bind push failed = " + s + "-" + s1);
                }
            });
        }
    }

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
