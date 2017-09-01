package com.it.cloudwater.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.bean.CouponListBean;
import com.it.cloudwater.bean.MyTicketListBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.it.cloudwater.viewholder.CouponListViewHolder;
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
 * author hpc
 * 我的优惠券
 */
public class CouponActivity extends BaseActivity {

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
    @BindView(R.id.recycler_coupon)
    EasyRecyclerView recyclerCoupon;
    private ArrayList<CouponListBean.Result.DataList> dataLists;
    private RecyclerArrayAdapter<CouponListBean.Result.DataList> couponAdapter;
    @Override
    protected void processLogic() {
        String userId = StorageUtil.getUserId(this);
        CloudApi.getMyCouponList(0x001, 1, 8, Integer.parseInt(userId), myCallBack);
    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("优惠券");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerCoupon.setLayoutManager(new LinearLayoutManager(this));
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
                            CouponListBean couponListBean = new Gson().fromJson(body, CouponListBean.class);

                            dataLists = new ArrayList<>();
                            for (int i = 0; i < couponListBean.result.dataList.size(); i++) {
                                dataLists.add(couponListBean.result.dataList.get(i));
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

    private void initUi(final ArrayList<CouponListBean.Result.DataList> dataLists) {
        recyclerCoupon.setAdapterWithProgress(couponAdapter = new RecyclerArrayAdapter<CouponListBean.Result.DataList>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new CouponListViewHolder(parent);
            }

        });
        couponAdapter.addAll(dataLists);
        couponAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("discount_amount", dataLists.get(position).nPrice+"");
                CouponActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                CouponActivity.this.finish();
            }
        });
    }


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_coupon);

    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

}
