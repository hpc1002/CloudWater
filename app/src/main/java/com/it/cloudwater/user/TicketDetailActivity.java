package com.it.cloudwater.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.bean.TicketDetailBean;
import com.it.cloudwater.constant.Constant;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.pay.PayActivity;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.viewholder.TicketDetailListViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class TicketDetailActivity extends BaseActivity {
    private static final int REQUEST_CODE3 = 0x003;
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
    @BindView(R.id.bucket_name)
    TextView bucketName;
    @BindView(R.id.bucket_price)
    TextView bucketPrice;
    @BindView(R.id.eyRecyclerView)
    EasyRecyclerView eyRecyclerView;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.to_pay)
    TextView toPay;
    @BindView(R.id.bucket_img)
    ImageView bucketImg;
    @BindView(R.id.tv_coupon_my)
    TextView tvCouponMy;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.rl_coupon_my)
    RelativeLayout rlCouponMy;
    private String ticketId;
    private RecyclerArrayAdapter<TicketDetailBean.Result.TicketContents> ticketListAdapter;
    private String userId;
    private TicketDetailBean ticketDetailBean;
    ArrayList<CheckBox> ticketContentsesCheckBox = new ArrayList<CheckBox>();
    private TicketDetailBean.Result.TicketContents TicketDatas;
    private String discount_amount;
//    private  int allPrice=0;
//    ArrayList<TicketDetailBean.Result.TicketContents> ticketContentsesCheck = new ArrayList<TicketDetailBean.Result.TicketContents>();
    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        userId = StorageUtil.getUserId(this);
        toolbarTitle.setText("水票详情");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ticketId = getIntent().getStringExtra("ticketId");
        eyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CloudApi.getTicketDetail(0x001, Long.parseLong(ticketId), myCallBack);
        totalPrice.setText("￥" + 0);
//        rlCouponMy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TicketDetailActivity.this, CouponActivity.class);
//                intent.putExtra("nFullPrice", ticketDetailBean.result.nPrice + "");
//                startActivityForResult(intent, REQUEST_CODE3);
//            }
//        });
        toPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> ticketParams = new HashMap<>();
                ticketParams.put("lUserid", userId);
                if (TicketDatas.lId > 0) {
                    ticketParams.put("lTicketConId", TicketDatas.lId);
                }
                if (discount_amount != null) {
                    ticketParams.put("nFactPrice", TicketDatas.nPrice - Integer.parseInt(discount_amount));
                } else {
                    ticketParams.put("nFactPrice", TicketDatas.nPrice);
                }

                ticketParams.put("nCount", TicketDatas.nCount);

                JSONObject ticketObject = new JSONObject(ticketParams);
                CloudApi.buyTicket(0x002, ticketObject, myCallBack);
            }
        });
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    String body = result.body();
                    ticketDetailBean = new Gson().fromJson(body, TicketDetailBean.class);
                    bucketName.setText(ticketDetailBean.result.strGoodsName);
                    Glide.with(TicketDetailActivity.this)
                            .load(Constant.IMAGE_URL + "0/" + ticketDetailBean.result.lGoodsid)
                            .centerCrop()
                            .placeholder(R.mipmap.home_load_error)
                            .into(bucketImg);
                    bucketPrice.setText("￥" + ((double) ticketDetailBean.result.nPrice / 100));
                    ArrayList<TicketDetailBean.Result.TicketContents> ticketContentses = new ArrayList<>();
                    for (int i = 0; i < ticketDetailBean.result.ticketcontents.size(); i++) {
                        ticketDetailBean.result.ticketcontents.get(i).setIndexNum(i);
                        ticketContentses.add(ticketDetailBean.result.ticketcontents.get(i));
                    }
                    eyRecyclerView.setAdapterWithProgress(ticketListAdapter = new RecyclerArrayAdapter<TicketDetailBean.Result.TicketContents>(TicketDetailActivity.this) {
                        @Override
                        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                            TicketDetailListViewHolder ticketDetailListViewHolder = new TicketDetailListViewHolder(parent,ticketContentsesCheckBox);
                            ticketDetailListViewHolder.setCallBack(new TicketDetailListViewHolder.allCheck() {
                                @Override
                                public void OnItemClickListener(final TicketDetailBean.Result.TicketContents data,boolean isChecked) {
//                                    allPrice=0;
//                                    if (isChecked){
//                                        ticketContentsesCheck.add(data);
//
//                                    }else{
//                                        for (int i=0;i<ticketContentsesCheck.size();i++){
//                                            TicketDetailBean.Result.TicketContents checkData = ticketContentsesCheck.get(i);
//                                            if (checkData.lId==data.lId){
//                                                ticketContentsesCheck.remove(i);
//                                            }
//                                        }
//
//                                    }
//
//                                    for (int i=0;i<ticketContentsesCheck.size();i++){
//                                        allPrice+=ticketContentsesCheck.get(i).nPrice;
//                                    }
                                    int indexNum=data.indexNum;//获取数据所在集合的索引即checkbox所在集合索引
                                    for (int i = 0; i < ticketContentsesCheckBox.size(); i++) {
                                        CheckBox checkBox=ticketContentsesCheckBox.get(i);
                                        if(i!=indexNum||!isChecked){//如果不是当前处理数据或者当前数据处理的isCheckked为false就取消选中
                                            checkBox.setChecked(false);
                                        }
                                    }

                                    if(isChecked){
                                        TicketDatas = data;
                                        totalPrice.setText("￥" + ((double) data.nPrice / 100));
                                    }else{
                                        TicketDatas = null;
                                        totalPrice.setText("￥0.00");
                                    }
//                                    TicketDatas = data;
//                                    totalPrice.setText("￥" + ((double) data.nPrice / 100));


                                    rlCouponMy.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(TicketDetailActivity.this, CouponActivity.class);
                                            intent.putExtra("nFullPrice", data.nPrice + "");
                                            startActivityForResult(intent, REQUEST_CODE3);
                                        }
                                    });
                                }
                            });
                            return ticketDetailListViewHolder;
                        }
                    });
                    ticketListAdapter.addAll(ticketContentses);
                    break;
                case 0x002:
                    String body2 = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body2);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            String ticketOrderId = jsonObject.getString("result");
                            Intent intent = new Intent(TicketDetailActivity.this, PayActivity.class);
                            intent.putExtra("orderId", ticketOrderId);
                            intent.putExtra("payType", "ticket");
                            startActivity(intent);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && REQUEST_CODE3 == requestCode) {

            discount_amount = data.getExtras().getString("discount_amount");
            tvDiscount.setText(((double) Integer.parseInt(discount_amount) / 100) + "元");
//            totalPrice.setText("￥" + ((double) (ticketDetailBean.result.nPrice - Integer.parseInt(discount_amount)) / 100));
            totalPrice.setText("￥" + ((double) (TicketDatas.nPrice - Integer.parseInt(discount_amount)) / 100));
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_ticket_detail);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

}
