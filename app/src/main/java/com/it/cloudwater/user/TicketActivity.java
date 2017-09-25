package com.it.cloudwater.user;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.bean.MyTicketListBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.it.cloudwater.viewholder.MyTicketListViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 我的水票
 */
public class TicketActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener {


    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
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
    private String userId;
    private ArrayList<MyTicketListBean.Result.DataList> dataLists;
    private RecyclerArrayAdapter<MyTicketListBean.Result.DataList> ticketAdapter;
    private int nTotal;
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
    protected void processLogic() {
        if (!userId.equals("")) {
            CloudApi.getMyTicketList(0x001, 1, 8, Integer.parseInt(userId), myCallBack);
        }
    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("我的水票");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userId = StorageUtil.getUserId(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                            MyTicketListBean myTicketListBean = new Gson().fromJson(body, MyTicketListBean.class);

                            dataLists = new ArrayList<>();
                            for (int i = 0; i < myTicketListBean.result.dataList.size(); i++) {
                                dataLists.add(myTicketListBean.result.dataList.get(i));

                            }
                            initUi(dataLists);
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

    private void initUi(ArrayList<MyTicketListBean.Result.DataList> dataLists) {

        recyclerView.setAdapterWithProgress(ticketAdapter = new RecyclerArrayAdapter<MyTicketListBean.Result.DataList>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyTicketListViewHolder(parent, TicketActivity.this);
            }

        });
        ticketAdapter.addAll(dataLists);
        mHandler.sendEmptyMessage(REFRESH_COMPLETE);
        ticketAdapter.setNoMore(R.layout.view_nomore);
        ticketAdapter.setMore(R.layout.view_more, this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_ticket);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    int page = 1;

    @Override
    public void onLoadMore() {
        if (!userId.equals("")) {

            if (page < (nTotal / 8 + 1)) {
                page++;
                CloudApi.getMyTicketList(0x001, 1, 8 * page, Integer.parseInt(userId), myCallBack);
            } else {
                ticketAdapter.stopMore();
            }
        }
    }
}
