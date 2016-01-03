package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;

import java.util.ArrayList;

/**
 * Created by liufy on 2015/8/10.
 */
public class ShopCartResponse extends YbbHttpResponse {
    public ShopCartData data;


    public static class ShopCartData {
        public String currentpage;
        public String totalnum;
        public String totalpage;

        public ArrayList<ShopCartDTo> info = new ArrayList<ShopCartDTo>();

        public String getCurrentpage() {
            int i = Integer.parseInt(currentpage) - 1;
            return String.valueOf(i);
        }

        public static class ShopCartDTo {

            public String id;//购物车ID;
            public String userId;// userId： 购物车所属用户ID；;//
            public String productId;//productId：商品ID;
            public String productName;// productName：商品名称;
            public String productNum;// productNum:数量;
            public String categoryId;// categoryId：套餐类型（1营养餐2零点餐3医护套餐4医患套餐）；
            public String price;// price：单价;
            public String startTime;// startTime：开始时间;
            public String endTime;// endTime：结束时间;
            public String imgPath;// imgPath：图片路径;
            public String imgType;// imgType：图片类型;
            public String collectionId;// collectionId：收藏ID （如果此字段存在值，则表示该物品已经收藏。如果此字段不存在或者为空，则表示未收藏）;
            public String reserveTime;
            public String attributeId;//餐次 早中晚
            public String attributeName;//餐次名称
            public boolean selected;
            public String preStatus;
            public String type;
        }
    }


}
