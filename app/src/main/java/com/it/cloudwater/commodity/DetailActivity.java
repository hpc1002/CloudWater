package com.it.cloudwater.commodity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.widget.button.AnimShopButton;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class DetailActivity extends BaseActivity {

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
    @BindView(R.id.commodity_pictures)
    ImageView commodityPictures;
    @BindView(R.id.trade_name)
    TextView tradeName;
    @BindView(R.id.sales_volume)
    TextView salesVolume;
    @BindView(R.id.specifications)
    TextView specifications;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.btnReplenish)
    AnimShopButton btnReplenish;
    @BindView(R.id.commodity_introduction)
    TextView commodityIntroduction;
    @BindView(R.id.order_submit)
    Button orderSubmit;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private String goodsLid;
    private static final String TAG = "DetailActivity";

    @Override
    protected void processLogic() {
        goodsLid = getIntent().getStringExtra("goodsLid");
        CloudApi.getGoodsDetailData(0x001, Long.parseLong(goodsLid), myCallBack);
    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("商品详情");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button mOrdersSubmit = (Button) findViewById(R.id.order_submit);
        mOrdersSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //订单参数包装
                Map<String, String> orderParams = new HashMap<>();
                orderParams.put("lGoodsid", "lGoodsid");
                orderParams.put("strGoodsname", "strGoodsname");
                orderParams.put("nPrice", "nPrice");
                orderParams.put("strGoodsimgurl", "strGoodsimgurl");
                orderParams.put("nGoodsTotalPrice", "nGoodsTotalPrice");
                orderParams.put("nCount", "nCount");
                JSONObject orderObject = new JSONObject(orderParams);
                ArrayList<JSONObject> orderGoods = new ArrayList<>();
                orderGoods.add(orderObject);
                Map<String, Object> params = new HashMap<>();
                params.put("lBuyerid", "lBuyerid");
                params.put("strBuyername", "strBuyername");
                params.put("nTotalprice", "nTotalprice");
                params.put("strInvoiceheader ", "strInvoiceheader ");
                params.put("strRemarks", "strRemarks");
                params.put("orderGoods", orderGoods);

                JSONObject jsonObject = new JSONObject(params);


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
        public void onSuccess(int what, Response<String> data) {
            switch (what) {
                case 0x001:
                    String body = data.body();
                    progressBar.setVisibility(View.GONE);
                    Log.i(TAG, "onSuccess: ----" + data);
                    parseDataAndShow(body);
                    break;
            }
        }

        @Override
        public void onFail(int what, Response<String> result) {

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
                int nMothnumber = result.getInt("nMothnumber");
                int nPrice = result.getInt("nPrice");
                tradeName.setText(strGoodsname);
                specifications.setText(strStandard);
                commodityIntroduction.setText(strIntroduce);
                salesVolume.setText("已售" + nMothnumber);
                price.setText(((double) nPrice / 100) + "元");
                Glide.with(this)
                        .load(strGoodsimgurl)
                        .placeholder(R.mipmap.home_load_error)
                        .crossFade()
                        .into((ImageView) findViewById(R.id.commodity_pictures));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
