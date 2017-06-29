package com.it.cloudwater.user.observer;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;


import com.it.cloudwater.user.RegisterActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hpc on 2017/5/21.
 */

public class SmsObserver extends ContentObserver {

    private Context mContext;
    private Handler mHandler;

    public SmsObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange);
        Log.d("DEBUG", "SMS has changed!");
        Log.d("DEBUG", uri.toString());
        if (uri.toString().equals("content://sms/raw")) {
            return;
        }
        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor cursor = mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");
        if (cursor!=null){
            if (cursor.moveToFirst()){
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String body = cursor.getString(cursor.getColumnIndex("body"));
                Log.d("DEBUG", "发件人为：" + address + " " + "短信内容为：" + body);
                Pattern pattern = Pattern.compile("(\\d{6})");
                Matcher matcher = pattern.matcher(body);
                if (matcher.find()){
                    String code = matcher.group(0);
                    Log.d("DEBUG", "code is" + code);

                    mHandler.obtainMessage(RegisterActivity.MSG_RECEIVED_CODE,code).sendToTarget();
                }
            }
            cursor.close();
        }
    }
}
