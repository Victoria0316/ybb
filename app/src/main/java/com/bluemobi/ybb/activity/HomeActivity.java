package com.bluemobi.ybb.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.DbManager;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.UpdateChecker;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.db.entity.Message;
import com.bluemobi.ybb.fragment.CategoryFragment;
import com.bluemobi.ybb.fragment.HomeFragment;
import com.bluemobi.ybb.fragment.MineFragment;
import com.bluemobi.ybb.fragment.ShoppingCartFragment;
import com.bluemobi.ybb.network.request.TrunInorOutRequest;
import com.bluemobi.ybb.network.request.UploadCidRequest;
import com.bluemobi.ybb.network.response.TrunInorOutResponse;
import com.bluemobi.ybb.network.response.UploadCidResponse;
import com.bluemobi.ybb.util.AlipayUtil;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.igexin.sdk.PushManager;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;


/**
 * Created by liufy on 2015/6/29.
 * P5首页
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener
{

    /**
     * 首页tab页
     */
    private HomeFragment homeFragment;

    /**
     * 类目tab页
     */
    private CategoryFragment categoryFragment;

    /**
     * 购物车tab页
     */
    private ShoppingCartFragment shoppingCartFragment;

    /**
     * 我的tab页
     */
    private MineFragment mineFragment;

    /**
     * 主页界面布局
     */
    private View homeLayout;

    /**
     * 类目界面布局
     */
    private View categoryLayout;

    /**
     * 购物车界面布局
     */
    private View shoppingCartLayout;

    /**
     * 我的界面布局
     */
    private View mineLayout;

    /**
     * 在Tab布局上显示主页图标的控件
     */
    private ImageView iv_home;

    /**
     * 在Tab布局上显示类目图标的控件
     */
    private ImageView iv_category;

    /**
     * 在Tab布局上显示购物车图标的控件
     */
    private ImageView iv_shopping;

    /**
     * 在Tab布局上显示我的控件
     */
    private ImageView iv_mine;

    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    private FrameLayout fl_content;

    public TitleBarManager titleBarManager;
    /**
     * record last click back time
     */
    private long exitTime;

    public static HomeActivity instance;

    public boolean isHiddChange ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance=this;
        titleBarManager = new TitleBarManager();
        titleBarManager.init(HomeActivity.this,getSupportActionBar());
        titleBarManager.showSearchTitleBar();
        showLoadingPage(false);
        UpdateChecker checker= UpdateChecker.getInstance(this);
        checker.check(false);
        PushManager.getInstance().initialize(this.getApplicationContext());
    }

    public static HomeActivity getInstance()
    {
        return instance;
    }

    public TitleBarManager getTitleBarManager() {
        return titleBarManager;
    }

    @Override
    protected void initBaseData() {

    }


    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.activity_home, null);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        homeLayout = view.findViewById(R.id.rl_home);
        categoryLayout = view.findViewById(R.id.rl_category);
        shoppingCartLayout = view.findViewById(R.id.rl_shopping);
        mineLayout = view.findViewById(R.id.rl_mine);

        iv_home = (ImageView) view.findViewById(R.id.iv_home);
        iv_category = (ImageView) view.findViewById(R.id.iv_category);
        iv_shopping = (ImageView) view.findViewById(R.id.iv_shopping);
        iv_mine = (ImageView) view.findViewById(R.id.iv_mine);

        homeLayout.setOnClickListener(this);
        categoryLayout.setOnClickListener(this);
        shoppingCartLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);

        DbUtils db = DbManager.getInstance(mContext).getDefaultDbUtils();
        String num= null;
        try {
            num = String.valueOf(db.count(Selector.from(Message.class).where("isread", "=", "0")));
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (titleBarManager!=null)
            Log.e("numcreateSuccessView","-----------------------"+num);
        titleBarManager.setBadgeViewHint(Integer.parseInt(num));
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarSearch() {
        if(YbbApplication.role_bh.equals(YbbApplication.getInstance().getMyUserInfo().getTypeId())){
            Utils.moveTo(mContext, HomeSearchActivity.class);
        }else if(YbbApplication.role_yh.equals(YbbApplication.getInstance().getMyUserInfo().getTypeId())){
            Intent intent = new Intent(mContext,HomeSearchActivity.class);
            intent.putExtra("From","P7");
            startActivity(intent);
        }
//        Utils.moveTo(mContext,HomeSearchActivity.class);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.rl_home:
                setTabSelection(0);
                break;
            case R.id.rl_category:
                setTabSelection(1);
                break;
            case R.id.rl_shopping:
                setTabSelection(2);
                break;
            case R.id.rl_mine:
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    @Override
    public void clickBarHint() {

        Utils.moveTo(mContext, MessageActivity.class);
    }



    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     */
    private void setTabSelection(int index) {
        isHiddChange = false;
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        titleBarManager = new TitleBarManager();
        titleBarManager.init(HomeActivity.this, getSupportActionBar());
        switch (index) {
            case 0:

                titleBarManager.showSearchTitleBar();
                titleBarManager.setBadgeViewHint(-999);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.common_blue)));
                iv_home.setImageResource(R.drawable.home_p);
                if (homeFragment == null)
                {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fl_content, homeFragment);
                    //transaction.add()
                } else
                {
                    transaction.show(homeFragment);

                }
                break;
            case 1:
                titleBarManager.showCommonTitleBar(R.string.str_h_category,R.drawable.common_back,false);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.common_blue)));
                iv_category.setImageResource(R.drawable.category_p);
                if (categoryFragment == null) {
                    categoryFragment = new CategoryFragment();

                    transaction.add(R.id.fl_content, categoryFragment);
                } else {
                    transaction.show(categoryFragment);
                }
                break;
            case 2:

                titleBarManager.showTwoTabTitleBarRightText(R.drawable.common_back, R.string.str_edit, R.string.str_shop_meals, R.string.str_shop_goods);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.common_blue)));

                iv_shopping.setImageResource(R.drawable.shopping_cart_p);
                    shoppingCartFragment = new ShoppingCartFragment();
                    transaction.add(R.id.fl_content, shoppingCartFragment);

                break;
            case 3:

                titleBarManager.showTitleImageBar(R.string.str_h_mine, R.drawable.setting);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                //titleBarManager.setActionBarbg(true);
                isHiddChange = true;
                iv_mine.setImageResource(R.drawable.mine_p);

                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fl_content, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commit();
    }

    public TitleBarManager getActionTitleBar()
    {
        return titleBarManager;
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        iv_home.setImageResource(R.drawable.home_n);
        iv_category.setImageResource(R.drawable.category_n);
        iv_shopping.setImageResource(R.drawable.shopping_cart_n);
        iv_mine.setImageResource(R.drawable.mine_n);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (categoryFragment != null) {
            transaction.hide(categoryFragment);
        }
        if (shoppingCartFragment != null) {
            transaction.remove(shoppingCartFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }


    @Override
    public void clickTwoTableft(){

        shoppingCartFragment.setTabSelection(0);

    }

    @Override
    public void clickTwoTabright() {

        shoppingCartFragment.setTabSelection(1);

    }

    @Override
    public void clickBarleft() {
        finish();
    }


    private  boolean isChangeRightText = false;

    @Override
    public void clickBarRight() {

        isChangeRightText =!isChangeRightText;
        if (isChangeRightText)
        {
            titleBarManager.setRightText(R.string.str_finish);
        }
        else
        {
            titleBarManager.setRightText(R.string.str_edit);
        }

        shoppingCartFragment.notifyDataSetChanged();
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 3000) {
                Toast.makeText(getApplicationContext(), "再按一次将退出应用",
                        Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                YbbApplication.getInstance().setMyUserInfo(null);
                this.finish();
            }
        }
        return true;
    }

    public  void uploadCid(String cid){
        UploadCidRequest request = new UploadCidRequest(new Response.Listener<UploadCidResponse>() {
            @Override
            public void onResponse(UploadCidResponse response) {
                Utils.closeDialog();
                if (response != null && response.getStatus() == 0) {// success
                    //Toast.makeText(HomeActivity.this, "上传cid成功", Toast.LENGTH_SHORT).show();
                } else {// failed
                    Log.e("error", "error");
                }
            }
        }, HomeActivity.this);
        request.setClientId(cid);
        request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
        WebUtils.doPost(request);
    }

    @Override
    public void clickImageRight() {
        //TODO
        Utils.moveTo(mContext, SetUpActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DbUtils db = DbManager.getInstance(mContext).getDefaultDbUtils();
        String num= "0";
        try {
            num = String.valueOf(db.count(Selector.from(Message.class).where("isread", "=", "0")));
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (titleBarManager!=null)
            Log.e("nummmmmmmmmmmmmmmm","+"+num);
            titleBarManager.setBadgeViewHint(Integer.parseInt(num));
    }
}
