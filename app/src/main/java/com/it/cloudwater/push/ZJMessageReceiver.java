package com.it.cloudwater.push;

import android.content.Context;
import android.content.Intent;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import java.util.Map;

public class ZJMessageReceiver extends MessageReceiver {
    public static final String ZJ_NOTIFICATION = "zhijin.xueba.notification";

    /**
     * 推送通知的回调方法
     */
    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        if ( null != extraMap ) {
            for (Map.Entry<String, String> entry : extraMap.entrySet()) {
//                LogUtil.d("push @Get diy param : Key=" + entry.getKey() + " , Value=" + entry.getValue());
            }
        } else {
//            LogUtil.d("push @收到通知 && 自定义消息为空");
        }
//        LogUtil.d("push 收到一条推送通知 ： " + title );
        context.sendBroadcast(new Intent(ZJ_NOTIFICATION));
    }

    /**
     * 推送消息的回调方法
     */
    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        try {
//            LogUtil.d("push 收到一条推送消息 ： " + cPushMessage.getTitle());
        } catch (Exception e) {
//            LogUtil.d(e.toString());
        }
    }

    /**
     * 从通知栏打开通知的扩展处理
     */
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
//        LogUtil.d("push onNotificationOpened ： " + " : " + title + " : " + summary + " : " + extraMap);
    }

    /**
     * 从通知栏移除通知的扩展处理
     */
    @Override
    public void onNotificationRemoved(Context context, String messageId) {
//        LogUtil.d("push onNotificationRemoved ： " + messageId);
    }
}
