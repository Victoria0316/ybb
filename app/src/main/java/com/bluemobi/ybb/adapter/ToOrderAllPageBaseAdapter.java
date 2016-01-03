package com.bluemobi.ybb.adapter;

import android.content.Context;
import android.media.Rating;
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
import android.widget.Toast;

import com.alipay.mobilesecuritysdk.deviceID.LOG;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by liufy on 2015/7/13.
 */
public abstract class ToOrderAllPageBaseAdapter extends BaseAdapter {
    private final static String tag = "ToOrderAllPageBaseAdapter";

    public final int DISPLAY_COMFIRM_TYPE = 0;

    public final int DISPLAY_NORMOL_TYPE = 1;

    protected LayoutInflater mInflater;

    protected Context mContext;

//    protected List<String> mDatas;

    private List<Orderinfoinfo> mDatas;

    public int orderStateFlag;

    private String CollectionType = "3";




    public ToOrderAllPageBaseAdapter(Context context, List<Orderinfoinfo> mDatas) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

//        return getCustomItemViewType(position, orderStateFlag);

//        需要查询的订单类型【1：未付款 2：已付款(待发货) 3：已付款（已发货，待收货） 4：已收货(待评价) 5：已完成 6：已退单】
        switch (Integer.parseInt(mDatas.get(position).getOrderStatus())) {
            case 1:
                orderStateFlag = DISPLAY_NORMOL_TYPE;
                break;
            case 2:
                orderStateFlag = DISPLAY_COMFIRM_TYPE;
                break;
            case 3:
                orderStateFlag = DISPLAY_COMFIRM_TYPE;
                break;
            case 4:
                orderStateFlag = DISPLAY_NORMOL_TYPE;
                break;
            case 5:
                orderStateFlag = DISPLAY_NORMOL_TYPE;
                break;
            case 6:
                orderStateFlag = DISPLAY_NORMOL_TYPE;
                break;
            default:
                break;

        }
        return orderStateFlag;

    }

    public abstract int getCustomItemViewType(int position,
                                              String orderStateFlag);

    /**
     * 让ListView 显示2个布局
     */
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        ViewHolderConfirm holderCar = null;
//        ViewHolderNormal holderGoods = null;
        int type = getItemViewType(position);
        switch (type) {
            case DISPLAY_COMFIRM_TYPE:
                convertView = mInflater.inflate(R.layout.lv_page_order_receive,
                        parent, false);


//                    时间
                TextView tv_createTime_item = (TextView) convertView.findViewById(R.id.tv_createTime);
                tv_createTime_item.setText(mDatas.get(position).getCreateTime());
//                    待付款
                TextView tv_order_state_item = (TextView) convertView.findViewById(R.id.tv_order_state);
                tv_order_state_item.setText(Utils.intToOrderString(Integer.parseInt(mDatas.get(position).getOrderStatus())));
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

                int datasize = mDatas.get(position).getOrderItemDTOList().size();

                for (int i = 0; i < datasize; i++) {
                    View ordercontentview = mInflater.inflate(R.layout.lv_order_several_meals, null);


                    //收藏
                    final ImageView isColl = (ImageView) ordercontentview.findViewById(R.id.isColl);
                    if ("1".equals(mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getIsColl())) {//是否被当前用户收藏，0：未收藏，1：已收藏
                        isColl.setBackgroundResource(R.drawable.coll);
                    } else {
                        isColl.setBackgroundResource(R.drawable.un_coll);
                    }
                    final int finalI = i;

                    isColl.setTag(R.id.isColl,mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getIsColl());

                    Logger.d(tag, "你单击了收藏");
                    isColl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String flag=String.valueOf(v.getTag(R.id.isColl));

                            if ("1".equals(flag)){//bu shoucang

                                String userid = YbbApplication.getInstance().getMyUserInfo().getUserId();
                                String id = mDatas.get(position).getOrderItemDTOList().get(finalI).getProductComboGroupDTO().getId();

                                DelCollectionRequest(userid, id, CollectionType, isColl, mDatas, position, finalI);


                            }else{

                                String userid = YbbApplication.getInstance().getMyUserInfo().getUserId();
                                String id = mDatas.get(position).getOrderItemDTOList().get(finalI).getProductComboGroupDTO().getId();

                                CollectionRequest(userid, id, CollectionType, isColl,mDatas,position,finalI);



                            }

                        }
                    });

                    //早餐
                    TextView tv_meal_kind_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_meal_kind_item_1);
                    tv_meal_kind_item_1.setText(mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getAttributeName());
