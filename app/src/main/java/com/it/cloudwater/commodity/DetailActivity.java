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
import com.bumptech.glide.signature.StringSignature;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.constant.Constant;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.user.LoginActivity;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.it.cloudwater.widget.button.AnimShopButton;
import com.it.cloudwater.widget.button.IOnAddDelListener;
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
    ImageView commodityIntroduction;
    @BindView(R.id.order_submit)
    Button orderSubmit;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private String goodsLid;
    private int goodCount;
    private static final String TAG = "DetailActivity";
    private int lId;
    private int nGoodstype;
    private String strGoodsname;
    private String strGoodsimgurl;
    private String strStandard;
    private String strRemarks;
    private String strIntroduce;
    private int nMothnumber;
    private int nPrice;
    private int nStock;
    private int nOnline;
    private String userId;
    private String body;

    @Override
    protected void processLogic() {


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
        goodsLid = getIntent().getStringExtra("goodsLid");
        userId = StorageUtil.getUserId(this);
        CloudApi.getGoodsDetailData(0x001, Long.parseLong(goodsLid), myCallBack);
        Button mOrdersSubmit = (Button) findViewById(R.id.order_submit);
        btnReplenish.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(int count) {
                goodCount = count;
            }

            @Override
            public void onAddFailed(int count, FailType failType) {
                goodCount = count;
            }

            @Override
            public void onDelSuccess(int count) {
                goodCount = count;
            }

            @Override
            public void onDelFaild(int count, FailType failType) {
                goodCount = count;
            }
        });
        mOrdersSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userId.equals("")) {
                    ToastManager.show("请先登录");
                    startActivity(new Intent(DetailActivity.this, LoginActivity.class));
                    return;
                }
                if (goodCount == 0) {
                    ToastManager.show("商品不能为空");
                    return;
                }
                //订单参数包装
                Map<String, Object> orderParams = new HashMap<>();
                orderParams.put("lGoodsid", goodsLid);
                orderParams.put("strGoodsname", strGoodsname);
                orderParams.put("nPrice", nPrice);
                orderParams.put("strGoodsimgurl", strGoodsimgurl);
                orderParams.put("nGoodsTotalPrice", nPrice * goodCount);
                orderParams.put("nCount", goodCount);
                JSONObject orderObject = new JSONObject(orderParams);
                ArrayList<JSONObject> orderGoods = new ArrayList<>();
                orderGoods.add(orderObject);
                Map<String, Object> params = new HashMap<>();
                params.put("lBuyerid", userId);
                params.put("strBuyername", "姓名");
                params.put("nTotalprice", nPrice * goodCount);
                params.put("orderGoods", orderGoods);
                params.put("nAddOrderType", 0);
                JSONObject jsonObject = new JSONObject(params);
                CloudApi.orderSubmit(0x002, jsonObject, myCallBack);

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

                    body = data.body();
                    progressBar.setVisibility(View.GONE);
                    Log.i(TAG, "onSuccess: ----" + data);
                    parseDataAndShow(body);
                    break;
                case 0x002:
                    String body2 = data.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body2);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            int orderId = jsonObject.getInt("result");//订单id
                            Intent intent = new Intent(DetailActivity.this, SubmitOrderActivity.class);
                            intent.putExtra("order_Id", orderId + "");
                            startActivity(intent);
                        } else if (resCode.equals("1")) {
                            String result = jsonObject.getString("result");
                            ToastManager.show(result);
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

    private void parseDataAndShow(String data) {
        try {
            JSONObject detailData = new JSONObject(data);
            String resCode = detailData.getString("resCode");
            if (resCode.equals("0")) {
                JSONObject result = detailData.getJSONObject("result");
                lId = result.getInt("lId");
                nGoodstype = result.getInt("nGoodstype");
                strGoodsname = result.getString("strGoodsname");
                strGoodsimgurl = result.getString("strGoodsimgurl");
                strStandard = result.getString("strStandard");
                strRemarks = result.getString("strRemarks");
                strIntroduce = result.getString("strIntroduce");
                nMothnumber = result.getInt("nMothnumber");
                nPrice = result.getInt("nPrice");
                nStock = result.getInt("nStock");
                nOnline = result.getInt("nOnline");
                tradeName.setText(strGoodsname);
                specifications.setText("规格:" + strStandard);
                salesVolume.setText("已售" + nMothnumber);
                price.setText("￥" + ((double) nPrice / 100));
                Glide.with(this)
                        .load(Constant.IMAGE_URL + "0/" + lId+"?date="+System.currentTimeMillis())
                        .placeholder(R.mipmap.home_load_error)
                        .crossFade()
                        .signature(new StringSignature("03"))
                        .skipMemoryCache(true)
                        .into((ImageView) findViewById(R.id.commodity_pictures));
                Glide.with(this)
                        .load(Constant.IMAGE_URL + "2/" + lId+"?date="+System.currentTimeMillis())
                        .placeholder(R.mipmap.home_load_error)
                        .crossFade()
                        .signature(new StringSignature("04"))
                        .skipMemoryCache(true)
                        .into((ImageView) findViewById(R.id.commodity_introduction));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
