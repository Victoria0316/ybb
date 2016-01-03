package com.bluemobi.ybb.network.response;

import com.bluemobi.ybb.network.YbbHttpResponse;
import com.bluemobi.ybb.network.model.Articleinfo;
import com.bluemobi.ybb.network.model.UserInfo;

/**

 */
public class ArticleinfoResponse extends YbbHttpResponse {

    private Articleinfo data;

    public Articleinfo getData() {

        return data;
    }

    public void setData(Articleinfo data) {
        this.data = data;
    }
}
