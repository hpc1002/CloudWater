package com.it.cloudwater.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseFragment;
import com.it.cloudwater.bean.TicketBean;
import com.it.cloudwater.constant.DataProvider;
import com.it.cloudwater.viewholder.MyTicketViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

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
    private RecyclerArrayAdapter<TicketBean> myTicketAdapter;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fr_tick_my, container, false);
    }

    @Override
    protected void initListener() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    protected void initData() {
        recyclerView.setAdapterWithProgress(myTicketAdapter= new RecyclerArrayAdapter<TicketBean>(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyTicketViewHolder(parent);
            }
        });
        myTicketAdapter.addAll(DataProvider.getMyTicketList());
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
