package com.bluemobi.ybb.app;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.view.CustomSpinner;
import com.jauker.widget.BadgeView;

import java.util.List;

/**
 * Created by liufy on 2015/6/28.
 */
public class TitleBarManager implements View.OnClickListener,CustomSpinner.OnSnipperClickListener {

    /**
     * 中间标题
     */
    private TextView tv_title;
    /**
     * 右边标题
     */
    private TextView tv_title_right;
    /**
     * 左边返回按钮
     */
    private ImageView iv_back;

    private View il_search_bar;

    private View il_three_tab_titlebar;

    private View il_two_tab_titlebar;

    private View il_spinner;

    /**
     * 搜索bar 搜索框
     */
    private TextView et_search;

    /**
     * 搜索bar 右边按钮
     */
    private ImageView iv_home_hint;

    private BaseActivity activity;

    private RelativeLayout rl_nutritious_food;
    private RelativeLayout rl_patients_meals;
    private RelativeLayout rl_zero_point_meal;
    private TextView tv_nutritious_food;
    private TextView tv_patients_meals;
    private TextView tv_zero_point_meal;

    private RelativeLayout rl_medical_food;
    private RelativeLayout rl_zero_meals;
    private TextView tv_medical_food;
    private TextView tv_zero_meals;
    private ImageView iv_right;

    public CustomSpinner getCs_titlebar_spinner() {
        return cs_titlebar_spinner;
    }

    private CustomSpinner cs_titlebar_spinner;

    private ActionBar mSupportActionBar;

    public TitleBarManager() {

    }

    public void init(Activity activity, ActionBar supportActionBar) {
        initTitle(supportActionBar);
        supportActionBar.setCustomView(R.layout.include_titlebar);
        tv_title = (TextView) activity.findViewById(R.id.tv_title);
        tv_title_right = (TextView) activity.findViewById(R.id.tv_title_right);
        iv_back = (ImageView) activity.findViewById(R.id.iv_back);
        il_search_bar = activity.findViewById(R.id.il_search_bar);
        il_three_tab_titlebar = activity.findViewById(R.id.il_three_tab_titlebar);
        il_two_tab_titlebar = activity.findViewById(R.id.il_two_tab_titlebar);
        il_spinner = activity.findViewById(R.id.il_spinner);
        et_search = (TextView) activity.findViewById(R.id.et_search);
        iv_home_hint = (ImageView) activity.findViewById(R.id.iv_home_hint);

        rl_nutritious_food = (RelativeLayout) activity.findViewById(R.id.rl_nutritious_food);
        rl_patients_meals = (RelativeLayout) activity.findViewById(R.id.rl_patients_meals);
        rl_zero_point_meal = (RelativeLayout) activity.findViewById(R.id.rl_zero_point_meal);
        tv_nutritious_food = (TextView) activity.findViewById(R.id.tv_nutritious_food);
        tv_patients_meals = (TextView) activity.findViewById(R.id.tv_patients_meals);
        tv_zero_point_meal = (TextView) activity.findViewById(R.id.tv_zero_point_meal);


        rl_medical_food = (RelativeLayout) activity.findViewById(R.id.rl_medical_food);
        rl_zero_meals = (RelativeLayout) activity.findViewById(R.id.rl_zero_meals);
        tv_medical_food = (TextView) activity.findViewById(R.id.tv_medical_food);
        tv_zero_meals = (TextView) activity.findViewById(R.id.tv_zero_meals);
        iv_right = (ImageView) activity.findViewById(R.id.iv_right);

        cs_titlebar_spinner = (CustomSpinner) activity.findViewById(R.id.cs_titlebar_spinner);

        il_spinner.setVisibility(View.GONE);
        il_search_bar.setVisibility(View.GONE);
        il_three_tab_titlebar.setVisibility(View.GONE);
        il_two_tab_titlebar.setVisibility(View.GONE);
        setListener();
    }
    /**
     * 初始化 TitleView 中的控件
     *
     * @param activity
     */
    public void init(BaseActivity activity, ActionBar supportActionBar) {
        this.activity = activity;
        initTitle(supportActionBar);
        supportActionBar.setCustomView(R.layout.include_titlebar);
        tv_title = (TextView) activity.findViewById(R.id.tv_title);
        tv_title_right = (TextView) activity.findViewById(R.id.tv_title_right);
        iv_back = (ImageView) activity.findViewById(R.id.iv_back);
        il_search_bar = activity.findViewById(R.id.il_search_bar);
        il_three_tab_titlebar = activity.findViewById(R.id.il_three_tab_titlebar);
        il_two_tab_titlebar = activity.findViewById(R.id.il_two_tab_titlebar);
        il_spinner = activity.findViewById(R.id.il_spinner);
        et_search = (TextView) activity.findViewById(R.id.et_search);
        iv_home_hint = (ImageView) activity.findViewById(R.id.iv_home_hint);

        rl_nutritious_food = (RelativeLayout) activity.findViewById(R.id.rl_nutritious_food);
        rl_patients_meals = (RelativeLayout) activity.findViewById(R.id.rl_patients_meals);
        rl_zero_point_meal = (RelativeLayout) activity.findViewById(R.id.rl_zero_point_meal);
        tv_nutritious_food = (TextView) activity.findViewById(R.id.tv_nutritious_food);
        tv_patients_meals = (TextView) activity.findViewById(R.id.tv_patients_meals);
        tv_zero_point_meal = (TextView) activity.findViewById(R.id.tv_zero_point_meal);


        rl_medical_food = (RelativeLayout) activity.findViewById(R.id.rl_medical_food);
        rl_zero_meals = (RelativeLayout) activity.findViewById(R.id.rl_zero_meals);
        tv_medical_food = (TextView) activity.findViewById(R.id.tv_medical_food);
        tv_zero_meals = (TextView) activity.findViewById(R.id.tv_zero_meals);
        iv_right = (ImageView) activity.findViewById(R.id.iv_right);

        cs_titlebar_spinner = (CustomSpinner) activity.findViewById(R.id.cs_titlebar_spinner);

        il_spinner.setVisibility(View.GONE);
        il_search_bar.setVisibility(View.GONE);
        il_three_tab_titlebar.setVisibility(View.GONE);
        il_two_tab_titlebar.setVisibility(View.GONE);
        setListener();
    }

