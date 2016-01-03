package com.bluemobi.ybb.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.MineOrderActivcity;
import com.bluemobi.ybb.activity.MyCommentActivity;
import com.bluemobi.ybb.activity.OrderDetailsActivity;
import com.bluemobi.ybb.activity.PaymentActivity;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.OrderAttribute;
import com.bluemobi.ybb.network.model.OrderAttributeChild;
import com.bluemobi.ybb.network.model.OrderItem;
import com.bluemobi.ybb.network.request.CollectionRequest;
import com.bluemobi.ybb.network.request.DelCollectionRequest;
import com.bluemobi.ybb.network.request.DeleteOrderRequest;
import com.bluemobi.ybb.network.request.SureReceiveRequest;
import com.bluemobi.ybb.network.response.CollectionResponse;
import com.bluemobi.ybb.network.response.DelCollectionResponse;
import com.bluemobi.ybb.network.response.DeleteOrderResponse;
import com.bluemobi.ybb.network.response.SureReceiveResponse;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.CustomExpandableListView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangzhijun on 2015/8/26.
 */
public class OrderAdapter extends BaseAdapter {
    private List<OrderItem> data;
    private Context mContext;
    //    private List<>
    private AttributeAdapter attributeAdapter;
    private List<List<OrderAttribute>> allItemGroups = new ArrayList<List<OrderAttribute>>();
    private List<List<List<OrderAttributeChild>>> allItemChilds = new ArrayList<List<List<OrderAttributeChild>>>();

    private BaseActivity baseActivity;

    private boolean isComment;

    private MineOrderActivcity mineOrderActivcity;

    public OrderAdapter(Context mContext, List<OrderItem> data) {
        this.data = data;
        this.mContext = mContext;
        baseActivity = (BaseActivity) mContext;
        mineOrderActivcity =(MineOrderActivcity)mContext;
//        attributeAdapter = new AttributeAdapter();
        loadData();


    }
    public OrderAdapter(Context mContext, List<OrderItem> data, boolean isComment) {
        this.data = data;
        this.mContext = mContext;
        baseActivity = (BaseActivity) mContext;
        mineOrderActivcity =(MineOrderActivcity)mContext;

        this.isComment = isComment;
//        attributeAdapter = new AttributeAdapter();
        loadData();


    }

    public void reload() {

        loadData();
        this.notifyDataSetChanged();
    }