//                    图片
                    ImageView iv_image_bg_item_1 = (ImageView) ordercontentview.findViewById(R.id.iv_image_bg_item_1);
//                    Glide.with(mContext)
//                            .load(mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getImgPathList().get(0))
//                            .into(iv_image_bg_item_1);
//                    紫署庄园
                    TextView tv_search_item_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_search_item_item_1);
//                    tv_search_item_item_1.setText(mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getComboName());
//                    星级评价
                    RatingBar rb_comment_item_1 = (RatingBar) ordercontentview.findViewById(R.id.rb_comment_item_1);
//                    rb_comment_item_1.setRating(Double.parseDouble(mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getCommentStar()));
//                    18条评价
                    TextView commentCount_item_1 = (TextView) ordercontentview.findViewById(R.id.commentCount_item_1);
//                    commentCount_item_1.setText("(" + mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getCommentCount() + "条评价)");
//                    20.00
                    TextView tv_charge_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_charge_item_1);
//                    tv_charge_item_1.setText("￥"+mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getCustomer_price());
//                    X3
                    TextView tv_num_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_num_item_1);
//                    tv_num_item_1.setText("X " + mDatas.get(position).getOrderItemDTOList().get(i).getProductNum());

//                    确认收货
                    TextView tv_kind_right_dinner_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_kind_right_dinner_item_1);
                    tv_kind_right_dinner_item_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Logger.d(tag, "item_1 你单击了确认收货 " + position);
                        }
                    });
                    ll_content.addView(ordercontentview);

                }


                break;

            case DISPLAY_NORMOL_TYPE:


                convertView = mInflater.inflate(R.layout.lv_page_order_obligation, null);

                //隐藏 下面的 两个按钮
                if ("6".equals(mDatas.get(position).getOrderStatus()) || "5".equals(mDatas.get(position).getOrderStatus())) {

                    View includeView = convertView.findViewById(R.id.il_order_btn);
                    includeView.setVisibility(View.GONE);
                }


                TextView tv_left = (TextView) convertView.findViewById(R.id.tv_left);
                TextView tv_right = (TextView) convertView.findViewById(R.id.tv_right);

                if ("5".equals(mDatas.get(position).getOrderStatus()) || "4".equals(mDatas.get(position).getOrderStatus())) {
                    tv_left.setText(mContext.getString(R.string.str_mine_order_to_delete));
                    tv_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Logger.d(tag, "你单击了" + mContext.getString(R.string.str_mine_order_to_delete));
                            DeleteOrderRequest request = new DeleteOrderRequest(new Response.Listener<DeleteOrderResponse>() {
                                @Override
                                public void onResponse(DeleteOrderResponse response) {
                                    if (response != null && response.getStatus() == 0) {
                                        Logger.d(tag, "删除订单成功");
                                        mDatas.remove(position);
                                        ToOrderAllPageBaseAdapter.this.notifyDataSetChanged();
                                        Utils.makeToastAndShow(mContext, "删除订单成功");

                                    }

                                }
                            }, (BaseActivity) mContext);

                            request.setOrderId(mDatas.get(position).getOrderItemDTOList().get(0).getOrderId());

                            WebUtils.doPost(request);
                        }
                    });

                    tv_right.setText(mContext.getString(R.string.str_mine_order_addcomment));
                    tv_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Logger.d(tag, "你单击了" + mContext.getString(R.string.str_mine_order_addcomment));
                            Utils.moveTo(mContext, MyCommentActivity.class);

                        }
                    });
                } else {

                    tv_left.setText(mContext.getString(R.string.str_mine_order_cancle));
                    tv_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Logger.d(tag, "你单击了" + mContext.getString(R.string.str_mine_order_cancle));
                        }
                    });

                    tv_right.setText(mContext.getString(R.string.str_mine_order_pay));
                    tv_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Logger.d(tag, "你单击了" + mContext.getString(R.string.str_mine_order_pay));
                        }
                    });


                }


                //创建时间
                TextView tv_createTime = (TextView) convertView.findViewById(R.id.tv_createTime);
                tv_createTime.setText(mDatas.get(position).getCreateTime());
                //代付款
                TextView tv_order_state = (TextView) convertView.findViewById(R.id.tv_order_state);
                tv_order_state.setText(Utils.intToOrderString(Integer.parseInt(mDatas.get(position).getOrderStatus())));

                //订单内容 LinearLayout
                LinearLayout ll_content_a1 = (LinearLayout) convertView.findViewById(R.id.ll_content);

                if (mDatas.get(position).getOrderItemDTOList()!=null&&mDatas.get(position).getOrderItemDTOList().size()!=0){


                int datasize_a1 = mDatas.get(position).getOrderItemDTOList().size();
                for (int i = 0; i < datasize_a1; i++) {
                    View ordercontentview = mInflater.inflate(R.layout.lv_order_detail_item, null);


                    //收藏
                    final ImageView isColl = (ImageView) ordercontentview.findViewById(R.id.isColl);
                    if ("1".equals(mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getIsColl())) {//是否被当前用户收藏，0：未收藏，1：已收藏
                        isColl.setBackgroundResource(R.drawable.coll);
                    } else {
                        isColl.setBackgroundResource(R.drawable.un_coll);
                    }
                    final int finalI = i;

                    isColl.setTag(R.id.isColl,mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getIsColl());

                    Logger.d(tag, "你单击了收藏");
                    isColl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String flag=String.valueOf(v.getTag(R.id.isColl));

                            if ("1".equals(flag)){//bu shoucang

                                String userid = YbbApplication.getInstance().getMyUserInfo().getUserId();
                                String id = mDatas.get(position).getOrderItemDTOList().get(finalI).getProductComboGroupDTO().getId();

                                DelCollectionRequest(userid, id, CollectionType, isColl,mDatas,position,finalI);


                            }else{

                                String userid = YbbApplication.getInstance().getMyUserInfo().getUserId();
                                String id = mDatas.get(position).getOrderItemDTOList().get(finalI).getProductComboGroupDTO().getId();

                                CollectionRequest(userid, id, CollectionType, isColl,mDatas,position,finalI);



                            }

                        }
                    });


                    //图片
                    ImageView iv_image_bg_item_1 = (ImageView) ordercontentview.findViewById(R.id.iv_image_bg_item_1);
