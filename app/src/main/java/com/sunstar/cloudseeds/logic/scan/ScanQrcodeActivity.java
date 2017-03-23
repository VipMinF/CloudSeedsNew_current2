package com.sunstar.cloudseeds.logic.scan;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.util.Log;

import com.classichu.classichu.app.CLog;
import com.classichu.classichu.basic.BasicCallBack;
import com.classichu.classichu.basic.helper.CameraHelper;
import com.classichu.classichu.basic.helper.GoToSysConfigHelper;
import com.classichu.classichu.basic.tool.ToastTool;
import com.classichu.classichu.classic.ClassicActivity;
import com.classichu.dialogview.manager.DialogManager;
import com.classichu.dialogview.ui.ClassicDialogFragment;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.UrlDatas;
import com.sunstar.cloudseeds.logic.login.UserLoginHelper;
import com.sunstar.cloudseeds.logic.login.bean.UserLoginBean;
import com.sunstar.cloudseeds.logic.scan.bean.QrcodeBean;
import com.sunstar.cloudseeds.logic.scan.model.QrcodeModelImpl;
import com.sunstar.cloudseeds.logic.shangpinqi.ui.SPQDetailFragment;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.ui.YZTZDetailFragment;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class ScanQrcodeActivity extends ClassicActivity  {

    private QRCodeView mQRCodeView;
    private Context mcontext;
    private String bindId;
    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_scan_qrcode;
    }

    @Override
    protected void initView() {
        setAppBarTitle("扫一扫");
        mcontext=this;
        mQRCodeView = (ZBarView) findViewById(R.id.id_zbarview);
        mQRCodeView.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {

                //暂停
                //###pauseScan();
                /**
                 * 震动
                 */
                Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(200);
                //停止识别
                mQRCodeView.stopSpot();
                ToastTool.showLong(result);
                //校验二维码或者绑定二维码
                checkOrBindQrcode(result);
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


    private void checkOrBindQrcode(String qrcode ){

        //DialogManager.showCustomLoadingDialog(this);
        String key=getResources().getString(R.string.scanqrcode_bundleextrakey_bindId);
        bindId=this.getBundleExtra().getString(key);

        final QrcodeModelImpl qrcodemodelImpl = new QrcodeModelImpl();
        qrcodemodelImpl.loadData(UrlDatas.CheckOrBindQrCode_URL ,qrcode,bindId,new BasicCallBack<QrcodeBean>(){
            @Override
            public void onSuccess(QrcodeBean qrcodeBean) {

                //DialogManager.hideLoadingDialog();
                ToastTool.showShort(qrcodeBean.getShow_msg().toString());
                handelWithResult(qrcodeBean);
            }
            @Override
            public void onError(String s) {
                //DialogManager.hideLoadingDialog();
                ToastTool.showShort(s);
            }
        });
    }


    private  void  handelWithResult(QrcodeBean qrcodeBean){


        int scanqrcodeType=getBundleExtraInt1();
        switch (scanqrcodeType)
        {
            case  ScanQrCodeType.bind_zuqun:

                this.finish();

                break;

            case  ScanQrCodeType.bind_xuanzhu:

                this.finish();

                break;

            case  ScanQrCodeType.select:

                UserLoginBean userloginbean= UserLoginHelper.userLoginBean(mcontext);
                if (qrcodeBean.getSecondary_id()!=null && qrcodeBean.getSecondary_id().length()>0) {

                    goToYZTZDetailFragment(userloginbean.getUserid(),qrcodeBean.getSecondary_id().toString());

                } else if (qrcodeBean.getTertiary_id()!=null && qrcodeBean.getTertiary_id().length()>0){

                    goToSPQDetailFragment(userloginbean.getUserid(),qrcodeBean.getTertiary_id().toString());
                }
                break;
        }

    }


    private void  goToYZTZDetailFragment(String param1,String param2 ){
        setAppBarTitle("族群信息");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.id_frame_layout_content_scansqcode, YZTZDetailFragment.newInstance(param1,param2))
                .commitAllowingStateLoss();

    }


    private void  goToSPQDetailFragment(String param1,String param2 ){

        setAppBarTitle("商品期调查");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.id_frame_layout_content_scansqcode, SPQDetailFragment.newInstance(param1,param2))
                .commitAllowingStateLoss();

    }

}
