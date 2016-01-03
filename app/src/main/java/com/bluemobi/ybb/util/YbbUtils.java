package com.bluemobi.ybb.util;

/**
 * Created by wangzhijun on 2015/9/30.
 */
public class YbbUtils {
    public static String getCategoryById(String categoryId){
        if("1".equals(categoryId)){
            return "营养餐";
        }else if("2".equals(categoryId)){
            return "零点餐";
        }else if("3".equals(categoryId)){
            return "医护套餐";
        }else if("4".equals(categoryId)){
            return "病患套餐";
        }
        return "";
    }
}