//                    Glide.with(mContext)
//                            .load(mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getImgPathList().get(0))
//                            .into(iv_image_bg_item_1);
                    //紫署庄园
                    TextView tv_search_item_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_search_item_item_1);
                    tv_search_item_item_1.setText(mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getComboName());
                    //星级评价
                    RatingBar rb_comment_item_1 = (RatingBar) ordercontentview.findViewById(R.id.rb_comment_item_1);
                    rb_comment_item_1.setRating(Float.parseFloat(mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getCommentStar()));
                    //18条评价
                    TextView commentCount_item_1 = (TextView) ordercontentview.findViewById(R.id.commentCount_item_1);
                    commentCount_item_1.setText("(" + mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getCommentCount() + "条评价)");
                    //20.00
                    TextView tv_charge_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_charge_item_1);
                    tv_charge_item_1.setText("¥" + mDatas.get(position).getOrderItemDTOList().get(i).getProductComboGroupDTO().getCustomer_price());
                    //X3
                    TextView tv_num_item_1 = (TextView) ordercontentview.findViewById(R.id.tv_num_item_1);
                    tv_num_item_1.setText("X" + mDatas.get(position).getOrderItemDTOList().get(i).getProductNum());


                    ll_content_a1.addView(ordercontentview);
                }
                }


