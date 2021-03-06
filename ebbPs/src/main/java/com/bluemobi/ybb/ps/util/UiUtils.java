package com.bluemobi.ybb.ps.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.bluemobi.ybb.ps.app.YbbPsApplication;

/**
 * Created by liufy on 2015/6/28.
 */
public class UiUtils
{
    /**
     * 获取到字符数组
     * @param tabNames  字符数组的id
     */
    public static String[] getStringArray(int tabNames) {
        return getResource().getStringArray(tabNames);
    }

    public static Resources getResource() {
        return YbbPsApplication.getInstance().getResources();
    }

    public static Context getContext(){
        return YbbPsApplication.getContext();
    }

    /**
     * 把Runnable 方法提交到主线程运行
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        // 在主线程运行
        if(android.os.Process.myTid()==YbbPsApplication.getMainTid()){
            runnable.run();
        }else{
            //获取handler
            YbbPsApplication.getHandler().post(runnable);
        }
    }


    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }


    public static Drawable getDrawalbe(int id) {
        return getResource().getDrawable(id);
    }

    public static int getDimens(int homePictureHeight) {
        return (int) getResource().getDimension(homePictureHeight);
    }
    /**
     * 延迟执行 任务
     * @param run   任务
     * @param time  延迟的时间
     */
    public static void postDelayed(Runnable run, int time) {
        YbbPsApplication.getHandler().postDelayed(run, time); // 调用Runable里面的run方法
    }
    /**
     * 取消任务
     * @param auToRunTask
     */
    public static void cancel(Runnable auToRunTask) {
        YbbPsApplication.getHandler().removeCallbacks(auToRunTask);
    }

}
