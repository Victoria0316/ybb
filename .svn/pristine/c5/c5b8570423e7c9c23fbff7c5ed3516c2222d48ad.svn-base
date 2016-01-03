package com.bluemobi.ybb.ps.util;

import com.bluemobi.ybb.ps.adapter.SoftModel;

import java.util.Comparator;

/**
 * Created by wangzhijun on 2015/7/22.
 */
public class PinyinComparator implements Comparator<SoftModel> {
    public int compare(SoftModel o1, SoftModel o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
