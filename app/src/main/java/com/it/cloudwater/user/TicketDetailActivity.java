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
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.viewholder.TicketDetailListViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;

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

    @Override
    protected void processLogic() {
        CloudApi.getTicketDetail(0x001, Long.parseLong(ticketId), myCallBack);
    }

    @Override
    protected void setListener() {
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
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                       /*
                {
                    "result": {
                    "lId": 1,
                            "strGoodsName": "SFSFDS",
                            "dExpire": 1506787200000,
                            "ticketcontents": [
                    {
                        "lId": 1,
                            "nCount": 5,
                            "nPrice": 55,
                            "strRemarks": "买20赠送10"
                    }
        ],
                    "strGoodsimgurl": "img_url",
                            "nPrice": 111
                },
                    "resCode": "0"
                }
                */
                    String body = result.body();
                    TicketDetailBean ticketDetailBean = new Gson().fromJson(body, TicketDetailBean.class);
                    bucketName.setText(ticketDetailBean.result.strGoodsName);
                    Glide.with(TicketDetailActivity.this)
                            .load(ticketDetailBean.result.strGoodsimgurl)
                            .centerCrop()
                            .placeholder(R.mipmap.home_load_error)
                            .into(bucketImg);
                    bucketPrice.setText("原价" + ((double) ticketDetailBean.result.nPrice / 100) + "元/桶");
                    ArrayList<TicketDetailBean.Result.TicketContents> ticketContentses = new ArrayList<>();
                    for (int i = 0; i < ticketDetailBean.result.ticketcontents.size(); i++) {
                        ticketContentses.add(ticketDetailBean.result.ticketcontents.get(i));
                    }
                    eyRecyclerView.setAdapterWithProgress(ticketListAdapter = new RecyclerArrayAdapter<TicketDetailBean.Result.TicketContents>(TicketDetailActivity.this) {
                        @Override
                        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                            return new TicketDetailListViewHolder(parent);
                        }
                    });
                    ticketListAdapter.addAll(ticketContentses);
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
