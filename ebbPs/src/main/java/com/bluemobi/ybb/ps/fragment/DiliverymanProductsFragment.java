package com.bluemobi.ybb.ps.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.base.crop.Crop;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.base.utils.StringUtils;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.base.utils.WebUtils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.activity.DiliverymanMainActivity;
import com.bluemobi.ybb.ps.activity.RefundMealReasonActivity;
import com.bluemobi.ybb.ps.adapter.CommonAdapter;
import com.bluemobi.ybb.ps.adapter.ViewHolder;
import com.bluemobi.ybb.ps.app.YbbPsApplication;
import com.bluemobi.ybb.ps.callback.GetPushRefreshData;
import com.bluemobi.ybb.ps.network.model.ParamModel;
import com.bluemobi.ybb.ps.network.model.PeiSongListModel;
import com.bluemobi.ybb.ps.network.model.PeiSongModel;
import com.bluemobi.ybb.ps.network.request.ConfirmSendedRequest;
import com.bluemobi.ybb.ps.network.request.ParamRequest;
import com.bluemobi.ybb.ps.network.request.PeiSongDanRequest;
import com.bluemobi.ybb.ps.network.response.ConfirmSendedResponse;
import com.bluemobi.ybb.ps.network.response.ParamResponse;
import com.bluemobi.ybb.ps.network.response.PeiSongDanResponse;
import com.bluemobi.ybb.ps.util.Constants;
import com.bluemobi.ybb.ps.view.CustomSpinner;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gaoyn on 2015/7/9.
 *
 * p23 餐品
 */
public class DiliverymanProductsFragment extends BaseFragment implements GetPushRefreshData.RefreshListener {

    private List<String> cacheAttributeList = new ArrayList<String>();//餐次cahche
    private List<String> cacheAttributeValueList = new ArrayList<String>();//餐次cahche

    private List<String> timeList = new ArrayList<String>();
    private List<String> attributeList = new ArrayList<String>();//餐次
    private List<String> attributeValueList = new ArrayList<String>();

    private List<String> powerList = new ArrayList<String>();
    private List<String> powerValueList = new ArrayList<String>();

    private List<String> bhTypeList = new ArrayList<String>();
    private List<String> bhTypeValueList = new ArrayList<String>();

    private List<String> yhTypeList = new ArrayList<String>();
    private List<String> yhTypeValueList = new ArrayList<String>();

    private CustomSpinner products_date;
    private CustomSpinner products_ID;
    private CustomSpinner products_type;
    private CustomSpinner products_num;

    public CommonAdapter<PeiSongModel> adapter;

    public List<PeiSongModel> lv = new ArrayList<PeiSongModel>();

    public DiliverymanMainActivity mActivity;

    private boolean flag=false;//

