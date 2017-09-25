package com.it.cloudwater.user;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.user.fragment.DistributionFragment;
import com.it.cloudwater.user.fragment.UnDistributionFragment;
import com.it.cloudwater.widget.SmartTab.SmartTabLayout;
import com.it.cloudwater.widget.SmartTab.UtilsV4.v4.FragmentPagerItem;
import com.it.cloudwater.widget.SmartTab.UtilsV4.v4.FragmentPagerItemAdapter;
import com.it.cloudwater.widget.SmartTab.UtilsV4.v4.FragmentPagerItems;

import butterknife.BindView;

public class Distribution2Activity extends BaseActivity {

    @BindView(R.id.tab)
    FrameLayout tab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
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

    @Override
    protected void processLogic() {

    }


    @Override
    protected void setListener() {
        toolbarTitle.setText("我的配送");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tab.addView(LayoutInflater.from(this).inflate(R.layout.tab_top_layout, tab, false));
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        FragmentPagerItems pages = new FragmentPagerItems(this);
        pages.add(FragmentPagerItem.of("未配送", UnDistributionFragment.class));
        pages.add(FragmentPagerItem.of("已配送", DistributionFragment.class));
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewpager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewpager);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_distribution2);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }


}
