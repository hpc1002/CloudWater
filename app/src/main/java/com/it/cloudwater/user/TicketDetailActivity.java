package com.it.cloudwater.user;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.bean.TicketDetailBean;
import com.it.cloudwater.constant.Constant;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
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
import butterknife.ButterKnife;

public class TicketDetailActivity extends BaseActivity {

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
    private String ticketId;
    private RecyclerArrayAdapter<TicketDetailBean.Result.TicketContents> ticketListAdapter;
    private String userId;
    private TicketDetailBean ticketDetailBean;

    private TicketDetailBean.Result.TicketContents TicketDatas;

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
        toPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> ticketParams = new HashMap<>();
                ticketParams.put("lUserid", userId);
                ticketParams.put("lTicketConId", TicketDatas.lId);
                ticketParams.put("nFactPrice", TicketDatas.nPrice);
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
                            .load(Constant.IMAGE_URL + "0/" + ticketDetailBean.result.lId)
                            .centerCrop()
                            .placeholder(R.mipmap.home_load_error)
                            .into(bucketImg);
                    bucketPrice.setText("￥" + ((double) ticketDetailBean.result.nPrice / 100));
                    ArrayList<TicketDetailBean.Result.TicketContents> ticketContentses = new ArrayList<>();
                    for (int i = 0; i < ticketDetailBean.result.ticketcontents.size(); i++) {
                        ticketContentses.add(ticketDetailBean.result.ticketcontents.get(i));
                    }
                    eyRecyclerView.setAdapterWithProgress(ticketListAdapter = new RecyclerArrayAdapter<TicketDetailBean.Result.TicketContents>(TicketDetailActivity.this) {
                        @Override
                        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                            TicketDetailListViewHolder ticketDetailListViewHolder = new TicketDetailListViewHolder(parent);
                            ticketDetailListViewHolder.setCallBack(new TicketDetailListViewHolder.allCheck() {
                                @Override
                                public void OnItemClickListener(TicketDetailBean.Result.TicketContents data) {
                                    TicketDatas = data;
                                    totalPrice.setText("￥" + ((double) data.nPrice / 100));
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
                            //请求支付接口
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
        setContentView(R.layout.activity_ticket_detail);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
