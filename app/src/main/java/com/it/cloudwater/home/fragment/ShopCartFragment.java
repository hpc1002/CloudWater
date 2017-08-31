package com.it.cloudwater.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.adapter.RvItemAdapter;
import com.it.cloudwater.base.BaseFragment;
import com.it.cloudwater.bean.ShopCartListBean;
import com.it.cloudwater.callback.OnItemListener;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hpc on 2017/8/16.
 */

public class ShopCartFragment extends BaseFragment {
    @BindView(R.id.cart_recycler)
    EasyRecyclerView cartRecycler;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll;
    @BindView(R.id.lly_menu)
    LinearLayout llyMenu;
    Unbinder unbinder;
    private String userId;
    private ArrayList<ShopCartListBean.Result.DataList> cartLists;
    private RecyclerArrayAdapter<ShopCartListBean.Result.DataList> shopCartAdapter;
    RvItemAdapter mRvItemAdapter;
    //记录选择的Item
    private HashSet<Integer> positionSet;
    //记录Menu的状态
    private boolean isShow;
    private boolean isSelectAll;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_shop_cart, container, false);
    }

    @Override
    protected void initListener() {
        cartRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        userId = StorageUtil.getUserId(getActivity());
        if (!userId.equals("")) {
            CloudApi.getShopList(0x001, Long.parseLong(userId), myCallBack);
        } else {
            ToastManager.show("未登录");
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
                            ShopCartListBean shopCartListBean = new Gson().fromJson(body, ShopCartListBean.class);
                            cartLists = new ArrayList<>();
                            for (int i = 0; i < shopCartListBean.result.dataList.size(); i++) {
                                cartLists.add(shopCartListBean.result.dataList.get(i));
                            }
                            initUi(cartLists);
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

    private void initUi(ArrayList<ShopCartListBean.Result.DataList> cartLists) {
//        cartRecycler.setAdapterWithProgress(shopCartAdapter = new RecyclerArrayAdapter<ShopCartListBean.Result.DataList>(getActivity()) {
//            @Override
//            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//                return new ShopCartViewHolder(parent);
//            }
//
//        });
//        shopCartAdapter.addAll(cartLists);
        mRvItemAdapter = new RvItemAdapter(getActivity(), cartLists);
        cartRecycler.setAdapter(mRvItemAdapter);
        positionSet = new HashSet<>();
        setListener();
    }

    private void setListener() {

        mRvItemAdapter.setOnItemListener(new OnItemListener() {

            @Override
            public void checkBoxClick(int position) {
                //已经有Item被选择,执行添加或删除操作
                addOrRemove(position);
            }

            @Override
            public void onItemClick(View view, int position) {
                //触发Item的单击事件
                Toast.makeText(getActivity(), String.format(getString(R.string.hint), position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isShow) {
                    isShow = true;
                    llyMenu.animate()
                            .alpha(1.0f)
                            .translationYBy(-llyMenu.getHeight())
                            .setDuration(500).start();
                    ((CheckBox) view.findViewById(R.id.cb_select)).setChecked(true);
                    cartLists.get(position).isSelect = true;
                    positionSet.add(position);
                }
            }
        });

    }

    /**
     * 操作Item记录集合
     */
    private void addOrRemove(int position) {
        if (positionSet.contains(position)) {
            // 如果包含，则撤销选择
            Log.e("----", "remove");
            positionSet.remove(position);
        } else {
            // 如果不包含，则添加
            Log.e("----", "add");
            positionSet.add(position);
        }

        if (positionSet.size() == 0) {
            isShow = false;
            llyMenu.animate()
                    .alpha(0.0f)
                    .translationYBy(llyMenu.getHeight())
                    .setDuration(500).start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
