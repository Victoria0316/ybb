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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.MyCommentActivity;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.Orderinfoinfo;
import com.bluemobi.ybb.network.request.CollectionRequest;
import com.bluemobi.ybb.network.request.DelCollectionRequest;
import com.bluemobi.ybb.network.request.DeleteOrderRequest;
import com.bluemobi.ybb.network.response.CollectionResponse;
import com.bluemobi.ybb.network.response.DelCollectionResponse;
import com.bluemobi.ybb.network.response.DeleteOrderResponse;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;

import java.util.List;

/**
 * Created by gaozq on 2015/8/17.
 */
public class MineOrderToEvaluatePageAdapter extends BaseAdapter {
    private final static String tag = "MineOrderToEvaluatePageAdapter";

    private List<Orderinfoinfo> mListData;

    private LayoutInflater inflater;

    private Context context;

    private boolean isCollFlag = false;

    private String CollectionType = "3";

    public MineOrderToEvaluatePageAdapter(Context c, List<Orderinfoinfo> data) {
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

        View view = inflater.inflate(R.layout.lv_page_order_obligation, null);


        TextView tv_left = (TextView) view.findViewById(R.id.tv_left);
        tv_left.setText(context.getString(R.string.str_mine_order_to_delete));
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(tag, "你单击了" + context.getString(R.string.str_mine_order_to_delete));
                DeleteOrderRequest request = new DeleteOrderRequest(new Response.Listener<DeleteOrderResponse>() {
                    @Override
                    public void onResponse(DeleteOrderResponse response) {
                        if (response != null && response.getStatus() == 0) {
                            Logger.d(tag, "删除订单成功");
                            mListData.remove(position);
                            MineOrderToEvaluatePageAdapter.this.notifyDataSetChanged();
                            Toast.makeText(context,"删除订单成功",Toast.LENGTH_SHORT).show();

                        }

                    }
                }, (BaseActivity) context);

                request.setOrderId(mListData.get(position).getOrderItemDTOList().get(0).getOrderId());

                WebUtils.doPost(request);


            }
        });

        TextView tv_right = (TextView) view.findViewById(R.id.tv_right);
        tv_right.setText(context.getString(R.string.str_mine_order_addcomment));
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(tag, "你单击了" + context.getString(R.string.str_mine_order_addcomment));
                Utils.moveTo(context, MyCommentActivity.class);
            }
        });

        //创建时间
        TextView tv_createTime = (TextView) view.findViewById(R.id.tv_createTime);
        tv_createTime.setText(mListData.get(position).getCreateTime());
        //代付款
        TextView tv_order_state = (TextView) view.findViewById(R.id.tv_order_state);
        tv_order_state.setText(Utils.intToOrderString(Integer.parseInt(mListData.get(position).getOrderStatus())));

        //订单内容 LinearLayout
        LinearLayout ll_content = (LinearLayout) view.findViewById(R.id.ll_content);

        int datasize = mListData.get(position).getOrderItemDTOList().size();
        for (int i = 0; i < datasize; i++) {
            View ordercontentview = inflater.inflate(R.layout.lv_order_detail_item, null);


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

            //图片
            ImageView iv_image_bg_item_1 = (ImageView) ordercontentview.findViewById(R.id.iv_image_bg_item_1);
//            Glide.with(context)
//                    .load(mListData.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().get)
//                    .into(iv_image_bg_item_1);
            //紫署庄园
            TextView tv_search_item_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_search_item_item_1);
            tv_search_item_item_1.setText(mListData.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getComboName());
            //星级评价
            RatingBar rb_comment_item_1 = (RatingBar) ordercontentview.findViewById(R.id.rb_comment_item_1);
            rb_comment_item_1.setRating(Float.parseFloat(mListData.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getCommentStar()));
            //18条评价
            TextView commentCount_item_1 = (TextView) ordercontentview.findViewById(R.id.commentCount_item_1);
            commentCount_item_1.setText("(" + mListData.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getCommentCount() + "条评价)");
            //20.00
            TextView tv_charge_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_charge_item_1);
            tv_charge_item_1.setText("¥" + mListData.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getCustomer_price());
            //X3
            TextView tv_num_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_num_item_1);
            tv_num_item_1.setText("X" + mListData.get(position).getOrderItemDTOList().get(i).getProductNum());


            ll_content.addView(ordercontentview);
        }


        return view;
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
                    MineOrderToEvaluatePageAdapter.this.notifyDataSetChanged();

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
                    MineOrderToEvaluatePageAdapter.this.notifyDataSetChanged();


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
