package com.it.cloudwater.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.adapter.CheckAdapter;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.bean.CheckBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.ToastManager;
import com.lhalcyon.adapter.base.BaseViewHolder;
import com.lhalcyon.adapter.helper.BasicController;
import com.lhalcyon.adapter.helper.OnItemClickListener;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 我的地址
 */
public class AddressActivity extends BaseActivity {

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
    @BindView(R.id.address_recycler)
    RecyclerView addressRecycler;
    private ArrayList<CheckBean> datalist;
    private CheckAdapter mAdapter;
    private static final String TAG = "AddressActivity";

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("我的地址");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("新增");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this,AddAddressActivity.class));
                ToastManager.show("新增地址");

            }
        });

        addressRecycler.setLayoutManager(new LinearLayoutManager(this));
        BasicController.BasicParams params = new BasicController.Builder()
                .checkId(R.id.checkbox)
                .choiceMode(BasicController.CHOICE_MODE_SINGLE)
                .layoutRes(R.layout.item_check)
                .build();
        datalist = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            datalist.add(new CheckBean("雪花女神龙", "盘丝洞" + i, i, true));
        }
        addressRecycler.setAdapter(mAdapter = new CheckAdapter(params, datalist) {
            @Override
            public boolean isItemChecked(CheckBean checkBean, int position) {
                return checkBean.isSingle;
            }
        });
        mAdapter.setOnItemClickListener(addressRecycler, new OnItemClickListener() {
            @Override
            public void onItemClick(BaseViewHolder vh, int position) {
                CheckBean checkedData = datalist.get(position);
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("result", "雪花女神龙");
                AddressActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                AddressActivity.this.finish();
                //设置返回数据
                ToastManager.show("position" + position + "iscClicked" + checkedData.address);
            }
        });
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:

                    break;
            }
        }

        @Override
        public void onFail(int what, Response<String> result) {

        }
    };

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_address);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }
}