    private GetPushRefreshData mReceiver;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (DiliverymanMainActivity) activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mReceiver) {
            mActivity.unregisterReceiver(mReceiver);
        }

    }
    @Override
    public void initData(Bundle savedInstanceState) {
        ((DiliverymanMainActivity)getActivity()).getTitleBarManager().showActionBar();
        isShowLoadPage = false;
        mReceiver =  new GetPushRefreshData(this);
        mActivity.registerReceiver( mReceiver, new IntentFilter(GetPushRefreshData.ACTION_NAME));
        ParamRequest request = new ParamRequest(new Response.Listener<ParamResponse>() {
            @Override
            public void onResponse(ParamResponse response) {
                if(response != null && response.getStatus() ==0){
                    YbbPsApplication.getInstance().setParamModel(response.getData());
                    Logger.e("wangzhijun", "param success");
                    String temp = response.getData().getCanteen_id();
                    YbbPsApplication.getInstance().setCanteenId(temp);
                    String startTimeStr = YbbPsApplication.getInstance().getParamModel().getStartAndEndTime_Logistics().getEntry().get(0).getValue().getValue();
                    String endTimeStr = "";//.getValue();//2015-07-1111:00:00
                    if (YbbPsApplication.getInstance().getParamModel().getStartAndEndTime_Logistics().getEntry().get(1).getValue()!=null){
                        endTimeStr=YbbPsApplication.getInstance().getParamModel().getStartAndEndTime_Logistics().getEntry().get(1).getValue().getValue();

                    }
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    startTimeStr = startTimeStr.substring(0, 10);
                    Date endTimeDate = null;
                    if (StringUtils.isNotEmpty(endTimeStr)&& !"null".equals(endTimeStr)){
                        endTimeStr = endTimeStr.substring(0, 10);
                        try {
                            endTimeDate = format.parse(endTimeStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }else {
                        endTimeDate=new Date();
                    }
                    Date startTimeDate = null;
                    try {
                        startTimeDate = format.parse(startTimeStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (startTimeDate.getTime() < (new Date()).getTime()) {
                        startTimeDate = new Date();
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(startTimeDate);

                    while (calendar.getTime().before(endTimeDate)||calendar.getTime().equals(endTimeDate)){
                        timeList.add(format.format(calendar.getTime()));
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    timeList.add(endTimeStr);

                    List<ParamModel.ProductAttributeEntity> attributeEntities =
                            YbbPsApplication.getInstance().getParamModel().getProductAttributeInfoList();
                    for (ParamModel.ProductAttributeEntity item : attributeEntities) {
                        attributeList.add(item.getAttributeName());
                        attributeValueList.add(item.getId());
                    }
//                    cacheAttributeList.addAll(attributeList);
//                    cacheAttributeValueList.addAll(attributeValueList);

                    powerList.add(YbbPsApplication.getInstance().role_bh_name);
                    powerList.add(YbbPsApplication.getInstance().role_yh_name);
                    powerValueList.add(YbbPsApplication.getInstance().role_bh);
                    powerValueList.add(YbbPsApplication.getInstance().role_yh);

                    products_date.setDatas(timeList);
                    products_date.setValues(timeList);
                    products_ID.setDatas(powerList);
                    products_ID.setValues(powerValueList);

                    bhTypeList.add("营养餐");
                    bhTypeValueList.add("1");
                    bhTypeList.add("病患套餐");
                    bhTypeValueList.add("4");

                    bhTypeList.add("零点餐");
                    bhTypeValueList.add("2");

                    bhTypeList.add("医护套餐");
                    bhTypeValueList.add("3");

                    if (YbbPsApplication.getInstance().role_bh_name.equals(powerList.get(0))){//binghuan
                        bhTypeList.remove(3);
                        bhTypeValueList.remove(3);
                    }else if(YbbPsApplication.getInstance().role_yh_name.equals(powerList.get(0))){
                        bhTypeList.remove(0);
                        bhTypeValueList.remove(0);
                        bhTypeList.remove(1);
                        bhTypeValueList.remove(1);
                    }

                    products_type.setDatas(bhTypeList);
                    products_type.setValues(bhTypeValueList);

                    products_num.setDatas(attributeList);
                    products_num.setValues(attributeValueList);

                    products_ID.setSpinnerText();
                    products_date.setSpinnerText();
                    products_type.setSpinnerText();
                    products_num.setSpinnerText();
                    getPage(LOAD_REFRESH);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setUserId(YbbPsApplication.getInstance().getMyUserInfo().getUserId());
        WebUtils.doPost(request);

    }

    @Override
    protected boolean getPage(int type) {
        if(!super.getPage(type)){
            return false;
        }
        connectToServer();
        return true;

    }

    private void connectToServer()
    {
        PeiSongDanRequest request = new PeiSongDanRequest(new Response.Listener<PeiSongDanResponse>() {
            @Override
            public void onResponse(PeiSongDanResponse response) {
                if (response != null && response.getStatus() == 0) {
                        flag=false;
                        plv_refresh.onRefreshComplete();
                        showListData(response.getData());
                }else {
                    plv_refresh.onRefreshComplete();
                    if (flag) {
                        lv.clear();
                        if(adapter!=null){
                            adapter.notifyDataSetChanged();
                        }
                    }
                    Toast.makeText(mContext, response.getContent(), Toast.LENGTH_SHORT).show();
                }
            }
        },  (Response.ErrorListener)mActivity);
        request.setHandleCustomErr(false);
        request.setCurrentnum(Constants.CURRENTNUMBASE + "");
        request.setCurrentpage(curPage + "");
        request.setCategoryId(Integer.parseInt(products_type.getValue()));
        if ("2".equals(products_type.getValue())){
            request.setAttributeId("");
        }else {
            request.setAttributeId(products_num.getValue());
        }
        request.setCanteenId(YbbPsApplication.getInstance().getCanteenId());
        request.setAdminTypeId(products_ID.getValue());
        request.setDeliverymanId(YbbPsApplication.getInstance().getMyUserInfo().getUserId());
        request.setDistributionType(-1);
        request.setDistributionTypeList("0,1,2,3,4,5");
        request.setQueryTime(products_date.getValue());
        WebUtils.doPost(request);
    }


    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View ProductsView = inflater.inflate(R.layout.fragment_products,null);

        plv_refresh = (PullToRefreshListView) ProductsView.findViewById(R.id.plv_refresh);
        initPullToRefresh(plv_refresh);

        products_date = (CustomSpinner) ProductsView.findViewById(R.id.products_date);
        products_ID = (CustomSpinner) ProductsView.findViewById(R.id.products_ID);
        products_type = (CustomSpinner) ProductsView.findViewById(R.id.products_type);
        products_num = (CustomSpinner) ProductsView.findViewById(R.id.products_num);

        products_date.setSpinnerText();
        products_ID.setSpinnerText();
        products_type.setSpinnerText();
        products_num.setSpinnerText();

        products_date.setSelectLine();
        products_date.setOnSwitchTabClickListener(new CustomSpinner.OnSwitchTabClickListener() {
            @Override
            public void OnSwitchTabClick() {
                clearSelectLine();
                products_date.setSelectLine();
            }
        });
        products_date.setSnipperClickListener(new CustomSpinner.OnSnipperClickListener() {
            @Override
            public void OnSnipperClick(int position) {
                curPage = 0;
                flag = true;
                setAttributteData();
                connectToServer();
            }
        });
        products_ID.setOnSwitchTabClickListener(new CustomSpinner.OnSwitchTabClickListener() {
            @Override
            public void OnSwitchTabClick() {
                clearSelectLine();
                products_ID.setSelectLine();
            }
        });
        products_ID.setSnipperClickListener(new CustomSpinner.OnSnipperClickListener() {
            @Override
            public void OnSnipperClick(int position) {
                curPage = 0;
                flag = true;
                products_type.current = 0;
                bhTypeList.clear();
                bhTypeValueList.clear();

                bhTypeList.add("营养餐");
                bhTypeValueList.add("1");
                bhTypeList.add("病患套餐");
                bhTypeValueList.add("4");

                bhTypeList.add("零点餐");
                bhTypeValueList.add("2");

                bhTypeList.add("医护套餐");
                bhTypeValueList.add("3");

                if (YbbPsApplication.getInstance().role_bh.equals(products_ID.getValue())) {//binghuan
                    bhTypeList.remove(3);
                    bhTypeValueList.remove(3);
                } else if (YbbPsApplication.getInstance().role_yh.equals(products_ID.getValue())) {//yihuan
                    bhTypeList.remove(0);
                    bhTypeValueList.remove(0);
                    bhTypeList.remove(1);
                    bhTypeValueList.remove(1);
                }
                products_type.setDatas(bhTypeList);
                products_type.setValues(bhTypeValueList);
                products_type.setSpinnerText();
                setAttributteData();
                connectToServer();
            }
        });
        //套餐类型（1营养餐2零点餐3医护套餐4医患套餐）
        products_type.setOnSwitchTabClickListener(new CustomSpinner.OnSwitchTabClickListener() {
            @Override
            public void OnSwitchTabClick() {
                clearSelectLine();
                products_type.setSelectLine();
            }
        });
        products_type.setSnipperClickListener(new CustomSpinner.OnSnipperClickListener() {
            @Override
            public void OnSnipperClick(int position) {
                curPage = 0;
                flag = true;
//                if ("2".equals(products_type.getValue())) {//零点餐
//                    products_num.setDatas(cacheAttributeList);
//                    products_num.setValues(cacheAttributeValueList);
//                    products_num.tv_label.setText("");
//                    products_num.setEnabled(false);
//                } else {
//                    products_num.setDatas(attributeList);
//                    products_num.setValues(attributeValueList);
//                    products_num.setSpinnerText();
//                    products_num.setEnabled(true);
//                }
                setAttributteData();
                connectToServer();
            }
        });
        //canci 餐次
        products_num.setOnSwitchTabClickListener(new CustomSpinner.OnSwitchTabClickListener() {
            @Override
            public void OnSwitchTabClick() {
                clearSelectLine();
                products_num.setSelectLine();
            }
        });
        products_num.setSnipperClickListener(new CustomSpinner.OnSnipperClickListener() {
            @Override
            public void OnSnipperClick(int position) {
                curPage = 0;
                flag = true;
//                setAttributteData();
                connectToServer();
            }
        });


        return ProductsView;
    }

    private  void setAttributteData(){
        if ("2".equals(products_type.getValue())) {//零点餐
            products_num.setDatas(cacheAttributeList);
            products_num.setValues(cacheAttributeValueList);
            products_num.tv_label.setText("");
            products_num.setEnabled(false);
        } else {
            products_num.setDatas(attributeList);
            products_num.setValues(attributeValueList);
            products_num.setSpinnerText();
            products_num.setEnabled(true);
        }
    }
    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


    private  void clearSelectLine()
    {
        products_date.clearSelectLine();
        products_ID.clearSelectLine();
        products_type.clearSelectLine();
        products_num.clearSelectLine();
    }

    /**
     * 显示列表数据
     */
    private void showListData(PeiSongListModel list) {
        if (list == null) {
            return;
        }
        if (list.getInfo().size() == 0) {
            return;
        }
        if(flag){
            lv.clear();
        }
        if (list.getCurrentpage().equals("1")) {
            lv.clear();
            lv.addAll(list.getInfo());
        } else {
            lv.addAll(list.getInfo());
        }
        if (adapter == null) {
            plv_refresh.setAdapter(adapter = new CommonAdapter<PeiSongModel>(mContext, lv, R.layout.lv_products) {
                @Override
                public void convert(ViewHolder helper, final PeiSongModel item) {

                    TextView ID = (TextView) helper.getView(R.id.ID);
                    StringBuffer sb= new StringBuffer();
                    if(StringUtils.isEmpty(item.getCityName())||"null".equals(item.getCityName())){
                    }else {
                        sb.append(item.getCityName());
                    }
                    if(StringUtils.isEmpty(item.getDistrictName())||"null".equals(item.getDistrictName())){
                    }else {
                        sb.append(item.getDistrictName());
                    }
                    if(StringUtils.isNotEmpty(sb.toString())){
                        ID.setText(sb.toString());
                    }

                    TextView content= (TextView) helper.getView(R.id.content);
                    content.setText(item.getProductAndNum().replaceAll(",","  "));
                    TextView zaocan= (TextView) helper.getView(R.id.zaocan);
                    zaocan.setText(item.getAllNames());
                   final  TextView return_button = (TextView) helper.getView(R.id.return_button);
                    final TextView complete = (TextView) helper.getView(R.id.complete);
                    return_button.setTag(item.getId());
                    complete.setTag(item.getId());
                    int p=lv.indexOf(item);
                    return_button.setTag(R.id.content,p);
                    if("0".equals(item.getDistributionType())){//  "distributionType": "配送状态（0未接单，1接单，2送货中，3已送到）",
                        complete.setText("确认配送");
                        complete.setTextColor(getResources().getColor(R.color.common_blue));
                        complete.setBackgroundResource(R.drawable.button_background);
                        return_button.setVisibility(View.VISIBLE);
                        complete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String id=String.valueOf(view.getTag());
                                ConfirmSendedRequest request= new ConfirmSendedRequest(new Response.Listener<ConfirmSendedResponse>() {
                                    @Override
                                    public void onResponse(ConfirmSendedResponse response) {
                                        Utils.closeDialog();
                                        if(response != null && response.getStatus() ==0){
                                            Toast.makeText(getActivity(), "确认配送成功", Toast.LENGTH_SHORT).show();
                                            complete.setText("配送中");
                                            complete.setBackgroundColor(getResources().getColor(R.color.white));
                                            complete.setTextColor(getResources().getColor(R.color.common_black));
                                            return_button.setVisibility(View.GONE);
                                            item.setDistributionType("2");
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                request.setId(id);
                                Utils.showProgressDialog(getActivity());
                                WebUtils.doPost(request);
                            }
                        });
                    }else if ("1".equals(item.getDistributionType())||"2".equals(item.getDistributionType())){
                        complete.setText("配送中");
                        complete.setBackgroundColor(getResources().getColor(R.color.white));
                        complete.setTextColor(getResources().getColor(R.color.common_black));
                        return_button.setVisibility(View.GONE);
                    }else if ("3".equals(item.getDistributionType())){
                        complete.setText("配送完成");
                        complete.setBackgroundColor(getResources().getColor(R.color.white));
                        complete.setTextColor(getResources().getColor(R.color.common_black));
                        return_button.setVisibility(View.GONE);
                    }else if ("4".equals(item.getDistributionType())){
                        complete.setText("退餐完成");
                        complete.setBackgroundColor(getResources().getColor(R.color.white));
                        complete.setTextColor(getResources().getColor(R.color.common_black));
                        return_button.setVisibility(View.GONE);
                    }else if ("5".equals(item.getDistributionType())){
                        complete.setText("退餐中");
                        complete.setBackgroundColor(getResources().getColor(R.color.white));
                        complete.setTextColor(getResources().getColor(R.color.common_black));
                        return_button.setVisibility(View.GONE);
                    }
                    return_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i= new Intent(getActivity(),RefundMealReasonActivity.class);
                            i.putExtra("id", String.valueOf(view.getTag()));
                            i.putExtra("pos",(Integer) view.getTag(R.id.content));
                            getActivity().startActivityForResult(i,200);
                        }
                    });
                }
            });
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refreshData() {
        curPage=0;
        flag = true;
        connectToServer();
    }
}
