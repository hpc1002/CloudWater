package com.it.cloudwater.constant;


import com.it.cloudwater.bean.TicketBean;
import com.it.cloudwater.home.bean.BannerDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Jude on 2016/1/6.
 */
public class DataProvider {
    public static ArrayList<BannerDto> getPictures() {
        ArrayList<BannerDto> arrayList = new ArrayList<>();
        for (int i = 0; i < VIRTUAL_PICTURE.length; i++) {
            arrayList.add(VIRTUAL_PICTURE[i]);
        }
        return arrayList;
    }

    static final BannerDto[] VIRTUAL_PICTURE = {
            new BannerDto("", "http://a1.qpic.cn/psb?/V140CNnH1eRmTV/jUiOQqgKqPLIUwcDzSlOO50.laGh5RJ*GGU.RFB1KnE!/b/dBoBAAAAAAAA&bo=7gJ4AQAAAAADB7c!&rf=viewer_4", 1, 123),
    };

    public static List<TicketBean> getMyTicketList() {
        ArrayList<TicketBean> arr = new ArrayList<>();
        arr.add(new TicketBean("张三", "19910528", 3, "http://a1.qpic.cn/psb?/V140CNnH1eRmTV/*gP7hmI9fJzyWP6allHW1O1lSEcj*7fGRD7mJV3zjhs!/b/dCABAAAAAAAA&ek=1&kp=1&pt=0&bo=oACgAAAAAAAFFzQ!&tm=1487833200&sce=60-2-2&rf=viewer_4"));
        arr.add(new TicketBean("里斯", "19910528432", 5, "http://a1.qpic.cn/psb?/V140CNnH1eRmTV/fo6whuOeXKybGWdLKtowW.F60pOSPBo.Bkp70S9ofd0!/b/dCABAAAAAAAA&bo=oACgAAAAAAAFByQ!&rf=viewer_4"));
        arr.add(new TicketBean("shabii", "19910528432", 6, "http://a3.qpic.cn/psb?/V140CNnH1eRmTV/MJZ.*zcUXu5yzs98zPFnAuLcNSH36M8GwtXtBVwHVKI!/b/dB8BAAAAAAAA&bo=oACgAAAAAAAFACM!&rf=viewer_4"));
        arr.add(new TicketBean("dasdsa", "19910528432", 4534, "http://a1.qpic.cn/psb?/V140CNnH1eRmTV/3HkjJperATyAwEDGX8WQwFV1aY*a1ijHkSIluhjRQTE!/b/dCABAAAAAAAA&bo=oACgAAAAAAAFACM!&rf=viewer_4"));
        return arr;
    }

//    static final int[] NarrowImage = {
//            R.mipmap.yy01,
//            R.mipmap.yy02,
//            R.mipmap.yy03,
//            R.mipmap.yy04,
//            R.mipmap.yy05,
//            R.mipmap.yy06,
//            R.mipmap.yy07,
//            R.mipmap.yy08,
//            R.mipmap.yy09,
//    };

//    public static ArrayList<Integer> getNarrowImage(int page) {
//        ArrayList<Integer> arrayList = new ArrayList<>();
//        if (page == 4) return arrayList;
//
//        for (int i = 0; i < NarrowImage.length; i++) {
//            arrayList.add(NarrowImage[i]);
//        }
//        return arrayList;
//    }
}
