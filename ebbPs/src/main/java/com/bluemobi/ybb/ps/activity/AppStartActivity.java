package com.bluemobi.ybb.ps.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluemobi.base.utils.SharedPreferencesUtil;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.ybb.ps.R;
import com.bluemobi.ybb.ps.util.Constants;


/**
 * Created by wangzhijun on 2015/7/1.
 */
public class AppStartActivity extends Activity {

    private FrameLayout frameLayout;

    private ImageView imageView;

    private TextView textView;

    private int defaulTime = 8;

    private CountDownTimer downTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        process();
    }

    private void process() {
        String temp = SharedPreferencesUtil.getFromFileByDefault(this, Constants.FIRSTLOADAPP, "true");
        if (Boolean.parseBoolean(temp)) {
            frameLayout = new FrameLayout(this);
//            imageView = new ImageView(this);
            textView = new TextView(this);
            textView.setTextColor(Color.RED);
            textView.setText(String.valueOf(defaulTime));
            downTimer = new CountDownTimer(defaulTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    textView.setText(String.valueOf(millisUntilFinished/1000));
                }

                @Override
                public void onFinish() {
                    SharedPreferencesUtil.saveToFile(AppStartActivity.this, Constants.FIRSTLOADAPP, "false");
                    finish();
                    Utils.moveTo(AppStartActivity.this, LoginActivity.class);
                }
            };
            downTimer.start();
//            imageView.setImageResource(R.drawable.loading);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.TOP|Gravity.RIGHT;
            params.topMargin = Utils.dip2px(this, 8);
            params.rightMargin = Utils.dip2px(this,8);
            frameLayout.addView(textView, params);
            frameLayout.setBackgroundResource(R.drawable.loading);
            setContentView(frameLayout);
        } else {
            finish();
            Utils.moveTo(this, LoginActivity.class);
        }
    }


}
