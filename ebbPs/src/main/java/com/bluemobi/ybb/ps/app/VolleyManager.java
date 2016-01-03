package com.bluemobi.ybb.ps.app;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.base.utils.StringUtils;
import com.bluemobi.base.utils.WebUtils;
import com.bluemobi.ybb.ps.network.YbbHttpBase;

public class VolleyManager
{
    private static RequestQueue mRequestQueue;


    /**
     * 初始化队列及图片加载器
     * 
     * @param context
     */
    static void init(Context context)
    {
         mRequestQueue = Volley.newRequestQueue(context);
    }



    /**
     * 添加request to queue
     * 
     * @param request
     */
    public static <T> void addToQueue(Request<T> request) {
        if (request instanceof YbbHttpBase) {
            YbbHttpBase<T> base = (YbbHttpBase<T>) request;

            Logger.d("VolleyManager",
                    "addToQueue-->" + base.requestUrl() + "?" + base.getParamJson());

        }
        mRequestQueue.add(request);
    }

    /**
     * 添加request并设置tag
     * 
     * @param request
     * @param tag
     */
    public static <T> void addToQueue(Request<T> request, String tag) {
        if (request instanceof YbbHttpBase) {
            YbbHttpBase<T> base = (YbbHttpBase<T>) request;
            Logger.d("VolleyManager",
                    "addToQueue-->" + base.requestUrl() + "?" + WebUtils.buildQuery(base.GetParameters()));
        }
        request.setTag(StringUtils.isEmpty(tag) ? YbbHttpBase.class.getSimpleName() : tag);
        mRequestQueue.add(request);
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    /**
     * 取消指定tag 的请求
     * 
     * @param tag
     */
    public static void cancel(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

}
