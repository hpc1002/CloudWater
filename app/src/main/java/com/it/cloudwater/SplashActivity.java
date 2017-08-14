package com.it.cloudwater;

import android.content.Context;
import android.content.Intent;


import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.home.HomeActivity;
import com.it.cloudwater.utils.StorageUtil;


public class SplashActivity extends BaseActivity {

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_splashy);
        ToMainUi();
    }
    @Override
    protected Context getActivityContext() {
        return this;
    }


    private void ToMainUi() {
        boolean isGuideShow = StorageUtil.getBoolean(SplashActivity.this, "is_guide_show", false);
        if (!isGuideShow) {// 如果没有展现过新手引导,跳引导页
//            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        }
    }

}
