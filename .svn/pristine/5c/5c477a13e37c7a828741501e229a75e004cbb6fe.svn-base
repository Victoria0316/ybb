package com.bluemobi.ybb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.OrderMakeModel;
import com.bluemobi.ybb.network.request.AddToShopCartRequest;
import com.bluemobi.ybb.network.response.CommonResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.PreferencesService;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.RatioLayout;
import com.bumptech.glide.Glide;

import java.math.BigDecimal;

/**
 * Created by liufy on 2015/7/8.
 * P6-3加入购物车
 */
public class AddShopCarActivity  extends BaseActivity implements View.OnClickListener
{
    private Button btn_add_shopcar;
    private  TitleBarManager titleBarManager;
    private ImageView iv_add;
    private AddToShopCartRequest request;
    private RatioLayout rl_wrap_image;

//    private CommodityDetailBean commodityDetailBean;

    private TextView tv_search_item;

    private ImageView iv_image_bg;

    private  ImageView iv_minus;

    private TextView tv_charge;

    private String total;

    private RelativeLayout rl_shop;

    private TextView tv_shopcar_total;

    private double price;

    private int cartCounts;

    private EditText et_hint;

    private Button shop_buy_now;

    private int count =1;

    private OrderMakeModel orderMakeModel;

    private String attributeName;

    private String cartAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleBarManager = new TitleBarManager();
        titleBarManager.init(AddShopCarActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.str_shop_add_num, R.drawable.common_back, true);
        showLoadingPage(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        cartAmount = YbbApplication.getInstance().getCartAmount();
    }

    @Override
    protected void initBaseData() {
        showLoadingPage(false);
//        commodityDetailBean = (CommodityDetailBean) getIntent().getSerializableExtra("commodityDetailBean");
        orderMakeModel = (OrderMakeModel) getIntent().getSerializableExtra("item");
        attributeName =  getIntent().getStringExtra("attributeName");
        cartCounts =  PreferencesService.getInstance(mContext).getPreferencesInt("cartCounts");

        try {
            PreferencesService.getInstance(mContext).saveInt("cartCounts", cartCounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCarShopTotal(String price)
    {
        tv_shopcar_total.setText(price);
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        cartAmount = YbbApplication.getInstance().getCartAmount();

        View addShopcarView = inflater.inflate(R.layout.activity_add_shopcar,null);
        rl_shop = (RelativeLayout)addShopcarView. findViewById(R.id.rl_shop);
        btn_add_shopcar = (Button) addShopcarView.findViewById(R.id.btn_add_shopcar);
        iv_add = (ImageView) addShopcarView.findViewById(R.id.iv_add);
        et_hint  = (EditText)addShopcarView. findViewById(R.id.et_hint);
        et_hint.setText(String.valueOf(count));
        tv_search_item = (TextView)addShopcarView. findViewById(R.id.tv_search_item);
        tv_charge = (TextView)addShopcarView. findViewById(R.id.tv_charge);
        iv_image_bg = (ImageView) addShopcarView.findViewById(R.id.iv_image_bg);
        iv_minus= (ImageView) addShopcarView.findViewById(R.id.iv_minus);
        tv_shopcar_total = (TextView)addShopcarView.findViewById(R.id.tv_shopcar_total);
        tv_shopcar_total.setText(cartAmount);
        rl_wrap_image= (RatioLayout) addShopcarView.findViewById(R.id.rl_wrap_image);
        rl_wrap_image.setRatio(1.25185185f);
        rl_shop.setOnClickListener(this);
        Glide.with(this).load(orderMakeModel.getImg()).placeholder(R.drawable.temp_item).into(iv_image_bg);
        tv_search_item.setText(orderMakeModel.getName() + "(" + attributeName + ")");
        price = Double.parseDouble(orderMakeModel.getPrice());
        tv_charge.setText(String.valueOf(price));
        shopCart = (ImageView)addShopcarView. findViewById(R.id.iv_shop_car);
        setCarShopTotal(cartAmount);
        setShopCarNum(cartCounts, true);
        btn_add_shopcar.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        iv_minus.setOnClickListener(this);
        shop_buy_now = (Button)addShopcarView.findViewById(R.id.shop_buy_now);
        shop_buy_now.setOnClickListener(this);
        phone=(RelativeLayout)addShopcarView. findViewById(R.id.phone);
        phone.setOnClickListener(this);
        return addShopcarView;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onClick(View v)
    {
//        int num = Integer.parseInt(et_hint.getText().toString());
        Logger.e("wangzhijun", "COUNT->> " + count);
        switch (v.getId())
        {
            case R.id. btn_add_shopcar:
                setStartPoint(iv_add, count);
                setAnim();
                addCartRequest(String.valueOf(count));
//                addShopCarRequest(count, orderMakeModel.getId(), orderMakeModel.getReserveTime(), Constants.FOODS_TYPE,false);
                break;
            case R.id. iv_add:
                count++;
                et_hint.setText(String.valueOf(count));
                break;
            case R.id. iv_minus:
                if(count == 1){
                    break;
                }
                count--;
                et_hint.setText(String.valueOf(count));
                break;
            case R.id.rl_shop:
                Utils.moveTo(mContext,ShopCarActivity.class);
                break;
            case R.id.shop_buy_now:
                Intent intent = new Intent();
                intent.setClass(this, OrderMakeActivity.class);
                intent.putExtra("item", orderMakeModel);
                startActivity(intent);
                break;
        }
        orderMakeModel.setCount(String.valueOf(count));
    }

    private void addCartRequest(String countString) {
        AddToShopCartRequest request = new AddToShopCartRequest
                (
                        new Response.Listener<CommonResponse>() {
                            @Override
                            public void onResponse(CommonResponse response) {
                                //addShopCarAction(et_hint);
                                addShopCarAction(et_hint);
                                int totalCount = YbbApplication.getInstance().getCartCounts() + count;
                                BigDecimal total ;
                                total = new BigDecimal(orderMakeModel.getCount()).multiply(new BigDecimal(
                                        orderMakeModel.getPrice()));
                                total = total.add(new BigDecimal(YbbApplication.getInstance().getCartAmount()));
                                total = total.setScale(2, BigDecimal.ROUND_UP);
                                YbbApplication.getInstance().setCartAmount(String.valueOf(total.floatValue()));
                                tv_shopcar_total.setText(String.valueOf(total.floatValue()));
                                Utils.makeToastAndShow(mContext, "加入购物车成功");
                                Intent mIntent = new Intent(Constants.CART_COUNT);
                                mIntent.putExtra("count", totalCount);
                                mIntent.putExtra("orgPrice", String.valueOf(total.floatValue()));
                                //发送广播
                                sendBroadcast(mIntent);
                                YbbApplication.getInstance().setCartCounts(totalCount);

                            }
                        }, this);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        request.setProductId(orderMakeModel.getId());
        request.setProductNum(countString);
        request.setReserveTime(orderMakeModel.getReserveTime());
        request.setCategoryId(orderMakeModel.getCategoryId());
        request.setAttributeId(orderMakeModel.getAttributeId());
        WebUtils.doPost(request);

    }

    @Override
    public void clickBarleft() {
        finish();
    }







}
