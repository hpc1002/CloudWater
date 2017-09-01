package com.it.cloudwater.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.it.cloudwater.App;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.home.fragment.FragmentController;
import com.it.cloudwater.user.LoginActivity;
import com.it.cloudwater.user.MessageActivity;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.it.cloudwater.utils.UIThread;

import java.util.ArrayList;

import butterknife.BindView;

public class HomeActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_order)
    RadioButton rbOrder;
    @BindView(R.id.rb_water_ticket)
    RadioButton rbWaterTicket;
    @BindView(R.id.rb_me)
    RadioButton rbMe;
    @BindView(R.id.hometab_radio)
    RadioGroup hometabRadio;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private FragmentController controller;
    private String userId;

    @Override
    protected void processLogic() {
        initPermissions();
        userId = StorageUtil.getUserId(this);
    }
    private int requestCode = 1;
    private void initPermissions() {
        String permissionPhone = Manifest.permission.READ_PHONE_STATE;
        String permissionContacts = Manifest.permission.GET_ACCOUNTS;
        String permissionStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String permissionReadSms = Manifest.permission.READ_SMS;
        String[] allPermissions = {permissionPhone, permissionContacts, permissionStorage, permissionReadSms};
        ArrayList<String> deniedPermissions = new ArrayList<>();
        for (String permission : allPermissions) {
            if (!hasPermission(permission)) {
                deniedPermissions.add(permission);
            }
        }
        if (!deniedPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this, deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
        } else {
            setListener();
        }

    }
    private boolean hasPermission(String permission) {
        int result = ActivityCompat.checkSelfPermission(this, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.requestCode) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    finish();
                }
            }

            setListener();
        }
    }
    @Override
    protected void setListener() {
        hometabRadio = (RadioGroup) findViewById(R.id.hometab_radio);
        hometabRadio.setOnCheckedChangeListener(this);
        tvRight.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                if (!userId.equals("")) {
                    startActivity(new Intent(HomeActivity.this, MessageActivity.class));
                } else {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                toolbarTitle.setText("首页");
                controller.showFragment(0);
                tvRight.setVisibility(View.GONE);
                break;
            case R.id.rb_order:
                controller.showFragment(1);
                toolbar.setVisibility(View.GONE);
                toolbarTitle.setText("购物车");
                tvRight.setVisibility(View.GONE);
                break;
            case R.id.rb_water_ticket:
                controller.showFragment(2);
                toolbar.setVisibility(View.VISIBLE);
                toolbarTitle.setText("水票");
                tvRight.setVisibility(View.GONE);
                break;
            case R.id.rb_me:
                controller.showFragment(3);
                toolbar.setVisibility(View.VISIBLE);
                toolbarTitle.setText("我的");
                tvRight.setVisibility(View.VISIBLE);
                tvRight.setText("消息");
                break;
            default:
                break;
        }
    }


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_home);
        controller = FragmentController.getInstance(this, R.id.frame_layout);
        controller.showFragment(0);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("首页");

    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    private boolean isDoubleClick = false;

    @Override
    public void onBackPressed() {
        if (isDoubleClick) {
            super.onBackPressed();
            App.getInstance().exit();
        } else {
            ToastManager.show("再次点击一次退出程序");
            isDoubleClick = true;
            UIThread.getInstance().postDelay(new Runnable() {
                @Override
                public void run() {
                    isDoubleClick = false;
                }
            }, 1000);
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.onDestroy();
    }

}
