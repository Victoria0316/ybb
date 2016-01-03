package com.bluemobi.ybb.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.fragment.SearchFoodsFragment;
import com.bluemobi.ybb.fragment.SearchGoodsFragment;
import com.bluemobi.ybb.util.KeyBoardUtils;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.view.LoadingPage;

/**
 * Created by liufy on 2015/6/30.
 * P5-1 搜索
 */
public class HomeSearchActivity extends BaseActivity implements View.OnClickListener
{


    private SearchFoodsFragment searchFoodsFragment;

    private SearchGoodsFragment searchGoodsFragment;

    private View searchFoodsLayout;

    private View searchGoodsLayout;

    private ImageView iv_tab_goods;

    private ImageView iv_tab_foods;

    private TextView tv_tab_goods;

    private TextView tv_tab_foods;

    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    private String strFrom;

    private LinearLayout ll_tab;

    public EditText et_search;

    private  boolean flag=false;//标识显示哪个fragment  false 显示商品  true 餐品
    private  ImageView delete;

    private RelativeLayout rl_shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarManager  titleBarManager = new TitleBarManager();
        titleBarManager.init(HomeSearchActivity.this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.search, R.drawable.common_back, true);
        showLoadingPage(false);

    }

    @Override
    protected void initBaseData() {
        Intent intent = getIntent();
        strFrom = intent.getStringExtra("From");
    }

    @Override
    public View createSuccessView(LayoutInflater inflater)
    {
        View view = inflater.inflate(R.layout.activity_home_search, null);
        rl_shop = (RelativeLayout) view.findViewById(R.id.rl_shop);
        rl_shop.setOnClickListener(this);
        shopCart=(ImageView)view.findViewById(R.id.iv_shop_car);
        phone=(RelativeLayout)view. findViewById(R.id.phone);
        phone.setOnClickListener(this);
        et_search=(EditText) view.findViewById(R.id.et_search);
        delete=(ImageView)view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });
        KeyBoardUtils.openKeybord(et_search, this);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                // Auto-generated method stub
                if ((arg1 == 0 || arg1 == 3) && arg2 != null) {
                    if (StringUtils.isEmpty(et_search.getText().toString())) {
                        Toast.makeText(mContext, "您输入的内容为空", Toast.LENGTH_SHORT).show();
                    }else {

                        if ("P7".equals(strFrom))
                        {
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            hideFragments(transaction);
                            if (searchFoodsFragment == null) {
                                searchFoodsFragment = new SearchFoodsFragment();
                                transaction.add(R.id.fl_content, searchFoodsFragment);
                            } else {
                                transaction.show(searchFoodsFragment);
                            }
                            transaction.commit();
                            searchFoodsFragment.getPage(2);
                        }
                        else
                        {
                            if (flag){//餐品
                                searchFoodsFragment.getPage(2);
                            }else {//商品
                                //searchGoodsFragment。。。。。
                            }
                        }

                    }
                }
                return false;
            }
        });
        searchGoodsLayout = view.findViewById(R.id.rl_tab_goods);
        searchFoodsLayout = view.findViewById(R.id.rl_tab_foods);
        ll_tab = (LinearLayout) view.findViewById(R.id.ll_tab);
        if ("P7".equals(strFrom))
        {
            ll_tab.setVisibility(View.GONE);
        }
        else
        {
            ll_tab.setVisibility(View.VISIBLE);
        }
        iv_tab_goods = (ImageView)view.findViewById(R.id.iv_tab_goods);
        iv_tab_foods = (ImageView)view. findViewById(R.id.iv_tab_foods);
        tv_tab_goods = (TextView)view. findViewById(R.id.tv_tab_goods);
        tv_tab_foods = (TextView) view.findViewById(R.id.tv_tab_foods);
        searchGoodsLayout.setOnClickListener(this);
        searchFoodsLayout.setOnClickListener(this);
        fragmentManager = getFragmentManager();

        if ("P7".equals(strFrom)) {
            setTabSelection(1);
        }
        else
        {
            setTabSelection(0);
        }

        return view;
    }

    @Override
    protected LoadingPage.LoadResult load()
    {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void clickBarleft() {
        finish();
    }

    @Override
    public void onClick(View v)
    {
         switch (v.getId())
         {
             case R.id.rl_tab_goods:
                 setTabSelection(0);
                 break;

             case R.id.rl_tab_foods:
                 setTabSelection(1);
                 break;
             case R.id.rl_shop:
                 Utils.moveTo(mContext, ShopCarActivity.class);
                 break;
             case R.id.phone:
                 call();
                 break;

         }
    }


    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                flag=false;
                iv_tab_goods.setVisibility(View.VISIBLE);
                tv_tab_goods.setTextColor(getResources().getColor(R.color.common_blue));
                if (searchGoodsFragment == null) {
                    searchGoodsFragment = new SearchGoodsFragment();
                    transaction.add(R.id.fl_content, searchGoodsFragment);
                } else {
                    transaction.show(searchGoodsFragment);
                }
                break;
            case 1:
                flag=true;
                iv_tab_foods.setVisibility(View.VISIBLE);
                tv_tab_foods.setTextColor(getResources().getColor(R.color.common_blue));
                if (searchFoodsFragment == null) {
                    searchFoodsFragment = new SearchFoodsFragment();
                    transaction.add(R.id.fl_content, searchFoodsFragment);
                } else {
                    transaction.show(searchFoodsFragment);
                }
////                //TODO test
//                delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        searchFoodsFragment.getPage(2);
//                    }
//                });

                break;

        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        iv_tab_goods.setVisibility(View.INVISIBLE);
        tv_tab_goods.setTextColor(getResources().getColor(R.color.common_InputBox));
        iv_tab_foods.setVisibility(View.INVISIBLE);
        tv_tab_foods.setTextColor(getResources().getColor(R.color.common_InputBox));

    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (searchGoodsFragment != null) {
            transaction.hide(searchGoodsFragment);
        }
        if (searchFoodsFragment != null) {
            transaction.hide(searchFoodsFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
     /*   InputMethodManager imm =  (InputMethodManager).getSystemService(
                NPUT_METHOD_SERVICE);
        if(imm != null) {

            imm.hideSoftInputFromWindow(.getWindow().getDecorView().getWindowToken(),
                    0);
        }*/
    }
}
