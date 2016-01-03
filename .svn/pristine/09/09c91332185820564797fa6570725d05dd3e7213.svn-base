package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;

import java.util.ArrayList;

/**
 * Created by liufy on 2015/8/10.
 */
public class InformationListResponse extends YbbHttpResponse {
    public InformationData data;


    public static class InformationData {
        public String currentpage;
        public String totalnum;
        public String totalpage;

        public ArrayList<InformationDTo> info = new ArrayList<InformationDTo>();

        public String getCurrentpage() {
            int i = Integer.parseInt(currentpage)-1;
            return String.valueOf(i);
        }

        public static class InformationDTo {
            public String actTag; //0草稿1发布中2归档
            public String advertisingPositionId; // 页面所在位置（广告位）",
            public String artAbstract; // 摘要",
            public String artChildTitle; //文章副标题",
            public String artContent; //文章内容,",
            public String artSupplier; //文章供应商（广告商）",
            public String artTitle; //文章标题",
            public String attrCityId; //归属城市",
            public String beginTime; //开始时间",
            public String columnId; //栏目id",
            public String columnName; //栏目名称",
            public String createTime; //2015-08-0812:22:31",
            public String endTime; //结束时间",
            public String id; //主键id",
            public String imagePath; //封面图片地址",
            public String isColl; //0未收藏1已收藏",
            public String optTime; //操作时间",
            public String releaseDate; //过期状态（0未过期，1已过期）",
            public String releaseState; //发布时间",
            public String remark; //备注",
            public String status; // 0,
            public String userId; // "发布者id",
            public String webLink; // "web链接",
            public ArrayList<String> imgList; // imgList,
        }
    }


}
