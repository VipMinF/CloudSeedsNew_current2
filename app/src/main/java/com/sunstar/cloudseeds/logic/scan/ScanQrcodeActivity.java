package com.sunstar.cloudseeds.logic.scan;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

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
import com.sunstar.cloudseeds.logic.scan.bean.QrcodeBean;
import com.sunstar.cloudseeds.logic.scan.model.QrcodeModelImpl;
import com.sunstar.cloudseeds.logic.shangpinqi.ui.SPQDetailFragment;
import com.sunstar.cloudseeds.logic.xuanzhu.event.XuanZhuListRefreshEvent;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.ui.YZTZDetailFragment;

import org.greenrobot.eventbus.EventBus;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class ScanQrcodeActivity extends ClassicActivity  {

    private QRCodeView mQRCodeView;
    private Context mcontext;
    private String bindId;
    private String qrcode;
    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_scan_qrcode;
    }

    @Override
    protected void initView() {
        setAppBarTitle("扫一扫");
        mcontext=this;
        String key=getResources().getString(R.string.scanqrcode_bundleextrakey_bindId);
        bindId=this.getBundleExtra().getString(key);

        mQRCodeView = (ZBarView) findViewById(R.id.id_zbarview);
        mQRCodeView.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                //暂停
                //###pauseScan();
                /**
                 * 震动
                 */
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(200);
                //继续识别
                //mQRCodeView.startSpot();
                pauseScan();
                qrcode=result;
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
    protected void onPause() {
        super.onPause();
        stopScan();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyScan();
    }

    private void destroyScan() {
        mQRCodeView.onDestroy();
    }

    private void stopScan() {
        mQRCodeView.stopCamera();
    }

    private void startScan() {
        mQRCodeView.startCamera();//开始预览
        mQRCodeView.startSpot();//开始识别
        mQRCodeView.showScanRect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CameraHelper.cameraCanUse()) {
            startScan();
        } else {
            //##ToastTool.showLong("打开相机出错,请确保有使用相机的权限！");
            DialogManager.showClassicDialog(this, "打开相机出错,需要使用相机权限", "是否去应用详情设置？", new ClassicDialogFragment.OnBtnClickListener() {
                @Override
                public void onBtnClickOk(DialogInterface dialogInterface) {
                    super.onBtnClickOk(dialogInterface);
                    //
                    GoToSysConfigHelper.goToNormalEnterWithManufacturer();
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


    private void pauseScan(){
        mQRCodeView.stopCamera();
    }

    private void continueScan() {
        mQRCodeView.startCamera();//开始预览
        mQRCodeView.startSpot();//开始识别
        mQRCodeView.showScanRect();
    }


    private void checkOrBindQrcode(String qrcode){

        DialogManager.showCustomLoadingDialog(this);

        final QrcodeModelImpl qrcodemodelImpl = new QrcodeModelImpl();
        qrcodemodelImpl.loadData(UrlDatas.CheckOrBindQrCode_URL, qrcode, bindId, new BasicCallBack<QrcodeBean>() {
            @Override
            public void onSuccess(QrcodeBean qrcodeBean) {
                DialogManager.hideLoadingDialog();
                handelWithResult(qrcodeBean);
            }
            @Override
            public void onError(String s) {
                DialogManager.hideLoadingDialog();
                ToastTool.showShort(s);
                continueScan();
            }
        });
    }


    private  void  handelWithResult(QrcodeBean qrcodeBean){

        int scanqrcodeType=getBundleExtraInt1();
        ToastTool.showLong(qrcodeBean.getShow_msg().toString());

        switch (scanqrcodeType)
        {
            case  ScanQrCodeType.bind_zuqun:
                //绑定失败
                if (!qrcodeBean.getShow_code().equals("1")){
                    bindError(qrcodeBean.getShow_msg().toString());
                    return;
                }
                this.finish();

                break;

            case  ScanQrCodeType.bind_xuanzhu:
                //绑定失败
                if (!qrcodeBean.getShow_code().equals("1")){
                    bindError(qrcodeBean.getShow_msg().toString());
                    return;
                }
                EventBus.getDefault().post(new XuanZhuListRefreshEvent());
                this.finish();

                break;

            case  ScanQrCodeType.select:

                //没有绑定，手动输入绑定Id
                if (qrcodeBean.getShow_code().equals("4")){
                    editBindId();

                }else if(qrcodeBean.getShow_code().equals("1")){

                    //UserLoginBean userloginbean= UserLoginHelper.userLoginBean();
                    if (qrcodeBean.getTertiary_id()!=null && qrcodeBean.getTertiary_id().length()>0){
                        goToSPQDetailFragment(qrcodeBean.getTertiary_id().toString(),null);
                        mQRCodeView.stopCamera();
                        mQRCodeView.setVisibility(View.GONE);

                    } else if (qrcodeBean.getSecondary_id()!=null && qrcodeBean.getSecondary_id().length()>0) {
                        goToYZTZDetailFragment(qrcodeBean.getSecondary_id().toString(),null);
                        mQRCodeView.stopCamera();
                        mQRCodeView.setVisibility(View.GONE);

                    }else {
                        bindError("查询失败了");
                    }

                }else {

                    bindError(qrcodeBean.getShow_msg().toString());
                }
                break;
        }

    }


    private void editBindId(){

        DialogManager.showEditDialog(this, "该二维码未绑定", "是否进行绑定？", new DialogManager.OnEditDialogBtnClickListener() {
            @Override
            public void onBtnClickOk(DialogInterface dialogInterface, String inputText) {
                super.onBtnClickOk(dialogInterface, inputText);
                if (inputText.length()>0){
                    bindId=inputText;
                    checkOrBindQrcode(qrcode);
                }else {
                    continueScan();
                }
            }
            @Override
            public void onBtnClickCancel(DialogInterface dialogInterface) {

                continueScan();
            }
        });

    }

   private void bindError(String error){

       DialogManager.showTipDialog(this, error, "请重新操作", new ClassicDialogFragment.OnBtnClickListener() {
           @Override
           public void onBtnClickOk(DialogInterface dialogInterface) {
               super.onBtnClickOk(dialogInterface);
               continueScan();
           }
       });

   }


    private void  goToYZTZDetailFragment(String param1,String param2 ){
        setAppBarTitle("族群信息");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.id_frame_layout_content_scansqcode, YZTZDetailFragment.newInstance(param1, param2))
                .commitAllowingStateLoss();

    }


    private void goToSPQDetailFragment(String param1, String param2) {

        setAppBarTitle("商品期调查");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.id_frame_layout_content_scansqcode, SPQDetailFragment.newInstance(param1, param2))
                .commitAllowingStateLoss();

    }

}
