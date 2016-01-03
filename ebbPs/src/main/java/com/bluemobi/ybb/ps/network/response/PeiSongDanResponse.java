package com.bluemobi.ybb.ps.network.response;

import com.bluemobi.ybb.ps.network.YbbHttpResponse;
import com.bluemobi.ybb.ps.network.model.PeiSongListModel;

/**
 * Created by gaoyn on 2015/8/13.
 */
public class PeiSongDanResponse extends YbbHttpResponse{

    private PeiSongListModel data;

    public PeiSongListModel getData() {
        return data;
    }

    public void setData(PeiSongListModel data) {
        this.data = data;
    }
}
