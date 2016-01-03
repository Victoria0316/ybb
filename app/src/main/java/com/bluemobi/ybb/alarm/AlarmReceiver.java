package com.bluemobi.ybb.alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	private final static String tag = "AlarmReceivers";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO 自动生成的方法存根
		
		Intent noteList = new Intent(context, AlarmActivity.class);
		noteList.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(noteList);

	}

}
