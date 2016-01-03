package com.bluemobi.ybb.network.request;

import com.android.volley.Response;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.response.ArticlecommentinfoResponse;

import java.util.HashMap;
import java.util.Map;

/**
 *gxy
 *
 */
public class ArticlecommentinfoRequest extends YbbHttpJsonRequest<ArticlecommentinfoResponse>{

    private static final String APIPATH = "mobi/articlecommentinfo/findByPage";


//    currentnum 	string 	是 	每页条数
//    currentpage 	string 	是 	当前页数
//    articleid 	string 	是 	资讯id

    private String currentnum;
    private String currentpage;
    private String articleid;

    public ArticlecommentinfoRequest(Response.Listener<ArticlecommentinfoResponse> listener,
                                     Response.ErrorListener errorListener)
    {
        super(Method.POST, APIPATH, listener, errorListener);
    }

    public ArticlecommentinfoRequest(int method, String partUrl,
                                     Response.Listener<ArticlecommentinfoResponse> listener, Response.ErrorListener errorListener)
    {
        super(method, partUrl, listener, errorListener);
    }
    @Override
    public String GetApiPath() {
        return APIPATH;
    }

    @Override
    public Map<String, String> GetParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("currentnum", currentnum);
        map.put("currentpage", currentpage);
        map.put("articleid", articleid);
        return map;
    }

    @Override
    public Class<ArticlecommentinfoResponse> getResponseClass() {
        return ArticlecommentinfoResponse.class;
    }


    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getCurrentnum() {
        return currentnum;
    }

    public void setCurrentnum(String currentnum) {
        this.currentnum = currentnum;
    }

    public String getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(String currentpage) {
        this.currentpage = currentpage;
    }
}
