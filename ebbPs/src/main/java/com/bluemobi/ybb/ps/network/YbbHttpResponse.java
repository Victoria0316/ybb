package com.bluemobi.ybb.ps.network;


import com.bluemobi.ybb.ps.network.util.DefaultTranslateErrorToCn;
import com.bluemobi.ybb.ps.network.util.TranslateErrorToCn;

public abstract class YbbHttpResponse implements YbbHttpError {
    private int status;

    private String msg;

    private TranslateErrorToCn translateErrorToCn;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return translateErrorToCn(status);
    }

    public void setContent(String content) {
        this.msg = content;
    }

    @Override
    public String translateErrorToCn(int errorCode) {
        if (translateErrorToCn == null) {
            translateErrorToCn = new DefaultTranslateErrorToCn();
        }
        return translateErrorToCn.translateErrorToCn(errorCode);
    }

    public String getMsg() {
        return msg;
    }
}

