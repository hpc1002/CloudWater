package com.it.cloudwater.constant;


import com.it.cloudwater.home.bean.BannerDto;

import java.util.ArrayList;

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
            new BannerDto("翻转课堂", "http://o84n5syhk.bkt.clouddn.com/57154327_p0.png", 1, 123),
            new BannerDto("ps实战", "http://o84n5syhk.bkt.clouddn.com/57180221_p0.jpg", 2, 124),
            new BannerDto("网页设计", "http://o84n5syhk.bkt.clouddn.com/57174070_p0.jpg", 3, 125),
            new BannerDto("职业礼仪", "http://o84n5syhk.bkt.clouddn.com/57166531_p0.jpg", 4, 126),
            new BannerDto("大数据时代", "http://o84n5syhk.bkt.clouddn.com/57151022_p0.jpg", 5, 127),
            new BannerDto("趣味课堂", "http://o84n5syhk.bkt.clouddn.com/57172236_p0.jpg", 6, 127),
    };
//    public static List<LiveCourse> getCourseList() {
//        ArrayList<LiveCourse> arr = new ArrayList<>();
//        arr.add(new LiveCourse(1, 11, "微积分", 888, "http://a1.qpic.cn/psb?/V140CNnH1eRmTV/*gP7hmI9fJzyWP6allHW1O1lSEcj*7fGRD7mJV3zjhs!/b/dCABAAAAAAAA&ek=1&kp=1&pt=0&bo=oACgAAAAAAAFFzQ!&tm=1487833200&sce=60-2-2&rf=viewer_4", "直播中"));
//        arr.add(new LiveCourse(2, 22, "线性代数领导就是哪家啊看看三的撒鸡冻撒", 6456, "http://a1.qpic.cn/psb?/V140CNnH1eRmTV/fo6whuOeXKybGWdLKtowW.F60pOSPBo.Bkp70S9ofd0!/b/dCABAAAAAAAA&bo=oACgAAAAAAAFByQ!&rf=viewer_4", "直播中"));
//        arr.add(new LiveCourse(3, 33, "高等数学", 324, "http://a3.qpic.cn/psb?/V140CNnH1eRmTV/MJZ.*zcUXu5yzs98zPFnAuLcNSH36M8GwtXtBVwHVKI!/b/dB8BAAAAAAAA&bo=oACgAAAAAAAFACM!&rf=viewer_4", "直播中"));
//        arr.add(new LiveCourse(4, 44, "工程制图", 4534, "http://a1.qpic.cn/psb?/V140CNnH1eRmTV/3HkjJperATyAwEDGX8WQwFV1aY*a1ijHkSIluhjRQTE!/b/dCABAAAAAAAA&bo=oACgAAAAAAAFACM!&rf=viewer_4", "直播中"));
//        return arr;
//    }

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
