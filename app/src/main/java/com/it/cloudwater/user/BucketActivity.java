package com.it.cloudwater.user;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 我的水桶
 */
public class BucketActivity extends BaseActivity {


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
    @BindView(R.id.bucket_count)
    TextView bucketCount;
    @BindView(R.id.re_back_money)
    TextView reBackMoney;
    @BindView(R.id.btn_re_back)
    Button btnReBack;
    private String userId;
    private int nBucketNum;

    @Override
    protected void processLogic() {
        userId = StorageUtil.getUserId(this);
        CloudApi.getMyBucket(0x001, Long.parseLong(userId), myCallBack);
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
                        if (resCode.equals("0")) {
                            JSONObject resultObject = jsonObject.getJSONObject("result");

                            nBucketNum = resultObject.getInt("nBucketNum");
                            int nBucketMoney = resultObject.getInt("nBucketMoney");
                            bucketCount.setText(nBucketNum + "");
                            reBackMoney.setText(((double) nBucketMoney / 100) + "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x002:
                    String body2 = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body2);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            ToastManager.show("退桶成功");
                            bucketCount.setText(0 + "");
                            reBackMoney.setText(0.00 + "");
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
    protected void setListener() {
        toolbarTitle.setText("我的水桶");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnReBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloudApi.retreatBucket(0x002, Long.parseLong(userId), "", nBucketNum, myCallBack);
            }
        });
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_bucket);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }
}
