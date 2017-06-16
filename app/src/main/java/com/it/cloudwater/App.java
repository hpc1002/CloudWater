package com.it.cloudwater;

import android.app.Activity;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hpc on 2017/3/23.
 */

public class App extends Application {
    //记录当前栈里所有activity
    private List<Activity> activities = new ArrayList<Activity>();
    //记录需要一次性关闭的页面
    private List<Activity> activitys = new ArrayList<Activity>();
    private String tokenId;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initLeakCanary();
    }
    /**
     * use LeakCanary to check mey leak
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }


    /**
     * 应用实例
     **/
    private static App instance;

    /**
     * 获得实例
     *
     * @return
     */
    public static App getInstance() {
        return instance;
    }

    /**
     * 新建了一个activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /*
    *给临时Activitys
    * 添加activity
    * */
    public void addTemActivity(Activity activity) {
        activitys.add(activity);
    }

    public void finishTemActivity(Activity activity) {
        if (activity != null) {
            this.activitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /*
    * 退出指定的Activity
    * */
    public void exitActivitys() {
        for (Activity activity : activitys) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 应用退出，结束所有的activity
     */
    public void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }

}
