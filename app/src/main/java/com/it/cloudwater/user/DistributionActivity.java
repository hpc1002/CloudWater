package com.it.cloudwater.user;

import android.content.Context;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.bean.OrderListBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.ArrayList;

public class DistributionActivity extends BaseActivity {

    private String userId;
    private Integer nState = 0;
    @Override
    protected void processLogic() {
        CloudApi.distributionList(0x001,1,10,Integer.parseInt(userId),nState,myCallBack);
    }
    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    String body = result.body();
                    break;
            }
        }

        @Override
        public void onFail(int what, Response<String> result) {

        }
    };
    @Override
    protected void setListener() {
        userId = StorageUtil.getUserId(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_distribution);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }
}
