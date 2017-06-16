package com.it.cloudwater.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.it.cloudwater.App;


/**
 * Toast
 */
public class ToastManager {
    private static Toast toast = null;

    public static void showError(String msg) {
        Context context = App.getInstance();
        toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        View view = toast.getView();
        TextView tv = (TextView) view.findViewById(android.R.id.message);
        view.setBackgroundColor(0xb0000000);
        tv.setSingleLine(true);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        toast.show();
    }

    public static void show(String msg) {
        Context context = App.getInstance();
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void cancle() {
        if (toast != null) {
            toast.cancel();
        }

    }
}
