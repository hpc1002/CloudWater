package com.it.cloudwater.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.adapter.ShoppingCartAdapter;
import com.it.cloudwater.base.BaseFragment;
import com.it.cloudwater.bean.ShopCartListBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hpc on 2017/8/16.
 */

public class ShopCart2Fragment extends BaseFragment implements View.OnClickListener
        , ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface {

    @BindView(R.id.list_shopping_cart)
    ListView listShoppingCart;
    @BindView(R.id.ck_all)
    CheckBox ckAll;
    @BindView(R.id.tv_show_price)
    TextView tvShowPrice;
    @BindView(R.id.tv_settlement)
    TextView tvSettlement;
    @BindView(R.id.rl_bottom)
    LinearLayout rlBottom;
//    @BindView(R.id.btn_back)
//    Button btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.bt_header_right)
    TextView btHeaderRight;
    Unbinder unbinder;
    private String userId;
    private ArrayList<ShopCartListBean.Result.DataList> shoppingCartBeanList;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private ShoppingCartAdapter shoppingCartAdapter;
    private boolean flag = false;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        return inflater.inflate(R.layout.fragment_shop2_cart, container, false);
    }

    @Override
    protected void initListener() {
        shoppingCartAdapter = new ShoppingCartAdapter(getActivity());

        btHeaderRight.setOnClickListener(this);
        ckAll.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
//        btnBack.setOnClickListener(this);
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
                            shoppingCartBeanList = new ArrayList<>();
                            for (int i = 0; i < shopCartListBean.result.dataList.size(); i++) {
                                shoppingCartBeanList.add(shopCartListBean.result.dataList.get(i));
                            }
                            initUi(shoppingCartBeanList);
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
        shoppingCartAdapter.setCheckInterface(this);
        shoppingCartAdapter.setModifyCountInterface(this);
        listShoppingCart.setAdapter(shoppingCartAdapter);
        shoppingCartAdapter.setShoppingCartBeanList(cartLists);

    }


    /**
     * 结算订单、支付
     */
    private void lementOnder() {
        //选中的需要提交的商品清单

        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            Map<String, Object> orderParams = new HashMap<>();
            boolean isChoosed = shoppingCartBeanList.get(i).isChoosed;
            if (isChoosed) {
                orderParams.put("lGoodsid", shoppingCartBeanList.get(i).lGoodsId);
                orderParams.put("strGoodsname", shoppingCartBeanList.get(i).strGoodsname);
                orderParams.put("nPrice", shoppingCartBeanList.get(i).nPrice);
                orderParams.put("strGoodsimgurl", shoppingCartBeanList.get(i).strGoodsimgurl);
                orderParams.put("nGoodsTotalPrice", shoppingCartBeanList.get(i).nPrice * shoppingCartBeanList.get(i).nGoodsCount);
                orderParams.put("nCount", shoppingCartBeanList.get(i).nGoodsCount);
                maps.add(orderParams);
            }
        }
//        for (ShopCartListBean.Result.DataList bean : shoppingCartBeanList) {
//            boolean choosed = bean.isChoosed();
//            if (choosed) {
//                String shoppingName = bean.strGoodsname;
//                int count = bean.nGoodsCount;
//                double price = bean.nPrice;
//                String attribute = bean.strStandard;
//                int id = bean.lId;
//                orderParams.put("lGoodsid", bean.lGoodsId);
//                orderParams.put("strGoodsname", bean.strGoodsname);
//                orderParams.put("nPrice", bean.nPrice);
//                orderParams.put("strGoodsimgurl", bean.strGoodsimgurl);
//                orderParams.put("nGoodsTotalPrice", bean.nPrice * bean.nGoodsCount);
//                orderParams.put("nCount", bean.nGoodsCount);
//                maps.add(orderParams);
//            }
//
//        }
        ToastManager.show("总价：" + totalPrice / 100);
        //跳转到支付界面
        //订单参数包装


        Map<String, Object> params = new HashMap<>();
        params.put("lBuyerid", userId);
        params.put("strBuyername", "姓名");
        params.put("nTotalprice", totalPrice);
        params.put("orderGoods", maps);

        JSONObject jsonObject = new JSONObject(params);
        CloudApi.orderSubmit(0x002, jsonObject, myCallBack);
    }

    /**
     * 单选
     *
     * @param position  组元素位置
     * @param isChecked 组元素选中与否
     */
    @Override
    public void checkGroup(int position, boolean isChecked) {
        shoppingCartBeanList.get(position).setChoosed(isChecked);
        if (isAllCheck())
            ckAll.setChecked(true);
        else
            ckAll.setChecked(false);
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }

    /**
     * 遍历list集合
     *
     * @return
     */
    private boolean isAllCheck() {

        for (ShopCartListBean.Result.DataList group : shoppingCartBeanList) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }

    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            ShopCartListBean.Result.DataList shoppingCartBean = shoppingCartBeanList.get(i);
            if (shoppingCartBean.isChoosed()) {
                totalCount++;
                totalPrice += shoppingCartBean.nPrice * shoppingCartBean.nGoodsCount;
            }
        }
        tvShowPrice.setText("合计:" + ((double) totalPrice / 100) + "元");
        tvSettlement.setText("结算(" + totalCount + ")");
    }

    /**
     * 增加
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        ShopCartListBean.Result.DataList shoppingCartBean = shoppingCartBeanList.get(position);
        int currentCount = shoppingCartBean.nGoodsCount;
        currentCount++;
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }

    /**
     * 删减
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        ShopCartListBean.Result.DataList shoppingCartBean = shoppingCartBeanList.get(position);
        int currentCount = shoppingCartBean.getCount();
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }

    /**
     * 删除
     *
     * @param position
     */
    @Override
    public void childDelete(int position) {
        shoppingCartBeanList.remove(position);
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }


    @Override
    public void onClick(View v) {
        if (userId.equals("")) {
            return;
        }
        switch (v.getId()) {
            //全选按钮
            case R.id.ck_all:

                if (shoppingCartBeanList.size() != 0) {
                    if (ckAll.isChecked()) {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(true);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(false);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    }
                }
                statistics();
                break;
            case R.id.bt_header_right:
                flag = !flag;
                if (flag) {
                    btHeaderRight.setText("完成");
                    shoppingCartAdapter.isShow(false);
                } else {
                    btHeaderRight.setText("编辑");
                    shoppingCartAdapter.isShow(true);
                }
                break;
            case R.id.tv_settlement: //结算
                lementOnder();
                break;
//            case R.id.btn_back:
//                getActivity().finish();
//                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!userId.equals("")) {
            CloudApi.getShopList(0x001, Long.parseLong(userId), myCallBack);
        } else {
            ToastManager.show("未登录");
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