    private void loadData() {
        long tempB =  new Date().getTime();
//        Logger.e("wangzhijun", "begin--> " + tempB);
        allItemGroups.clear();
        allItemChilds.clear();
        if (data != null && data.size() > 0) {
//            Logger.e("wangzhijun", "data.size()--> " + data.size());
            int size = data.size();
            for (int i = 0; i < size; i++) {
                List<List<OrderAttributeChild>> tempList = new ArrayList<List<OrderAttributeChild>>();
                OrderItem orderItem = data.get(i);
                int size2 = orderItem.getLogisticsDistributionInfoDTOList() == null ? 0 :
                        orderItem.getLogisticsDistributionInfoDTOList().size();
                if (size2 == 0) {
                    allItemChilds.add(null);
                } else {
                    for (int j = 0; j < size2; j++) {
                        List<OrderAttributeChild> temp = orderItem.getLogisticsDistributionInfoDTOList().
                                get(j).getProductComboGroupDTOList();
                        tempList.add(temp);
                    }
                    allItemChilds.add(tempList);
                }
                allItemGroups.add(orderItem.getLogisticsDistributionInfoDTOList());

            }
        }
        long tempE =  new Date().getTime();

//        Logger.e("wangzhijun", "end--> " + tempE);
//        Logger.e("wangzhijun", "total--> " + (tempE-tempB));

    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(
                R.layout.lv_page_adapter_order_list, parent, false
        );
        final OrderItem orderItem = data.get(position);
        TextView time = (TextView) convertView.findViewById(R.id.tv_createTime);
        TextView orderStatus = (TextView) convertView.findViewById(R.id.tv_order_state);
        time.setText(orderItem.getReserveTime());

        TextView tv_left = (TextView) convertView.findViewById(R.id.tv_left);


        TextView tv_right = (TextView) convertView.findViewById(R.id.tv_right);

        if ("1".equals(orderItem.getOrderStatus())) {//付款
            tv_left.setText("取消订单");
            tv_right.setText("付款");

        } else if ("2".equals(orderItem.getOrderStatus())
                || "3".equals(orderItem.getOrderStatus())) {
            tv_left.setVisibility(View.GONE);
            tv_right.setVisibility(View.GONE);
        } else if ("4".equals(orderItem.getOrderStatus())) {
            tv_left.setText("删除订单");
            tv_right.setText("添加评论");
        } else {
            tv_left.setVisibility(View.GONE);
            tv_right.setText("删除订单");
        }
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(orderItem.getOrderStatus())) {//付款
                    cancelOrder(orderItem);

                } else if ("2".equals(orderItem.getOrderStatus())
                        || "3".equals(orderItem.getOrderStatus())) {

                } else if ("4".equals(orderItem.getOrderStatus())) {
                    delOrder(orderItem);
                } else {
                    delOrder(orderItem);
                }
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(orderItem.getOrderStatus())) {//付款
                    doPay(orderItem);
                } else if ("2".equals(orderItem.getOrderStatus())
                        || "3".equals(orderItem.getOrderStatus())) {

                } else if ("4".equals(orderItem.getOrderStatus())) {
                    doComment(orderItem);
                } else {
                    delOrder(orderItem);
                }
            }
        });

        orderStatus.setText(getOrderStatus(orderItem.getOrderStatus()));
        initChild(position, convertView, orderItem.getOrderStatus());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetail(orderItem.getId());
            }
        });
        return convertView;
    }

    private void doComment(OrderItem orderItem) {
        Intent intent = new Intent();
        intent.setClass(mContext, MyCommentActivity.class);
        intent.putExtra("item", orderItem);
        intent.putExtra("index", data.indexOf(orderItem));
        Logger.e("wangzhijun", "doComment--> " + isComment);
        intent.putExtra("isComment", isComment);
        mContext.startActivity(intent);
    }

    private void doPay(OrderItem orderItem) {
        Intent intent = new Intent();
        intent.setClass(mContext, PaymentActivity.class);
        intent.putExtra("orderNo", orderItem.getOrderNo());
        intent.putExtra("totalAmount", orderItem.getTotalAmount());
        intent.putExtra("from", "mineOrder");
        mContext.startActivity(intent);
//        doComment(orderItem);
    }

    private void delOrder(final OrderItem orderItem) {
        DeleteOrderRequest request = new DeleteOrderRequest(new Response.Listener<DeleteOrderResponse>() {
            @Override
            public void onResponse(DeleteOrderResponse response) {
                Utils.closeDialog();
                if(response != null && response.getStatus() ==0){
                    Utils.makeToastAndShow(mContext,"删除成功");
                    int index = data.indexOf(orderItem);
                    data.remove(index);
                    allItemGroups.remove(index);
                    allItemChilds.remove(index);
                    OrderAdapter.this.notifyDataSetChanged();
//                    YbbApplication.getInstance()
                    mineOrderActivcity.notifyChanged(MineOrderActivcity.MEAL);
                }
            }
        }, baseActivity);
        request.setOrderId(orderItem.getId());
        request.setHandleCustomErr(false);
        WebUtils.doPost(request);
        Utils.showProgressDialog(mContext);
    }

    private void cancelOrder(OrderItem orderItem) {
        //TODO: do same with del oderder?
        delOrder(orderItem);
    }

    /**
     * 需要查询的订单类型【"" : 全部
     * 1：未付款 2：已付款(待发货) 3：已付款（已发货，待收货） 4：已收货(待评价) 5：已完成 6：已退单】
     * @param orderStatus
     * @return
     */
    private String getOrderStatus(String orderStatus) {
        if ("1".equals(orderStatus)) {
            return "待付款";
        } else if ("2".equals(orderStatus)) {
            return "待发货";
        } else if ("3".equals(orderStatus)) {
            return "待收货";
        } else if ("4".equals(orderStatus)) {
            return "待评价";
        } else if ("5".equals(orderStatus)) {
            return "已完成";
        } else if ("6".equals(orderStatus)) {
            return "已退餐";
        }
        return StringUtils.isEmpty(orderStatus) ? "未知" : orderStatus;
    }

    private void initChild(final int position, View convertView, String orderStatus) {
        List<OrderAttribute> itemGroups = allItemGroups.get(position);
        List<List<OrderAttributeChild>> itemChilds = allItemChilds.get(position);
        AttributeAdapter attributeAdapter = new AttributeAdapter(itemGroups, itemChilds, orderStatus);
        CustomExpandableListView expandableListView = (CustomExpandableListView) convertView.findViewById(R.id.child_items);
        expandableListView.setAdapter(attributeAdapter);
        expandableListView.setGroupIndicator(null);
        expandableListView.setSelector(R.color.transparent);
        expandableListView.setCacheColorHint(mContext.getResources().getColor(R.color.transparent));
        expandableListView.setDivider(mContext.getResources().getDrawable(R.drawable.line_h));
        for (int i = 0; i < attributeAdapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                goToDetail(data.get(position).getId());
                return true;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                goToDetail(data.get(position).getId());
                return false;
            }
        });
