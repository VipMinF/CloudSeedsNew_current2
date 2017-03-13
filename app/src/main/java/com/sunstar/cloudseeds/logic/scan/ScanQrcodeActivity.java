package com.sunstar.cloudseeds.logic.scan;

import android.content.DialogInterface;
import android.os.Vibrator;
import android.util.Log;

import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.helper.CameraHelper;
import com.classichu.classichu.basic.helper.GoToSysConfigHelper;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.classichu.classic.ClassicActivity;
import com.classichu.dialogview.manager.DialogManager;
import com.classichu.dialogview.ui.ClassicDialogFragment;
import com.sunstar.cloudseeds.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class ScanQrcodeActivity extends ClassicActivity {


    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_scan_qrcode;
    }
    private QRCodeView mQRCodeView;
    @Override
    protected void initView() {

        setAppBarTitle("扫一扫");

        mQRCodeView = (ZBarView) findViewById(R.id.id_zbarview);
        mQRCodeView.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                ToastTool.showLong(result);
                //暂停
                //###pauseScan();
                /**
                 * 震动
                 */
                Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(200);
                //
                mQRCodeView.startSpot();
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                Log.e("XXX", "打开相机出错");
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle.ClassicTitleBar;
    }

    @Override
    protected boolean configSwipeBackEnable() {
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (CameraHelper.cameraCanUse()){
            mQRCodeView.startCamera();//开始预览
            mQRCodeView.startSpot();//开始识别
        }else {
            //##ToastTool.showLong("打开相机出错,请确保有使用相机的权限！");
            DialogManager.showClassicDialog(this, "打开相机出错,需要使用相机权限", "是否去应用详情设置？", new ClassicDialogFragment.OnBtnClickListener() {
                @Override
                public void onBtnClickOk(DialogInterface dialogInterface) {
                    super.onBtnClickOk(dialogInterface);
                    //
                    GoToSysConfigHelper.goToNormalEnterWithManufacturer(mContext);
                    //
                    ScanQrcodeActivity.this.finish();
                }
                @Override
                public void onBtnClickCancel(DialogInterface dialogInterface) {
                    super.onBtnClickCancel(dialogInterface);
                    //
                    ScanQrcodeActivity.this.finish();
                }
            });
            CLog.d("打开相机出错,请确保有使用相机的权限！");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mQRCodeView.stopCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mQRCodeView.onDestroy();
    }

    private void pauseScan(){
        mQRCodeView.stopCamera();
    }
    private void continueScan() {
        mQRCodeView.startCamera();//开始预览
        mQRCodeView.startSpot();//开始识别
        mQRCodeView.showScanRect();
    }
}
