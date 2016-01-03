package com.bluemobi.ybb.util;

import com.bluemobi.ybb.db.entity.DateText;

import java.util.Comparator;

/**
 * Created by gaoyn on 2015/7/10.
 *
 * 比较日期戳的工具类
 *
 */
public class DateComparator implements Comparator<DateText> {
    @Override
    public int compare(DateText lhs, DateText rhs) {
        return  rhs.getDate().compareTo(lhs.getDate());
    }
}

