package com.it.cloudwater.home.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseFragment;
import com.it.cloudwater.bean.BuyTicketListBean;
import com.it.cloudwater.constant.Constant;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.user.TicketDetailActivity;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.it.cloudwater.viewholder.BuyTicketViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by hpc on 2017/6/19.
 */

public class BuyTicketFragment extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener {

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private ArrayList<BuyTicketListBean.Result.DataList> dataLists;
    private RecyclerArrayAdapter<BuyTicketListBean.Result.DataList> ticketAdapter;
    private TextView water_name;
    private TextView water_sale;
    private TextView water_discount;
    private TextView tv_price;
    private TextView ori_price;
    private TextView buy;
    private ImageView ticketImg;
    private int nTotal;
    private String userId;
    private static final int REFRESH_COMPLETE = 0X110;
    private static final int SWIPE_REFRESH_COMPLETE = 0X111;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    recyclerView.setRefreshing(false);
                    break;
                case SWIPE_REFRESH_COMPLETE:
//                    swipeRefresh.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fr_tick_buy, container, false);
    }

    @Override
    protected void initListener() {
        userId=StorageUtil.getUserId(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
            CloudApi.getBuyTicketList(0x001, 1, 8, myCallBack);
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    String body = result.body();
                    try {
                        dataLists = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(body);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("1")) {
                            String result1 = jsonObject.getString("result");
                            ToastManager.show(result1);
                        } else if (resCode.equals("0")) {
                            BuyTicketListBean myTicketListBean = new Gson().fromJson(body, BuyTicketListBean.class);
                            nTotal=myTicketListBean.result.nTotal;

                            for (int i = 0; i < myTicketListBean.result.dataList.size(); i++) {
                                dataLists.add(myTicketListBean.result.dataList.get(i));

                            }
                            initUi(dataLists);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x002:

                    break;
            }
        }

        @Override
        public void onFail(int what, Response<String> result) {

        }
    };

    private void initUi(ArrayList<BuyTicketListBean.Result.DataList> dataLists) {

        recyclerView.setAdapterWithProgress(ticketAdapter = new RecyclerArrayAdapter<BuyTicketListBean.Result.DataList>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                BuyTicketViewHolder buyTicketViewHolder = new BuyTicketViewHolder(parent, R.layout.item_water_ticket) {
                    @Override
                    public void setData(final BuyTicketListBean.Result.DataList data) {
                        water_name = $(R.id.water_name);
                        ticketImg = $(R.id.iv_ticket);
                        water_sale = $(R.id.water_sale);
                        water_discount = $(R.id.water_discount);
                        tv_price = $(R.id.tv_price);
                        ori_price = $(R.id.ori_price);
                        buy = $(R.id.buy);
                        water_name.setText(data.strGoodsName);
                        water_sale.setText("月销量" + data.nMonthCount);
                        water_discount.setText(data.strRemarks);
                        tv_price.setText("￥" + ((double) data.nPrice / 100));
                        ori_price.setText("原价￥" + ((double) data.nOldPrice / 100));
                        Glide.with(getContext())
                                .load(Constant.IMAGE_URL + "0/" + data.lGoodsid)
                                .placeholder(R.mipmap.home_load_error)
                                .bitmapTransform(new CenterCrop(getContext()))
                                .into(ticketImg);
                        buy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (userId.equals("")){
                                    ToastManager.show("请先登录");
                                    return;
                                }
                                Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                                intent.putExtra("ticketId", data.lId + "");
                                startActivity(intent);

                            }
                        });
                    }
                };
                return buyTicketViewHolder;
            }

        });
        ticketAdapter.addAll(dataLists);
        mHandler.sendEmptyMessage(REFRESH_COMPLETE);
        ticketAdapter.setNoMore(R.layout.view_nomore);
        ticketAdapter.setMore(R.layout.view_more, this);
    }

    int page = 1;

    @Override
    public void onLoadMore() {

            if (page < (nTotal / 8 + 1)) {
                page++;
                CloudApi.getBuyTicketList(0x001, 1, 8 * page, myCallBack);
            } else {
                ticketAdapter.stopMore();
            }
    }
}
