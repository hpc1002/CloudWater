package com.it.cloudwater.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.it.cloudwater.App;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private ConnectivityManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 锁定竖屏
        mContext = getActivityContext();
        initView();
        ButterKnife.bind(this);
        initdata();
        App.getInstance().addActivity(this);
    }

    private void initView() {
        loadViewLayout();
    }

    private void initdata() {
        setListener();
        processLogic();
    }

    /**
     * 业务逻辑处理，主要与后端交互
     */
    protected abstract void processLogic();

    /**
     * 设置各种事件的监听器
     */
    protected abstract void setListener();

    /**
     * 加载页面layout
     */
    protected abstract void loadViewLayout();

    /**
     * Activity.this
     */
    protected abstract Context getActivityContext();

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//		StatService.onPause(mContext);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//		StatService.onResume(mContext);
    }

//    /**
//     * toolbar返回事件拦截
//     * @param item
//     * @return
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        App.getInstance().finishActivity(this);
    }
}
