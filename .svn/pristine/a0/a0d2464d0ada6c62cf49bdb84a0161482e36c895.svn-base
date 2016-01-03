package com.bluemobi.ybb.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.Orderinfoinfo;
import com.bluemobi.ybb.network.request.CollectionRequest;
import com.bluemobi.ybb.network.request.ConfirmGoodsRequest;
import com.bluemobi.ybb.network.request.DelCollectionRequest;
import com.bluemobi.ybb.network.response.CollectionResponse;
import com.bluemobi.ybb.network.response.ConfirmGoodsResponse;
import com.bluemobi.ybb.network.response.DelCollectionResponse;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;

import java.util.List;

/**
 * Created by gaozq on 2015/8/17.
 */
public class MineOrderReceivePageAdapter extends BaseAdapter {
    private final static String tag = "MineOrderObligationPageAdapter";

    private List<Orderinfoinfo> mListData;

    private LayoutInflater inflater;

    private Context context;

    private boolean isCollFlag = false;

    private String CollectionType = "3";

    public MineOrderReceivePageAdapter(Context c, List<Orderinfoinfo> data) {
        this.mListData = data;
        this.inflater = LayoutInflater.from(c);
        this.context = c;
    }

    @Override
    public int getCount() {
        return mListData == null ? 0 : mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData == null ? null : mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        convertView = inflater.inflate(R.layout.lv_page_order_receive,
                parent, false);


//                    时间
        TextView tv_createTime_item = (TextView) convertView.findViewById(R.id.tv_createTime);
        tv_createTime_item.setText(mListData.get(position).getCreateTime());
//                    待付款
        TextView tv_order_state_item = (TextView) convertView.findViewById(R.id.tv_order_state);
        tv_order_state_item.setText(Utils.intToOrderString(Integer.parseInt(mListData.get(position).getOrderStatus())));
//                    显示其余一件
        RelativeLayout rl_another = (RelativeLayout) convertView.findViewById(R.id.rl_another);
        rl_another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(tag, "你单击了显示其余一件");

            }
        });

        //订单内容 LinearLayout
        LinearLayout ll_content = (LinearLayout) convertView.findViewById(R.id.ll_content);

        int datasize = mListData.get(position).getOrderItemDTOList().size();

        for (int i = 0; i < datasize; i++) {
            View ordercontentview = inflater.inflate(R.layout.lv_order_several_meals, null);

            //收藏
            final ImageView isColl = (ImageView) ordercontentview.findViewById(R.id.isColl);
            if ("1".equals(mListData.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getIsColl())) {//是否被当前用户收藏，0：未收藏，1：已收藏
                isColl.setBackgroundResource(R.drawable.coll);
            } else {
                isColl.setBackgroundResource(R.drawable.un_coll);
            }
            final int finalI = i;

            isColl.setTag(R.id.isColl,mListData.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getIsColl());

            Logger.d(tag, "你单击了收藏");
            isColl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String flag=String.valueOf(v.getTag(R.id.isColl));

                    if ("1".equals(flag)){//bu shoucang

                        String userid = YbbApplication.getInstance().getMyUserInfo().getUserId();
                        String id = mListData.get(position).getOrderItemDTOList().get(finalI).getProductComboGroupDTO().getId();

                        DelCollectionRequest(userid, id, CollectionType, isColl, mListData, position, finalI);


                    }else{

                        String userid = YbbApplication.getInstance().getMyUserInfo().getUserId();
                        String id = mListData.get(position).getOrderItemDTOList().get(finalI).getProductComboGroupDTO().getId();

                        CollectionRequest(userid, id, CollectionType, isColl,mListData,position,finalI);



                    }

                }
            });


            //早餐
            TextView tv_meal_kind_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_meal_kind_item_1);
            tv_meal_kind_item_1.setText(mListData.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getAttributeName());
