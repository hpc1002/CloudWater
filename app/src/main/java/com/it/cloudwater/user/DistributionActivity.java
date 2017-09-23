package com.it.cloudwater.user;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.bean.OrderListBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.it.cloudwater.viewholder.ShopperListViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

public class DistributionActivity extends BaseActivity {

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
    @BindView(R.id.shopRecyclerView)
    EasyRecyclerView shopRecyclerView;
    private String userId;
    private Integer nState = 0;
    private RecyclerArrayAdapter<OrderListBean.Result.DataList> shopperAdapter;

    @Override
    protected void processLogic() {
        if (!userId.equals("")) {
            CloudApi.distributionList(0x001, 1, 10, Integer.parseInt(userId), nState, myCallBack);
        }
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
                            OrderListBean orderListBean = new Gson().fromJson(body, OrderListBean.class);
                            ArrayList<OrderListBean.Result.DataList> dataLists = new ArrayList<>();
                            for (int i = 0; i < orderListBean.result.dataList.size(); i++) {
                                dataLists.add(orderListBean.result.dataList.get(i));
                            }
                            initUi(dataLists);
                        } else if (resCode.equals("1")) {
                            ToastManager.show("暂无配送单");
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

    private void initUi(final ArrayList<OrderListBean.Result.DataList> dataLists) {
        shopRecyclerView.setAdapterWithProgress(shopperAdapter = new RecyclerArrayAdapter<OrderListBean.Result.DataList>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ShopperListViewHolder(parent);
            }

        });
        shopperAdapter.addAll(dataLists);

    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("我的配送");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        userId = StorageUtil.getUserId(this);
        shopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_distribution);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

}
