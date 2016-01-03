package com.bluemobi.ybb.ps.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluemobi.base.utils.Utils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.app.DbManager;
import com.bluemobi.ybb.ps.app.TitleBarManager;
import com.bluemobi.ybb.ps.base.BaseActivity;
import com.bluemobi.ybb.ps.db.entity.PSMessage;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

/**
 * Created by gaoyn on 2015/7/1.
 *
 * p5-4 消息
 */
public class PSMessageActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout OrderRemind;//订单提醒
    private RelativeLayout SystemRemind;//系统提醒

    private TextView order_remind_content;
    private TextView order_remind_time;
    private PSMessage bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(PSMessageActivity.this, getSupportActionBar());
        titleBarManager.showCommonTitleBar(R.string.message_title, R.drawable.common_back, true);


        showLoadingPage(false);
    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_psmessage,null);

        OrderRemind = (RelativeLayout)view.findViewById(R.id.order_remind_item);
        SystemRemind = (RelativeLayout)view.findViewById(R.id.system_remind_item);

        OrderRemind.setOnClickListener(this);
        SystemRemind.setOnClickListener(this);

        order_remind_content= (TextView)view.findViewById(R.id.order_remind_content);
        order_remind_time= (TextView)view.findViewById(R.id.order_remind_time);


        DbUtils db = DbManager.getInstance(mContext).getDefaultDbUtils();
        try {
            bean = db.findFirst(Selector.from(PSMessage.class).where("isread", "=", "0").orderBy("createTime"));
            if (bean!=null) {
                order_remind_content.setText(bean.getMsg());
                order_remind_time.setText(bean.getCreateTime());
            }else {
                order_remind_content.setText("暂无");
                order_remind_time.setVisibility(View.INVISIBLE);

            }
        } catch (DbException e) {
            e.printStackTrace();
        }
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.order_remind_item:
                Utils.moveTo(this, PSOrderRemindActivity.class);
                break;
            case R.id.system_remind_item:
                Toast.makeText(mContext,"系统消息",Toast.LENGTH_SHORT).show();
//                Utils.moveTo(this, SystemRemindActivity.class);
                break;
        }
    }
}