//                holderGoods = new ViewHolderNormal();
//
//
////                时间
//                holderGoods.tv_createTime = (TextView) convertView.findViewById(R.id.tv_createTime);
////                待付款
//                holderGoods.tv_order_state = (TextView) convertView.findViewById(R.id.tv_order_state);
////                图片
//                holderGoods.iv_image_bg_item_1 = (ImageView) convertView.findViewById(R.id.iv_image_bg_item_1);
////                紫署庄园
//                holderGoods.tv_search_item_item_1 = (TextView) convertView.findViewById(R.id.tv_search_item_item_1);
////                星级评价
//                holderGoods.rb_comment_item_1 = (RatingBar) convertView.findViewById(R.id.rb_comment_item_1);
////                18条评价
//                holderGoods.commentCount_item_1 = (TextView) convertView.findViewById(R.id.commentCount_item_1);
////                20.00
//                holderGoods.tv_charge_item_1 = (TextView) convertView.findViewById(R.id.tv_charge_item_1);
////                X3
//                holderGoods.tv_num_item_1 = (TextView) convertView.findViewById(R.id.tv_num_item_1);
////                删除订单
//                holderGoods.tv_left = (TextView) convertView.findViewById(R.id.tv_left);
////                添加评价
//                holderGoods.tv_right = (TextView) convertView.findViewById(R.id.tv_right);
//
//
//                convertView.setTag(holderGoods);
                break;

            default:
                break;
        }


        //设置数据
        switch (type) {
            case DISPLAY_COMFIRM_TYPE:

////                    时间
//                holderCar.tv_createTime_item.setText(mDatas.get(position).getCreateTime());
////                    待付款
//                holderCar.tv_order_state_item.setText(mDatas.get(position).getOrderStatus());
//
//
//                int i = mDatas.get(position).getOrderItemDTOList().size();
//
//                //                        早餐
//                holderCar.tv_meal_kind_item_1.setText(mDatas.get(position).getOrderItemDTOList().get(index).getProductComboGroupDTO().getAttributeName());
////                    图片
//                Glide.with(mContext)
//                        .load(mDatas.get(position).getOrderItemDTOList().get(index).getProductComboGroupDTO().getImgPath())
//                        .into(holderCar.iv_image_bg_item_1);
////                    紫署庄园
//                holderCar.tv_search_item_item_1.setText(mDatas.get(position).getOrderItemDTOList().get(index).getProductComboGroupDTO().getComboName());
////                    星级评价
//                holderCar.rb_comment_item_1.setRating(Double.parseDouble(mDatas.get(position).getOrderItemDTOList().get(index).getProductComboGroupDTO().getCommentStar()));
////                    18条评价
//                holderCar.commentCount_item_1.setText("(" + mDatas.get(position).getOrderItemDTOList().get(index).getProductComboGroupDTO().getCommentCount() + "条评价)");
////                    20.00
//                holderCar.tv_charge_item_1.setText("￥"+mDatas.get(position).getOrderItemDTOList().get(index).getProductComboGroupDTO().getCustomer_price());
////                    X3
//                holderCar.tv_num_item_1.setText("X " + mDatas.get(position).getOrderItemDTOList().get(index).getProductNum());
//
//                holderCar.tv_kind_right_dinner_item_1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Logger.d(tag, "item_1 你单击了确认收货 " + position);
//                    }
//                });


                break;

            case DISPLAY_NORMOL_TYPE:

////                    时间
//                holderGoods.tv_createTime.setText(mDatas.get(position).getCreateTime());
////                    待付款
//                holderGoods.tv_order_state.setText(mDatas.get(position).getOrderStatus());
////                    图片
//                Glide.with(mContext)
//                        .load(mDatas.get(position).getOrderItemDTOList().get(index).getProductComboGroupDTO().getImgPath())
//                        .into(holderGoods.iv_image_bg_item_1);
////                    紫署庄园
//                holderGoods.tv_search_item_item_1.setText(mDatas.get(position).getOrderItemDTOList().get(index).getProductComboGroupDTO().getComboName());
////                    星级评价
//                holderGoods.rb_comment_item_1.setRating(Double.parseDouble(mDatas.get(position).getOrderItemDTOList().get(index).getProductComboGroupDTO().getCommentStar()));
////                    18条评价
//                holderGoods.commentCount_item_1.setText("(" + mDatas.get(position).getOrderItemDTOList().get(index).getProductComboGroupDTO().getCommentCount() + "条评价)");
////                    20.00
//                holderGoods.tv_charge_item_1.setText("¥"+mDatas.get(position).getOrderItemDTOList().get(index).getProductComboGroupDTO().getCustomer_price());
////                    X3
//                holderGoods.tv_num_item_1.setText("X " + mDatas.get(position).getOrderItemDTOList().get(index).getProductNum());
////                删除订单
//                holderGoods.tv_left.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Logger.d(tag, "你单击了删除订单");
//                    }
//                });
////                添加评价
//                holderGoods.tv_right.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Logger.d(tag, "你单击了添加评价");
//                    }
//                });


                break;

            default:
                break;
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
                    Utils.makeToastAndShow(mContext, "收藏成功");

                    isColl.setBackgroundResource(R.drawable.coll);
                    mDatas.get(position).getOrderItemDTOList().get(finalI).getProductComboGroupDTO().setIsColl("1");
                    ToOrderAllPageBaseAdapter.this.notifyDataSetChanged();

                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, (BaseActivity) mContext);
        request.setUserId(userid);
        request.setCollectionId(id);
        request.setCollectionType(collectionType);
        Utils.showProgressDialog(mContext);
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
                    Utils.makeToastAndShow(mContext, "取消收藏");

                    isColl.setBackgroundResource(R.drawable.un_coll);
                    mDatas.get(position).getOrderItemDTOList().get(finalI).getProductComboGroupDTO().setIsColl("0");
                    ToOrderAllPageBaseAdapter.this.notifyDataSetChanged();


                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, (BaseActivity) mContext);
        request.setUserid(userid);
        request.setCollectionId(id);
        request.setCollectionType(collectionType);
        Utils.showProgressDialog(mContext);
        WebUtils.doPost(request);
    }

    class ViewHolderConfirm {

        TextView tv_createTime_item, tv_order_state_item;
        RelativeLayout rl_another;

        View il_order_breakfast;
        TextView tv_meal_kind_item_1, tv_search_item_item_1, commentCount_item_1, tv_charge_item_1, tv_num_item_1, tv_kind_right_dinner_item_1;
        ImageView iv_image_bg_item_1;
        RatingBar rb_comment_item_1;

        View il_order_lunch;
        TextView tv_meal_kind_item_2, tv_search_item_item_2, commentCount_item_2, tv_charge_item_2, tv_num_item_2, tv_kind_right_dinner_item_2;
        ImageView iv_image_bg_item_2;
        RatingBar rb_comment_item_2;

        View il_order_supper;
        TextView tv_meal_kind_item_3, tv_search_item_item_3, commentCount_item_3, tv_charge_item_3, tv_num_item_3, tv_kind_right_dinner_item_3;
        ImageView iv_image_bg_item_3;
        RatingBar rb_comment_item_3;

    }

    class ViewHolderNormal {

        TextView tv_createTime, tv_order_state, tv_search_item_item_1, commentCount_item_1, tv_charge_item_1, tv_num_item_1, tv_left, tv_right;
        ImageView iv_image_bg_item_1;
        RatingBar rb_comment_item_1;


    }
}
