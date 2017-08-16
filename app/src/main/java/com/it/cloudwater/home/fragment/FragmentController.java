package com.it.cloudwater.home.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import java.util.ArrayList;

/**
 * Created by hpc on 2016/12/21.
 */

public class FragmentController {
    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private static FragmentController controller;
    private Bundle bundle;

    public static FragmentController getInstance(FragmentActivity activity, int containerId) {
//        if (controller == null) {
            controller = new FragmentController(activity, containerId);
//        }
        return controller;
    }

    public static void onDestroy() {
        controller = null;
    }

    public FragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    public void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new HomeFragment());//0
        fragments.add(new ShopCartFragment());//1
        fragments.add(new WaterTicketFragment());//2
        fragments.add(new MeFragment());//3
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position) {

        return fragments.get(position);
    }
}
