package com.bluemobi.ybb.app;

import com.bluemobi.ybb.network.model.ParamModel;

import java.util.Comparator;

/**
 * Created by wangzhijun on 2015/9/28.
 */
public class AttributeComparator implements Comparator<ParamModel.ProductAttributeEntity>{
    @Override
    public int compare(ParamModel.ProductAttributeEntity lhs, ParamModel.ProductAttributeEntity rhs) {
        if(lhs.getAttributeName().contains("晚")){
            return 1;
        }
        if(lhs.getAttributeName().contains("中") && rhs.getAttributeName().contains("早")){
            return 1;
        }
        if(lhs.getAttributeName().contains("午") && rhs.getAttributeName().contains("早")){
            return 1;
        }
        return 0;
    }
}
