package com.it.cloudwater.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * author hpc
 * 我的优惠券
 */
public class CouponActivity extends BaseActivity {

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
    @BindView(R.id.recycler_coupon)
    EasyRecyclerView recyclerCoupon;

    @Override
    protected void processLogic() {
        String userId = StorageUtil.getUserId(this);
        CloudApi.getMyCouponList(0x001, 1, 8, Integer.parseInt(userId), myCallBack);
    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("优惠券");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    String body = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("1")) {
                            String result1 = jsonObject.getString("result");
                            ToastManager.show(result1);
                        } else if (resCode.equals("0")) {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        @Override
        public void onFail(int what, Response<String> result) {

        }
    };
    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_coupon);

    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

}
