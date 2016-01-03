package com.bluemobi.ybb.activity;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.app.TitleBarManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.model.TwoDimensionCodeBean;
import com.bluemobi.ybb.network.request.ModificationAddressRequest;
import com.bluemobi.ybb.network.response.ModificationAddressResponse;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.zxing.camera.CameraManager;
import com.bluemobi.ybb.zxing.decoding.CaptureActivityHandler;
import com.bluemobi.ybb.zxing.decoding.InactivityTimer;
import com.bluemobi.ybb.zxing.view.ViewfinderView;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

/**
 * Created by gaoyn on 2015/7/3.
 * <p/>
 * p6-6 扫一扫
 */
public class MipcaActivityCapture extends BaseActivity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    //保存二维码扫描后的数据
    TwoDimensionCodeBean twoDimensionCodeBean;
    //判断 json 解析
    private boolean json_error_flag = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TitleBarManager titleBarManager = new TitleBarManager();
        titleBarManager.init(MipcaActivityCapture.this, getSupportActionBar());
        titleBarManager.showTitleTextBar(R.string.scanning, R.drawable.common_back, R.string.Manually_modify);

        showLoadingPage(false);

    }

    @Override
    protected void initBaseData() {

    }

    @Override
    public View createSuccessView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.activity_modification_address_scan, null);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) view.findViewById(R.id.viewfinder_view);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    public void clickBarRight() {
        Intent i = new Intent(MipcaActivityCapture.this, ModificationAddressPatientActivity.class);
        startActivityForResult(i, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 11) {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * ����ɨ����
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (resultString.equals("")) {
            Toast.makeText(MipcaActivityCapture.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else {

            String json = resultString;
            Logger.d("json == ", json);

            Gson gson = new Gson();

            try {
                twoDimensionCodeBean = gson.fromJson(json, TwoDimensionCodeBean.class);//指定了要还原的class，保存在gson中。
                json_error_flag = false;
            } catch (Exception e) {
                Toast.makeText(this, "二维码错误", Toast.LENGTH_SHORT).show();
                json_error_flag = true;
                MipcaActivityCapture.this.finish();
            }

            if (!json_error_flag) {

                ModificationAddressRequest request = new ModificationAddressRequest(new Response.Listener<ModificationAddressResponse>() {
                    @Override
                    public void onResponse(ModificationAddressResponse response) {
                        Utils.closeDialog();
                        if (response != null && response.getStatus() == 0) {// success

                            Toast.makeText(MipcaActivityCapture.this, "地址修改成功", Toast.LENGTH_SHORT).show();

                            Intent resultIntent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("ok", "ok");
                            resultIntent.putExtras(bundle);
                            MipcaActivityCapture.this.setResult(RESULT_OK, resultIntent);

                            MipcaActivityCapture.this.finish();


                        } else {// failed
                            Log.e("error", "error");
                        }
                    }
                }, this);


//            {
//                "hospitalName": "中国医大附属医院",
//                    "hospitalId": "8aba20b74ef30df4014ef313389e0000",
//                    "departmentName": "耳鼻喉科一科",
//                    "departmentId": "8aba20b74ef76c49014ef79d6b9c0001",
//                    "bedName": "1",
//                    "bedId": "8aba20b74f6df7a3014f6df8c79a0003"
//            }
//

                request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
                request.setUsername(YbbApplication.getInstance().getMyUserInfo().getNickName());
                request.setHospitalId(twoDimensionCodeBean.getHospitalId());
                request.setHospitalName(twoDimensionCodeBean.getHospitalName());
                request.setDepartmentId(twoDimensionCodeBean.getDepartmentId());
                request.setDepartmentName(twoDimensionCodeBean.getDepartmentName());
                request.setBedId(twoDimensionCodeBean.getBedId());
                request.setBedName(twoDimensionCodeBean.getBedName());
                request.setAddress(twoDimensionCodeBean.getHospitalName() + twoDimensionCodeBean.getDepartmentName() + twoDimensionCodeBean.getBedName());
                request.setCellPhone(YbbApplication.getInstance().getMyUserInfo().getUserName());
                Utils.showProgressDialog(this);
                WebUtils.doPost(request);


//			Intent resultIntent = new Intent();
//			Bundle bundle = new Bundle();
//            bundle.putString("result", resultString);
//			bundle.putParcelable("bitmap", barcode);
//			resultIntent.putExtras(bundle);
//            this.setResult(RESULT_OK, resultIntent);
            }
        }

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}