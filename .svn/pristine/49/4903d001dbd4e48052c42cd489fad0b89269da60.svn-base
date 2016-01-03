package com.bluemobi.ybb.ps.callback;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class GetPushRefreshData extends BroadcastReceiver
{
	public static final String ACTION_NAME = "com.bluemobi.psybb";
  private RefreshListener listener;

  public GetPushRefreshData(RefreshListener listener)
  {
    this.listener = listener;
  }

  public void onReceive(Context context, Intent intent)
  {
    System.out.println("接收完成的广播");
    if (this.listener == null) {
      return;
    }
      this.listener.refreshData();
  }

  public   interface RefreshListener
  {
    public  void refreshData();

  }
}