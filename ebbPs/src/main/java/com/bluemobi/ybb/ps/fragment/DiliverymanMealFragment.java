package com.bluemobi.ybb.ps.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluemobi.base.utils.StringUtils;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.base.utils.WebUtils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.YbbPsApplication;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.network.model.PatientModel;
import com.bluemobi.ybb.ps.network.request.PatientsListRequest;
import com.bluemobi.ybb.ps.network.response.PatientsListResponse;
import com.bluemobi.ybb.ps.util.CharacterParser;
import com.bluemobi.ybb.ps.util.PinyinComparator;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.bluemobi.ybb.ps.view.OnTouchingLetterChangedListener;
import com.bluemobi.ybb.ps.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by gaoyn on 2015/7/9.
 * <p/>
 * p32 代点餐
 */
public class DiliverymanMealFragment extends BaseFragment {

    private BaseActivity baseActivity;

    private ListView mListView;

    private List<PatientModel> dataLists = new ArrayList<PatientModel>();
    private PatientsAdapter mAdapter;
    private SideBar sideBar;
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        baseActivity = (BaseActivity) activity;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        isShowLoadPage = false;
    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {

        View MealView = inflater.inflate(R.layout.fragment_meal, null);
        mListView = (ListView) MealView.findViewById(R.id.listView);
        sideBar = (SideBar) MealView.findViewById(R.id.sideBar);
        request();
        return MealView;
    }

    private void request() {
        PatientsListRequest request = new PatientsListRequest(new Response.Listener<PatientsListResponse>() {
            @Override
            public void onResponse(PatientsListResponse response) {
                if (response != null && response.getStatus() == 0) {
                    dataLists.clear();
                    dataLists.addAll(response.getData());
                    prepare();
                    mAdapter = new PatientsAdapter();

                    mListView.setAdapter(mAdapter);
                    //设置右侧触摸监听
                    sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
                        @Override
                        public void onTouchingLetterChanged(String s, String dis) {
                            //该字母首次出现的位置
                            int position = mAdapter.getPositionForSection(s.charAt(0));
                            if (position != -1) {
                                mListView.setSelection(position);
                            }
                        }
                    });
                }
            }
        }, baseActivity);
        request.setLoginuserid(YbbPsApplication.getInstance().getMyUserInfo().getUserId());
        request.setAdminTypeId(YbbPsApplication.getInstance().getMyUserInfo().getTypeId());
        WebUtils.doPost(request);
    }

    private void prepare() {

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();
        for (int i = 0; i < dataLists.size(); i++) {
            PatientModel model = dataLists.get(i);
            String pinyin;
            String sortString;
            if(StringUtils.isEmpty(model.getCustomerName())){
                 sortString = "#";
            }else{
                 pinyin = characterParser.getSelling(model.getCustomerName());
                 sortString = pinyin.substring(0, 1).toUpperCase();
            }

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                model.setSortLetters(sortString.toUpperCase());
            } else {
                model.setSortLetters("#");
            }
        }
        // 根据a-z进行排序源数据
        Collections.sort(dataLists, pinyinComparator);
    }


    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    class PatientsAdapter extends BaseAdapter implements SectionIndexer {

        @Override
        public int getCount() {
            return dataLists == null ? 0 : dataLists.size();
        }

        @Override
        public Object getItem(int position) {
            return dataLists == null ? null : dataLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.lv__generation_item, parent, false
                );
                holder = new ViewHolder();
                holder.iv_image_bg = (ImageView) convertView.findViewById(R.id.iv_image_bg);
                holder.tv_search_item = (TextView) convertView.findViewById(R.id.tv_search_item);//标题
                holder.tv_tel = (TextView) convertView.findViewById(R.id.tv_tel);//电话
                holder.tv_charge = (TextView) convertView.findViewById(R.id.tv_charge);//地址
                holder.iv_search_more = (TextView) convertView.findViewById(R.id.iv_search_more);
                holder.soft_rl = (RelativeLayout) convertView.findViewById(R.id.soft_rl);
                holder.soft = (TextView) convertView.findViewById(R.id.soft);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            PatientModel model = dataLists.get(position);
//            holder.iv_image_bg
            holder.tv_search_item.setText(model.getCustomerName());
            holder.tv_tel.setText(model.getCustomerTelephone());
            StringBuffer bf = new StringBuffer();
            if (StringUtils.isNotEmpty(model.getProvinceName())) {
                bf.append(model.getProvinceName());
            }
            if (StringUtils.isNotEmpty(model.getCityName())) {
                bf.append(model.getCityName());
            }
            if (StringUtils.isNotEmpty(model.getDistrictName())) {
                bf.append(model.getDistrictName());
            }
            holder.tv_charge.setText(bf.toString());
            holder.iv_search_more.setTag(new Integer(position));
            holder.iv_search_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer temp = (Integer) v.getTag();
                    PatientModel tempModel = dataLists.get(temp.intValue());
//                    Utils.makeToastAndShow(getActivity(), tempModel.getCustomerName());
                    generationMeal(tempModel);
                }
            });

            //根据position获取分类的首字母的char ascii值
            int section = getSectionForPosition(position);

            //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (position == getPositionForSection(section)) {
                holder.soft_rl.setVisibility(View.VISIBLE);
                holder.soft.setText(model.getSortLetters());
            } else {
                holder.soft_rl.setVisibility(View.GONE);
            }
            return convertView;
        }

        /**
         * 根据ListView的当前位置获取分类的首字母的char ascii值
         */
        public int getSectionForPosition(int position) {
            return dataLists.get(position).getSortLetters().charAt(0);
        }

        /**
         * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
         */
        public int getPositionForSection(int section) {
            for (int i = 0; i < getCount(); i++) {
                String sortStr = dataLists.get(i).getSortLetters();
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }

            return -1;
        }


        @Override
        public Object[] getSections() {
            return null;
        }
    }

    private void generationMeal(PatientModel tempModel) {
        // 通过包名获取要跳转的app，创建intent对象
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.bluemobi.ybb");
// 这里如果intent为空，就说名没有安装要跳转的应用嘛
        if (intent != null) {
            // 这里跟Activity传递参数一样的嘛，不要担心怎么传递参数，还有接收参数也是跟Activity和Activity传参数一样
            intent.putExtra("ssid", YbbPsApplication.getInstance().getMyUserInfo().getSsid());
            intent.putExtra("userId", tempModel.getUserId());
            intent.putExtra("checkUserId", YbbPsApplication.getInstance().getMyUserInfo().getUserId());
            startActivity(intent);
        } else {
            // 没有安装要跳转的app应用，提醒一下
            Toast.makeText(getActivity(), "哟，赶紧下载医帮宝医患端吧", Toast.LENGTH_LONG).show();
        }
    }

    static class ViewHolder {
        ImageView iv_image_bg;
        TextView tv_search_item;
        TextView tv_tel;
        TextView tv_charge;
        TextView iv_search_more;
        RelativeLayout soft_rl;
        TextView soft;
    }
}