//        expandableListView.setFocusable(false);
//        expandableListView.setClickable(false);
//        expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                goToDetail(data.get(position).getId());
//            }
//        });
    }

    private void goToDetail(String orderId) {
        Intent intent = new Intent();
        intent.setClass(mContext, OrderDetailsActivity.class);
        intent.putExtra("orderId", orderId);
        mContext.startActivity(intent);
    }

    class AttributeAdapter extends BaseExpandableListAdapter {

        private List<OrderAttribute> itemGroups = new ArrayList<OrderAttribute>();
        private List<List<OrderAttributeChild>> itemChilds = new ArrayList<List<OrderAttributeChild>>();
        private String orderStatus;

        public AttributeAdapter(List<OrderAttribute> itemGroups, List<List<OrderAttributeChild>> itemChilds, String orderStatus) {
            this.itemGroups = itemGroups;
            this.itemChilds = itemChilds;
            this.orderStatus = orderStatus;
        }

        @Override
        public int getGroupCount() {
            return itemGroups == null ? 0 : itemGroups.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return itemGroups == null ? 0 : itemChilds.get(groupPosition) == null ? 0 :
                    itemChilds.get(groupPosition).size()
                    ;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return itemGroups == null ? null : itemGroups.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return itemGroups == null ? null : itemChilds.get(groupPosition) == null ? null :
                    itemChilds.get(groupPosition).get(childPosition) == null ? null :
                            itemChilds.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final OrderAttribute item = itemGroups.get(groupPosition);
            GroupViewHolder groupViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.lv_page_adapter_order_group, parent, false
                );
                groupViewHolder = new GroupViewHolder();
                groupViewHolder.attribute_name = (TextView) convertView.findViewById(R.id.attribute_name);
                groupViewHolder.attribute_status = (TextView) convertView.findViewById(R.id.attribute_status);
                convertView.setTag(groupViewHolder);
            }
            groupViewHolder = (GroupViewHolder) convertView.getTag();
            if("2".equals(itemChilds.get(groupPosition).get(0).getCategoryId())){
                groupViewHolder.attribute_name.setText("零点餐");
            }else{
                groupViewHolder.attribute_name.setText(itemChilds.get(groupPosition).get(0).getAttributeName());
            }
            groupViewHolder.attribute_status.setText(getGroupStatus(groupViewHolder.attribute_status, item.getDistributionType()));
            groupViewHolder.attribute_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("2".equals(item.getDistributionType()) ||
                            "1".equals(item.getDistributionType())) {
                        sureReceive(item);
                    }
                }
            });

            return convertView;
        }

        class GroupViewHolder {
            public TextView attribute_name;
            public TextView attribute_status;
        }

        private void sureReceive(final OrderAttribute item) {
            SureReceiveRequest receiveRequest = new SureReceiveRequest(new Response.Listener<SureReceiveResponse>() {
                @Override
                public void onResponse(SureReceiveResponse response) {
                    Utils.closeDialog();
                    if(response != null && response.getStatus() ==0){
                        Utils.makeToastAndShow(baseActivity, "收货成功");
                        item.setDistributionType("3");
                        AttributeAdapter.this.notifyDataSetChanged();
                        OrderAdapter.this.notifyDataSetChanged();
                        mineOrderActivcity.notifyChanged(MineOrderActivcity.MEAL);
                    }
                }
            }, baseActivity);
            receiveRequest.setId(item.getId());
            WebUtils.doPost(receiveRequest);
            Utils.showProgressDialog(baseActivity);
        }

        /**
         * 0未接单，1接单，2送货中，3已送到，4已退餐，5退餐中, 6 退餐处理中
         * 配送单状态
         * @param distributionType
         * @return
         */
        private String getGroupStatus(TextView attribute_status, String distributionType) {
            attribute_status.setVisibility(View.VISIBLE);
            if ("0".equals(distributionType)) {
                if("1".equals(orderStatus)){//待付款
                    attribute_status.setVisibility(View.GONE);
                }else if("2".equals(orderStatus)){
                    attribute_status.setTextAppearance(mContext, R.style.text_13_sp);
                    attribute_status.setBackgroundResource(R.color.white);
                }else{
                    attribute_status.setVisibility(View.GONE);
                }
                return "等待店主确认";
            } else if ("1".equals(distributionType)) {
                attribute_status.setTextAppearance(mContext, R.style.order_btn_bg);
                attribute_status.setBackgroundResource(R.drawable.order_btn_bg);
                attribute_status.setGravity(Gravity.CENTER);
                return "确认收货";
            } else if ("2".equals(distributionType)) {
                attribute_status.setTextAppearance(mContext, R.style.order_btn_bg);
                attribute_status.setBackgroundResource(R.drawable.order_btn_bg);
                attribute_status.setGravity(Gravity.CENTER);
                return "确认收货";
            } else if ("3".equals(distributionType)) {
                attribute_status.setTextAppearance(mContext, R.style.text_13_sp);
                attribute_status.setBackgroundResource(R.color.white);
                return "已完成";
            } else if ("4".equals(distributionType)) {
                attribute_status.setTextAppearance(mContext, R.style.text_13_sp);
                attribute_status.setBackgroundResource(R.color.white);
                return "已退餐";
            } else if ("5".equals(distributionType)) {
                attribute_status.setTextAppearance(mContext, R.style.text_13_sp);
                attribute_status.setBackgroundResource(R.color.white);
                return "退餐中";
            }else if ("6".equals(distributionType)) {
                attribute_status.setTextAppearance(mContext, R.style.text_13_sp);
                attribute_status.setBackgroundResource(R.color.white);
                return "退餐处理中";
            }
            return StringUtils.isEmpty(distributionType) ? "未知" : distributionType;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, final ViewGroup parent) {
            ChildViewHolder childViewHolder = null;
            final OrderAttributeChild item = itemChilds.get(groupPosition).get(childPosition);
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.lv_page_adapter_order_child, parent, false
                );
                childViewHolder = new ChildViewHolder();
                childViewHolder.iv_image_bg_item_1 = (ImageView) convertView.findViewById(R.id.iv_image_bg_item_1);
                childViewHolder.tv_search_item_item_1 = (TextView) convertView.findViewById(R.id.tv_search_item_item_1);
                childViewHolder.rb_comment_item_1 = (RatingBar) convertView.findViewById(R.id.rb_comment_item_1);
                childViewHolder.commentCount_item_1 = (TextView) convertView.findViewById(R.id.commentCount_item_1);
                childViewHolder.tv_charge_item_1 = (TextView) convertView.findViewById(R.id.tv_charge_item_1);
                childViewHolder.tv_num_item_1 = (TextView) convertView.findViewById(R.id.tv_num_item_1);
                childViewHolder.isColl=(ImageView)convertView.findViewById(R.id.isColl);
                convertView.setTag(childViewHolder);
            }

            childViewHolder = (ChildViewHolder)convertView.getTag();
            childViewHolder.tv_search_item_item_1.setText(item.getComboName());
            childViewHolder.rb_comment_item_1.setRating(Float.parseFloat(StringUtils.isEmpty(
                    item.getCommentStar()) ? "0" : item.getCommentStar()));
            childViewHolder.commentCount_item_1.setText("(" + item.getCommentCount() + "条评论)");
            childViewHolder.tv_charge_item_1.setText("￥" + item.getCustomerPrice());
            childViewHolder.tv_num_item_1.setText("X" + item.getNum());
            String tempPath = item.getImgList() == null ? "" : item.getImgList().size()
                    > 0 ? item.getImgList().get(0) : "";
            Glide.with(mContext).load(tempPath)