    public View getTwoTabLeftView() {
        if (rl_medical_food != null) {
            return rl_medical_food;
        } else {
            return (RelativeLayout) activity.findViewById(R.id.rl_medical_food);
        }
    }

    public View getTwoTabRightView() {
        if (rl_zero_meals != null) {
            return rl_zero_meals;
        } else {
            return (RelativeLayout) activity.findViewById(R.id.rl_zero_meals);
        }
    }

    public RelativeLayout getNutritiousFoodId() {
        if (rl_nutritious_food != null) {
            return rl_nutritious_food;
        } else {
            return (RelativeLayout) activity.findViewById(R.id.rl_nutritious_food);
        }

    }


    public RelativeLayout getPatientsmealsId() {
        if (rl_patients_meals != null) {
            return rl_patients_meals;
        } else {
            return (RelativeLayout) activity.findViewById(R.id.rl_patients_meals);
        }
    }

    public RelativeLayout getZeroPointMealId() {
        if (rl_zero_point_meal != null) {
            return rl_zero_point_meal;
        } else {
            return (RelativeLayout) activity.findViewById(R.id.rl_zero_point_meal);
        }
    }

    /**
     * 初始化titlebar
     */
    private void initTitle(ActionBar supportActionBar) {
        this.mSupportActionBar = supportActionBar;
        supportActionBar.setDisplayHomeAsUpEnabled(false);
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(false);
        supportActionBar.setDisplayShowCustomEnabled(true);
    }

    /**
     * 添加监听器
     */
    private void setListener() {
        iv_back.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
        il_search_bar.setOnClickListener(this);
        iv_home_hint.setOnClickListener(this);
        rl_nutritious_food.setOnClickListener(this);
        rl_patients_meals.setOnClickListener(this);
        rl_zero_point_meal.setOnClickListener(this);
        rl_medical_food.setOnClickListener(this);
        rl_zero_meals.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        cs_titlebar_spinner.setSnipperClickListener(this);
    }

    /**
     * 显示最普通的Titlebar 左边按钮，中间文字
     *
     * @param textID     中间的文字
     * @param leftSrcID
     * @param isShowBack 是否显示回退按钮 true 显示
     */
    public void showCommonTitleBar(int textID, int leftSrcID, boolean isShowBack) {
        tv_title.setText(textID);
        if (isShowBack) {
            iv_back.setImageResource(leftSrcID);
            iv_back.setVisibility(View.VISIBLE);
        } else {
            iv_back.setVisibility(View.GONE);
        }


        tv_title_right.setVisibility(View.INVISIBLE);
    }


    /**
     * 显示最普通的Titlebar 左边按钮，中间文字，右边文字
     */
    public void showTitleTextBar(int textID, int leftSrcID, int rightTextID) {
        tv_title.setText(textID);
        iv_back.setImageResource(leftSrcID);
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setText(rightTextID);

    }

    /**
     * P5 主页的titlebar 带搜索
     */
    public void showSearchTitleBar() {
        tv_title.setVisibility(View.GONE);
        iv_back.setVisibility(View.GONE);
        tv_title_right.setVisibility(View.GONE);
        il_search_bar.setVisibility(View.VISIBLE);


    }

   public void showThreeTabTitleBar(int leftSrcID) {
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setImageResource(leftSrcID);
        tv_title_right.setVisibility(View.GONE);
        il_three_tab_titlebar.setVisibility(View.VISIBLE);
    }

