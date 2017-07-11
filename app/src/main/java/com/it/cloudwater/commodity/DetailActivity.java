package com.it.cloudwater.commodity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DetailActivity extends BaseActivity {

    private String goodsLid;
    private static final String TAG = "DetailActivity";

    @Override
    protected void processLogic() {
        goodsLid = getIntent().getStringExtra("goodsLid");
        CloudApi.getGoodsDetailData(0x001, Long.parseLong(goodsLid), myCallBack);
    }

    @Override
    protected void setListener() {
        Button mOrdersSubmit = (Button) findViewById(R.id.order_submit);
        mOrdersSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this, SubmitOrderActivity.class));
            }
        });
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    /**
     * {
     * "result": {
     * "lId": 1,
     * "strGoodsname": "桶装水1",
     * "strGoodsimgurl": "1.jpg",
     * "strStandard": "1/每箱",
     * "strRemarks": "说明文字",
     * "nPrice": 220,
     * "nMothnumber": 40,
     * "strIntroduce": "是的范德萨",
     * "nStock": 100,
     * "nOnline": 1
     * },
     * "resCode": "0"
     * }
     */
    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, String data) {
            switch (what) {
                case 0x001:
                    Log.i(TAG, "onSuccess: ----" + data);
                    parseDataAndShow(data);
                    break;
            }
        }

        @Override
        public void onSuccessList(int what, List results) {

        }

        @Override
        public void onFail(int what, Object result) {

        }
    };

    private void parseDataAndShow(String data) {
        try {
            JSONObject detailData = new JSONObject(data);
            String resCode = detailData.getString("resCode");
            if (resCode.equals("0")) {
                JSONObject result = detailData.getJSONObject("result");
                String strGoodsname = result.getString("strGoodsname");
                String strGoodsimgurl = result.getString("strGoodsimgurl");
                String strStandard = result.getString("strStandard");
                String strRemarks = result.getString("strRemarks");
                String strIntroduce = result.getString("strIntroduce");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
