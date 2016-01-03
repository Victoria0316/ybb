package com.bluemobi.ybb.callback;

import android.view.View;

/**
 * Created by wangzhijun on 2015/8/30.
 */
public interface PaymentDialogOnclick {
    void doSure(View v, String pwd);
    void doCancel(View v);
}