//                    .override(Utils.dip2px(mContext, 100),
//                    Utils.dip2px(mContext, 65))
                    .placeholder(R.drawable.temp_item)
//                    .into(Utils.dip2px(mContext, 100),
//                    Utils.dip2px(mContext, 65))
                    .into(childViewHolder.iv_image_bg_item_1);

            if("1".equals(item.getIsColl())){//是否被当前用户收藏，0：未收藏，1：已收藏
                childViewHolder.isColl.setBackgroundResource(R.drawable.coll);
            }else{
                childViewHolder.isColl.setBackgroundResource(R.drawable.un_coll);
            }
            //mContext
            childViewHolder.isColl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("1".equals(item.getIsColl())) {//是否被当前用户收藏，0：未收藏，1：已收藏
//                        setDelCollectionRequest(item.getId(), "3", refresh, groupPosition, childPosition);//收藏信息类型 1:店铺，2:商品，3：餐品，4：资讯
                        DelCollectionRequest request = new DelCollectionRequest(new Response.Listener<DelCollectionResponse>() {
                            @Override
                            public void onResponse(DelCollectionResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {// success
                                    Utils.makeToastAndShow(mContext, "取消收藏");
                                    itemChilds.get(groupPosition).get(childPosition).setIsColl("0");
                                    OrderAdapter.this.notifyDataSetChanged();
                                } else {// failed
                                    Log.e("error", "error");
                                }
                            }
                        },(BaseActivity) mContext);
                        request.setUserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
                        request.setCollectionId(item.getId());
                        request.setCollectionType("3");
                        Utils.showProgressDialog(mContext);
                        WebUtils.doPost(request);
                    } else {
                        CollectionRequest request = new CollectionRequest(new Response.Listener<CollectionResponse>() {
                            @Override
                            public void onResponse(CollectionResponse response) {
                                Utils.closeDialog();
                                if (response != null && response.getStatus() == 0) {// success
                                    Utils.makeToastAndShow(mContext, "收藏成功");
                                    itemChilds.get(groupPosition).get(childPosition).setIsColl("1");
                                    OrderAdapter.this.notifyDataSetChanged();
                                } else {// failed
                                    Log.e("error", "error");
                                }
                            }
                        },(BaseActivity) mContext);
                        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
                        request.setCollectionId(item.getId());
                        request.setCollectionType("3");
                        Utils.showProgressDialog(mContext);
                        WebUtils.doPost(request);
//                        setCollectionRequest(item.getId(), "3", refresh, groupPosition, childPosition);
                    }
                    AttributeAdapter.this.notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ChildViewHolder {
            public ImageView iv_image_bg_item_1;
            public TextView tv_search_item_item_1;
            public RatingBar rb_comment_item_1;
            public TextView commentCount_item_1;
            public TextView tv_charge_item_1;
            public TextView tv_num_item_1;
            public ImageView isColl;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
