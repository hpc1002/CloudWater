package com.it.cloudwater.home.fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseFragment;
import com.it.cloudwater.widget.SmartTab.SmartTabLayout;
import com.it.cloudwater.widget.SmartTab.UtilsV4.v4.FragmentPagerItem;
import com.it.cloudwater.widget.SmartTab.UtilsV4.v4.FragmentPagerItemAdapter;
import com.it.cloudwater.widget.SmartTab.UtilsV4.v4.FragmentPagerItems;

import butterknife.BindView;

/**
 * Created by hpc on 2017/6/16.
 */

public class WaterTicketFragment extends BaseFragment {

    @BindView(R.id.tab2)
    FrameLayout tab2;
    @BindView(R.id.viewpager2)
    ViewPager viewpager2;
    private View view;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_water_ticket, container, false);
        return view;
    }

    @Override
    protected void initListener() {
        tab2.addView(LayoutInflater.from(getActivity()).inflate(R.layout.tab_top_layout, tab2, false));
        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        FragmentPagerItems pages = new FragmentPagerItems(getActivity());
        pages.add(FragmentPagerItem.of("购买水票", BuyTicketFragment.class));
        pages.add(FragmentPagerItem.of("我的水票", MyTicketFragment.class));
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getActivity().getSupportFragmentManager(), pages);
        viewpager2.setAdapter(adapter);
        viewPagerTab.setViewPager(viewpager2);
    }

    @Override
    protected void initData() {

    }


}
