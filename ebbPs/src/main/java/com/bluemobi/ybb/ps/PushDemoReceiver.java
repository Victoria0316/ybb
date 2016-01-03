package com.bluemobi.ybb.ps;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.bluemobi.base.utils.SharedPreferencesUtil;
import com.bluemobi.base.utils.StringUtils;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.ybb.ps.activity.DiliverymanMainActivity;
import com.bluemobi.ybb.ps.activity.PSOrderRemindDetailsFoodActivity;
import com.bluemobi.ybb.ps.app.DbManager;
import com.bluemobi.ybb.ps.callback.GetPushRefreshData;
import com.bluemobi.ybb.ps.db.entity.PSMessage;
import com.bluemobi.ybb.ps.db.entity.PSMessageFoods;
import com.bluemobi.ybb.ps.db.entity.PSOrderInfoModel;
import com.bluemobi.ybb.ps.util.Constants;
import com.google.gson.Gson;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

public class PushDemoReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                byte[] payload = bundle.getByteArray("payload");
                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");
                if (payload != null) {
                    String data = new String(payload);
                    analyzeDatas(context, data);
                    Log.e("ggggggggggggggggggg", "receiver payload : " + data);
                    payloadData.append(data);
                    payloadData.append("\n");
                    Intent i = new Intent(GetPushRefreshData.ACTION_NAME);
                    context.sendBroadcast(i);
                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
//                Utils.makeToastAndShow(context, "cid--> " +cid);
                if (StringUtils.isNotEmpty(cid)){
                    if( DiliverymanMainActivity.getInstance()!=null){
                        DiliverymanMainActivity.getInstance().uploadCid(cid);
                    }
                }


                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 * 
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }
    private  void analyzeDatas(Context context, String str){
        PSOrderInfoModel order= new PSOrderInfoModel();
        PSMessage bean= new PSMessage();
        Gson gson = new Gson();
        order = gson.fromJson(str, PSOrderInfoModel.class);
        bean.setIsread("0");//未读
        bean.setCreateTime(order.getData().getCreateTime());
        bean.setOrderId(order.getData().getId());
        bean.setOrderNo(order.getData().getOrderNo());
        bean.setOrderStatus(order.getData().getOrderStatus());
        bean.setMsg(order.getMsg());
        /*      0：预览订单     1：未付款     2：已付款(待发货)     3：已付款（已发货，待收货）
            4：已收货(待评价)        5：已完成         6：已退单      */
        DbUtils db = DbManager.getInstance(context).getDefaultDbUtils();
        //  2.存库
        try {
            db.saveOrUpdate(bean);
        } catch (DbException e1) {
            e1.printStackTrace();
        }
        List<PSMessageFoods> foodses = new ArrayList<PSMessageFoods>();
        try {
            foodses=db.findAll(Selector.from(PSMessageFoods.class).where("orderid", "=", order.getData().getId()));
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (order.getData().getOrderItemDTOList()!=null &&order.getData().getOrderItemDTOList().size()!=0){
            for (int i=0;i<order.getData().getOrderItemDTOList().size();i++){
                if (foodses.size()==0){
//                    PSMessageFoods foodInfo= new PSMessageFoods();
//                    foodInfo.setOrderid(order.getData().getId());
//
//      foodInfo.setCount(order.getData().getOrderItemDTOList().get(i).getProductComboGroupDTO().getNum());
//                    foodInfo.setImgPath(order.getData().getOrderItemDTOList().get(i).getProductComboGroupDTO().getImgPath());
//                    foodInfo.setPrice(order.getData().getOrderItemDTOList().get(i).getProductComboGroupDTO().getCustomer_price());
//                    foodInfo.setShopAndName(order.getData().getOrderItemDTOList().get(i).getProductComboGroupDTO().getShopName() + order.getData().getOrderItemDTOList().get(i).getProductComboGroupDTO().getComboName());
//                    try {
//                        db.saveOrUpdate(foodInfo);
//                    } catch (DbException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }

        sendNotify("餐品消息", order.getMsg(), context, order);
    }

    /**
     * 发送通知方法
     *
     * @param notifyMsg
     *            通知Title
     * @param notifyMsg
     *            通知内容
     */
    private void sendNotify(String notifyTitle, String notifyMsg, Context c,
                            PSOrderInfoModel bean) {

        DbUtils db = DbManager.getInstance(c).getDefaultDbUtils();
        PSMessage partent= new PSMessage();
        try {
            partent = db.findFirst(Selector.from(PSMessage.class).
                    where("orderId", "=", bean.getData().getId()).and("isread","=","0").and("orderStatus","=",bean.getData().getOrderStatus()));
        } catch (DbException e) {
            e.printStackTrace();
        }
        NotificationManager nm = (NotificationManager) c
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // 1 得到通知管理器
        // 2构建通知
        Notification notification = new Notification(R.drawable.push,
                notifyTitle, System.currentTimeMillis());

        // 3设置通知的点击事件
        Intent intent = new Intent(c, PSOrderRemindDetailsFoodActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("id", String.valueOf(partent.getId()));
        intent.putExtras(bundle);
        PendingIntent contentIntent = PendingIntent.getActivity(c, 0, intent,
                0);
        notification.setLatestEventInfo(c, notifyTitle, notifyMsg,
                contentIntent);
        notification.flags = Notification.FLAG_AUTO_CANCEL;// 点击通知之后自动消失
//        notification.defaults=Notification.DEFAULT_ALL;

        // 4发送通知
//        nm.notify(Constants.NOTIFICATION++, notification);
        String isAccepted= SharedPreferencesUtil.getFromFile(c, "inform_off");
        String type=SharedPreferencesUtil.getFromFile(c,"hint_type");//提醒方式
        if ("1".equals(isAccepted)){//新消息提醒
            if(StringUtils.isNotEmpty(type)){
                if("铃声提醒".equals(type)){
                    notification.defaults=Notification.DEFAULT_SOUND;
                }else if ("震动提醒".equals(type)){
                    notification.defaults=Notification.DEFAULT_VIBRATE;
                }
            }
            nm.notify(Constants.NOTIFICATION++, notification);
        }else if(StringUtils.isEmpty(isAccepted)) {
            notification.defaults=Notification.DEFAULT_SOUND;
            nm.notify(Constants.NOTIFICATION++, notification);
        }
    }
}
