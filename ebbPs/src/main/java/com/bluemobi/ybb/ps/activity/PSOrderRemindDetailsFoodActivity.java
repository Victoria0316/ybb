package com.bluemobi.ybb.ps.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bluemobi.base.utils.StringUtils;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.adapter.CommonAdapter;
import com.bluemobi.ybb.ps.adapter.ViewHolder;
import com.bluemobi.ybb.ps.app.DbManager;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.db.entity.PSMessage;
import com.bluemobi.ybb.ps.db.entity.PSMessageFoods;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * Created by gaoyn on 2015/7/3.
 *
 * p5-8-2 餐品提醒详情
 */
public class PSOrderRemindDetailsFoodActivity extends BaseActivity {

    private CommonAdapter<PSMessageFoods> adapter;
    public   List<PSMessageFoods> childData ;
    public PSMessage partentData = new PSMessage();
    private String id;
    private String orderstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(PSOrderRemindDetailsFoodActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.Order_remind_details, R.drawable.common_back, true);
        showLoadingPage(false);
    }
    @Override
    protected void initBaseData() {
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        id = bundle.getString("id");
        if(StringUtils.isNotEmpty(id)){
            //显示
            DbUtils db = DbManager.getInstance(mContext).getDefaultDbUtils();
            try {
                partentData = db.findFirst(Selector.from(PSMessage.class).
                        where("id", "=", id));
                if (partentData!=null) {
                     childData=db.findAll(Selector.from(PSMessageFoods.class).where("orderid","=",partentData.getOrderId()));
                    db.execNonQuery("update PSMessage set isread = 1 where id = "+id);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
         View view = inflater.inflate(R.layout.activity_psorder_remind_details,null);
        TextView status=(TextView)view.findViewById(R.id.status);
        TextView orderid=(TextView)view.findViewById(R.id.orderid);
        TextView time=(TextView)view.findViewById(R.id.time);
        status.setText("订单状态："+ Utils.getStatusName(Integer.valueOf(partentData.getOrderStatus())));
        time.setText(partentData.getCreateTime());
        orderid.setText(partentData.getOrderNo());
        ListView order_goods = (ListView) view.findViewById(R.id.order_goods);
        order_goods.setAdapter(
                adapter = new CommonAdapter<PSMessageFoods>(mContext, childData, R.layout.lv_psorder_details_goods) {
                    @Override
                    public void convert(ViewHolder helper, PSMessageFoods item) {
                        TextView name = (TextView) helper.getView(R.id.name);
                        name.setText(item.getShopAndName());
                        TextView tv_charge = (TextView) helper.getView(R.id.tv_charge);
                        if(StringUtils.isNotEmpty(item.getPrice())){
                            tv_charge.setText("¥" + item.getPrice());
                        }else {
                            tv_charge.setText("无");
                        }
                        TextView et_hint = (TextView) helper.getView(R.id.et_hint);
                        if(StringUtils.isNotEmpty(item.getCount())){
                            et_hint.setText("×"+item.getCount());
                        }else {
                            et_hint.setText("无");
                        }

                        ImageView iv_image_bg=(ImageView)helper.getView(R.id.iv_image_bg);
                        if (StringUtils.isNotEmpty(item.getImgPath())&&!"null".equals(item.getImgPath())){
//                           String[] strings= item.getImgPath().split(",");
                            Glide.with(mContext).load(item.getImgPath()).into(iv_image_bg);
                        }else {
                            iv_image_bg.setImageResource(R.drawable.order_goods_head);
                        }
                    }
                }
        );
        return view;
    }


    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }
    @Override
    public void clickBarleft() {
        finish();
    }

//    public void onNewIntent(Intent intent) {
//        // TODO Auto-generated method stub
//        super.onNewIntent(intent);
//        OrderInfoModel bean = (OrderInfoModel) getIntent().getSerializableExtra("bean");
//                if ( bean.getData().getOrderItemDTOList()!=null && bean.getData().getOrderItemDTOList().size()!=0){
//                            for (int i=0;i<bean.getData().getOrderItemDTOList().size();i++){
//                                MessageFoods food= new MessageFoods();
//                                food.setOrderid(bean.getData().getOrderItemDTOList().get(i).getOrderId());
//                                food.setShopAndName(bean.getData().getOrderItemDTOList().get(i).getProductComboGroupDTO().getShopName());
//                                food.setPrice(bean.getData().getOrderItemDTOList().get(i).getProductComboGroupDTO().getSellPrice());
//                                food.setImgPath(bean.getData().getOrderItemDTOList().get(i).getProductComboGroupDTO().getImgPath());
//                                food.setCount(bean.getData().getOrderItemDTOList().get(i).getProductComboGroupDTO().getSaleCount());
//                                childData.add(food);
//                            }
//                }
//        partentData.setId(bean.getData().getId());
//        partentData.setOrderNo(bean.getData().getOrderNo());
//        partentData.setOrderStatus(bean.getData().getOrderStatus());
//        partentData.setCreateTime(bean.getData().getCreateTime());
//        partentData.setIsread("1");//已读
//    }
}
