package com.bluemobi.ybb.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.bluemobi.ybb.R;
import com.bluemobi.ybb.callback.PaymentDialogOnclick;
import com.bluemobi.ybb.util.Utils;
import com.jungly.gridpasswordview.GridPasswordView;

/**
 * Created by liufy on 2015/7/9.
 */
public class PaymentDialog  extends DialogFragment implements View.OnClickListener

{

    private PaymentDialogOnclick mListener;

    private GridPasswordView gridPasswordView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_payment, null);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_sure = (Button) view.findViewById(R.id.btn_sure);
        gridPasswordView = (GridPasswordView) view.findViewById(R.id.gpv_normal);
        btn_cancel.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
        return view;
    }



    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_cancel:
                getDialog().dismiss();
                if(mListener != null){
                    mListener.doCancel(v);
                }
                break;
            case R.id.btn_sure:
                if(mListener != null){
                    mListener.doSure(v, gridPasswordView.getPassWord());
                }
                break;
        }
    }

    public PaymentDialogOnclick getmListener() {
        return mListener;
    }

    public void setmListener(PaymentDialogOnclick mListener) {
        this.mListener = mListener;
    }
}
