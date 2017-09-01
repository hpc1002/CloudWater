package com.it.cloudwater.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseFragment;
import com.it.cloudwater.bean.MyTicketListBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.it.cloudwater.viewholder.MyTicketListViewHolder;
import com.it.cloudwater.viewholder.MyTicketViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hpc on 2017/6/19.
 */

public class MyTicketFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    Unbinder unbinder;
    private String userId;
    private ArrayList<MyTicketListBean.Result.DataList> dataLists;
    private RecyclerArrayAdapter<MyTicketListBean.Result.DataList> ticketAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {

        userId = StorageUtil.getUserId(getActivity());
        return inflater.inflate(R.layout.fr_tick_my, container, false);
    }

    @Override
    protected void initListener() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    protected void initData() {
        recyclerView.setAdapterWithProgress(ticketAdapter = new RecyclerArrayAdapter<MyTicketListBean.Result.DataList>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyTicketViewHolder(parent);
            }
        });
        if (!userId.equals("")) {
            CloudApi.getMyTicketList(0x001, 1, 8, Integer.parseInt(userId), myCallBack);
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

        recyclerView.setAdapterWithProgress(ticketAdapter = new RecyclerArrayAdapter<MyTicketListBean.Result.DataList>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyTicketListViewHolder(parent);
            }

        });
        ticketAdapter.addAll(dataLists);
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
