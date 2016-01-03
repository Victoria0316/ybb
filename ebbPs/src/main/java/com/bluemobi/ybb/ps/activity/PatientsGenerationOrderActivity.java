package com.bluemobi.ybb.ps.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.view.LoadingPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufy on 2015/7/15.
 * P32代点餐-病患
 */
public class PatientsGenerationOrderActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    String[] cityLetter = {"C", "B", "H", "G", "W", "C", "S", "S", "C", "W", "H", "Z", "C", "D", "N", "L", "Q", "S", "S"
            , "L", "N", "H", "X", "H", "S", "Z", "G", "N", "J", "T", "S", "X", "A"};
    String[] cityName = {"长沙", "北京", "杭州", "广州", "武汉", "重庆", "上海", "深圳", "长春", "乌鲁木齐", "哈尔滨", "郑州", "成都", "大连", "南昌", "兰州", "齐齐哈尔", "汕头", "苏州"
            , "拉萨", "南京", "呼和浩特", "厦门", "合肥", "沈阳", "张家界", "贵州", "宁夏", "济南", "天津", "石家庄", "西安", "澳门"};
    List<String> letterToCity = new ArrayList<String>();
    int count;
    ListView lv;

    ListView lv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(this,getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.generation, R.drawable.common_back,false);

        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View generationView = inflater.inflate(R.layout.activity_generation_search, null);
        String str = "";
        for (int i = 0; i < letter.length; i++) {
            str = letter[i];
            boolean isAddLetter = false;
            for (int j = 0; j < cityLetter.length; j++) {
                if (str.equals(cityLetter[j])) {
                    if (!isAddLetter) {
                        letterToCity.add(str);
                        isAddLetter = true;
                    }
                    letterToCity.add(cityName[j]);
                }
            }
        }

        lv = (ListView) generationView.findViewById(R.id.listView1);
        lv.setAdapter(new MyAdapter());
        lv.setOnItemClickListener(this);

        lv1 = (ListView) generationView.findViewById(R.id.listView2);
        lv1.setAdapter(new MyAdapter1());
        lv1.setOnItemClickListener(this);
        return generationView;
    }

    @Override
    protected LoadingPage.LoadResult load() {

        return LoadingPage.LoadResult.success;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.listView1:
                boolean isLetter = false;
                for (int i = 0; i < letter.length; i++) {
                    if (letter[i].equals(letterToCity.get(position))) {
                        isLetter = true;
                        break;
                    }
                }
                if (!isLetter) {

                }
                break;
            case R.id.listView2:
                for (int i = 0; i < letterToCity.size(); i++) {
                    if (letter[position].equals(letterToCity.get(i))) {
                        lv.setSelection(i);
                        break;
                    }
                }
                break;
            default:
                break;
        }


    }

    class MyAdapter1 extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return letter.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return letter[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.letter_list, null);
            TextView tv = (TextView) view.findViewById(R.id.letterListTextView);
            tv.setText(letter[position]);
            return view;
        }

    }

    class MyAdapter extends BaseAdapter {

        final static int TYPE_1 = 1;
        final static int TYPE_2 = 2;

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return letterToCity.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return letterToCity.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            for (int i = 0; i < letter.length; i++) {
                if (letterToCity.get(position).equals(letter[i])) {
                    return TYPE_1;
                }
            }
            return TYPE_2;
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder1 vh1 = null;
            ViewHolder2 vh2 = null;
            int type = getItemViewType(position);
            if (convertView == null) {
                switch (type) {
                    case TYPE_1:
                        convertView = LayoutInflater.from(mContext).inflate(R.layout.letter, null);
                        vh1 = new ViewHolder1();
                        vh1.tv = (TextView) convertView.findViewById(R.id.letterTextView);
                        convertView.setTag(vh1);
                        break;
                    case TYPE_2:
                        convertView = LayoutInflater.from(mContext).inflate(R.layout.lv__generation_item, null);
                        vh2 = new ViewHolder2();

                        convertView.setTag(vh2);
                        break;
                    default:
                        break;
                }
            } else {
                switch (type) {
                    case TYPE_1:
                        vh1 = (ViewHolder1) convertView.getTag();
                        break;
                    case TYPE_2:
                        vh2 = (ViewHolder2) convertView.getTag();
                        break;
                    default:
                        break;
                }
            }
            switch (type) {
                case TYPE_1:
                    vh1.tv.setText(letterToCity.get(position));
                    break;
                case TYPE_2:

                    break;
                default:
                    break;
            }
            return convertView;




        }

        class ViewHolder {
            TextView tv;
            LinearLayout ll;
        }

        class ViewHolder1 {
            TextView tv;
        }

        class ViewHolder2 {
            TextView tv;
        }
    }
}
