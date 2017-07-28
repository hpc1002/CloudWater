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
import com.it.cloudwater.utils.ToastManager;
import com.lhalcyon.adapter.base.BaseViewHolder;
import com.lhalcyon.adapter.helper.BasicController;
import com.lhalcyon.adapter.helper.OnItemClickListener;

import java.util.ArrayList;

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

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_address);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }
}