    /**
     * 显示2个tab页的切换
     * @param leftSrcID  左边的图片
     * @param rightSrcID 右边的图片
     */
    public void showTwoTabTitleBar(int leftSrcID,int rightSrcID,int leftTabText,int rightTabText) {

        iv_back.setBackgroundResource(leftSrcID);
        if (rightSrcID!=-1)
        {
            iv_right.setVisibility(View.VISIBLE);
            iv_right.setBackgroundResource(rightSrcID);
        }
        tv_title.setVisibility(View.GONE);
        tv_title_right.setVisibility(View.GONE);
        tv_medical_food.setText(leftTabText);
        tv_zero_meals.setText(rightTabText);
        il_two_tab_titlebar.setVisibility(View.VISIBLE);
    }

    public void showTwoTabTitleBarRightText(int leftSrcID,int strText,int leftTabText,int rightTabText) {
        iv_back.setBackgroundResource(leftSrcID);
        iv_back.setVisibility(View.GONE);
        iv_right.setVisibility(View.GONE);
        tv_title.setVisibility(View.GONE);
        tv_medical_food.setText(leftTabText);
        tv_zero_meals.setText(rightTabText);
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setText(strText);
        il_two_tab_titlebar.setVisibility(View.VISIBLE);
    }

    public void setRightText(int rightText)
    {
        tv_title_right.setText(rightText);

    }


    /**
     * 带spinner效果的titlebar
     * @param datas 下拉数据
     */
    public void showSpinnerTitlerBar(List<String> datas,int leftSrcID)
    {
        iv_back.setBackgroundResource(leftSrcID);
        iv_right.setVisibility(View.GONE);
        tv_title.setVisibility(View.GONE);
        il_spinner.setVisibility(View.VISIBLE);
        cs_titlebar_spinner.setDatas(datas);
        cs_titlebar_spinner.setSpinnerText();
    }
    BadgeView badgeView;
    public void setBadgeViewHint(int hint) {
        if(hint == -999){
            return;
        }
        if(badgeView == null){
            badgeView = new BadgeView(activity);
            badgeView.setTargetView(iv_home_hint);
            badgeView.setBackgroundResource(R.drawable.home_badgeview);
            badgeView.setBadgeGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            badgeView.setBadgeMargin(0, 0, 0, 10);
            badgeView.setBadgeCount(hint);
            badgeView.setTextSize(10);
        }
        badgeView.setBadgeCount(hint);
    }

    /**
     * 显示普通的Titlebar 左边按钮，中间文字，右边按钮
     */
    public void showTitleImageBar(int textID, int rightSrcID) {
        tv_title.setText(textID);
        iv_back.setVisibility(View.GONE);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(rightSrcID);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                onClickBarleft();
                break;
            case R.id.tv_title_right:
                onClickBarRight();
                break;

            case R.id.il_search_bar:
                onClickBarSearch();
                break;
            case R.id.iv_home_hint:
                onClickBarHint();
                break;

            case R.id.rl_nutritious_food:
                onClickleftTab();
                break;

            case R.id.rl_patients_meals:
                onClickMiddleTab();
                break;
            case R.id.rl_zero_point_meal:
                  onClickRightTab();
                break;

            case R.id.rl_medical_food:
                onClickTwoTableft();
                break;
            case R.id.rl_zero_meals:
                onClickTwoTabright();
                break;
            case R.id.iv_right:
                onClickImageRight();
                break;

        }
    }

    public void onClickImageRight() {
        (activity).clickImageRight();
    }

    public void onClickTwoTabright() {
        (activity).clickTwoTabright();
    }

    public void onClickTwoTableft() {

        (activity).clickTwoTableft();
    }


    /**
     * 点击标题栏左边的tab
     */
    public void onClickleftTab() {
        (activity).clickleftTab();
    }

    /**
     * 点击标题栏中间的tab
     */
    public void onClickMiddleTab() {
        (activity).clickMiddleTab();
    }

    /**
     * 点击标题栏右边的tab
     */
    public void onClickRightTab() {
        (activity).clickRightTab();
    }

    /**
     * 点击右边的bar按钮
     */
    public void onClickBarRight() {
        (activity).clickBarRight();
    }

    /**
     * 点击左边的bar按钮
     */
    public void onClickBarleft() {

        Log.e("tag", "onClickBarleft");

        (activity).clickBarleft();
    }

    /**
     * 点击中间搜索的bar按钮
     */
    public void onClickBarSearch() {
        (activity).clickBarSearch();
    }

    /**
     * 点击右边提示数量的bar按钮
     */
    public void onClickBarHint() {
        (activity).clickBarHint();
    }

    @Override
    public void OnSnipperClick(int position)
    {
        (activity).clickBarSpinner(position);
    }
}