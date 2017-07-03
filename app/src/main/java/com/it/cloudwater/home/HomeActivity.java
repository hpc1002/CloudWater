package com.it.cloudwater.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
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
import com.it.cloudwater.user.MessageActivity;
import com.it.cloudwater.utils.ToastManager;
import com.it.cloudwater.utils.UIThread;

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

    @Override
    protected void processLogic() {

    }


    @Override
    protected void setListener() {
        hometabRadio.setOnCheckedChangeListener(this);
        tvRight.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                startActivity(new Intent(HomeActivity.this, MessageActivity.class));
                break;
          default:
              break;
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                controller.showFragment(0);
                toolbarTitle.setText("首页");
                tvRight.setVisibility(View.GONE);
                break;
            case R.id.rb_order:
                controller.showFragment(1);
                toolbar.setVisibility(View.VISIBLE);
                toolbarTitle.setText("订单");
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
    protected void onDestroy() {
        super.onDestroy();
        FragmentController.onDestroy();
    }
}