//                    图片
            ImageView iv_image_bg_item_1 = (ImageView) ordercontentview.findViewById(R.id.iv_image_bg_item_1);
//                    Glide.with(mContext)
//                            .load(mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getImgPath())
//                            .into(iv_image_bg_item_1);
//                    紫署庄园
            TextView tv_search_item_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_search_item_item_1);
            tv_search_item_item_1.setText(mListData.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getComboName());
//                    星级评价
            RatingBar rb_comment_item_1 = (RatingBar) ordercontentview.findViewById(R.id.rb_comment_item_1);
            rb_comment_item_1.setRating(Float.parseFloat(mListData.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getCommentStar()));
//                    18条评价
            TextView commentCount_item_1 = (TextView) ordercontentview.findViewById(R.id.commentCount_item_1);
            commentCount_item_1.setText("(" + mListData.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getCommentCount() + "条评价)");
//                    20.00
            TextView tv_charge_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_charge_item_1);
            tv_charge_item_1.setText("￥" + mListData.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getCustomer_price());
//                    X3
            TextView tv_num_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_num_item_1);
            tv_num_item_1.setText("X " + mListData.get(position).getOrderItemDTOList().get(i).getProductNum());

//                    确认收货
            TextView tv_kind_right_dinner_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_kind_right_dinner_item_1);
            tv_kind_right_dinner_item_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.d(tag, "item_1 你单击了确认收货 " + position);
                    ConfirmGoodsRequest request = new ConfirmGoodsRequest(new Response.Listener<ConfirmGoodsResponse>() {
                        @Override
                        public void onResponse(ConfirmGoodsResponse response) {
                            if (response != null && response.getStatus() ==0){
                                Logger.d(tag, "确认收货 ok");
                                mListData.remove(position);
                                MineOrderReceivePageAdapter.this.notifyDataSetChanged();
                                Utils.makeToastAndShow(context,"确认收货成功");

                            }

                        }
                    },(BaseActivity)context);

                    request.setOrderId(mListData.get(position).getOrderItemDTOList().get(0).getOrderId());

                    WebUtils.doPost(request);


                }
            });
            ll_content.addView(ordercontentview);

        }


        return convertView;
    }


    /**
     * 收藏
     */
    private void CollectionRequest(String userid, String id, String collectionType, final ImageView isColl, final List<Orderinfoinfo> mDatas, final int position, final int finalI) {


        CollectionRequest request = new CollectionRequest(new Response.Listener<CollectionResponse>() {
            @Override
            public void onResponse(CollectionResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(context, "收藏成功");

                    isColl.setBackgroundResource(R.drawable.coll);
                    mDatas.get(position).getOrderItemDTOList().get(finalI).getProductComboGroupDTO().setIsColl("1");
                    MineOrderReceivePageAdapter.this.notifyDataSetChanged();

                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, (BaseActivity) context);
        request.setUserId(userid);
        request.setCollectionId(id);
        request.setCollectionType(collectionType);
        Utils.showProgressDialog(context);
        WebUtils.doPost(request);
    }

    /**
     * 取消收藏
     */
    public void DelCollectionRequest(String userid, String id, String collectionType, final ImageView isColl, final List<Orderinfoinfo> mDatas, final int position, final int finalI) {

        DelCollectionRequest request = new DelCollectionRequest(new Response.Listener<DelCollectionResponse>() {
            @Override
            public void onResponse(DelCollectionResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    Utils.makeToastAndShow(context, "取消收藏");

                    isColl.setBackgroundResource(R.drawable.un_coll);
                    mDatas.get(position).getOrderItemDTOList().get(finalI).getProductComboGroupDTO().setIsColl("0");
                    MineOrderReceivePageAdapter.this.notifyDataSetChanged();


                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, (BaseActivity) context);
        request.setUserid(userid);
        request.setCollectionId(id);
        request.setCollectionType(collectionType);
        Utils.showProgressDialog(context);
        WebUtils.doPost(request);
    }
}
