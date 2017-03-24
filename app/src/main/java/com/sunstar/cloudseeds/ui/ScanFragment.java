package com.sunstar.cloudseeds.ui;


import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.classichu.classichu.basic.helper.GoToSysConfigHelper;
import com.classichu.classichu.basic.helper.PermissionsHelper;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.classichu.classichu.classic.ClassicFragment;
import com.classichu.dialogview.manager.DialogManager;
import com.classichu.dialogview.ui.ClassicDialogFragment;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.scan.ScanQrCodeType;
import com.sunstar.cloudseeds.logic.scan.ScanQrcodeActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanFragment extends ClassicFragment {

    public ScanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanFragment newInstance(String param1, String param2) {
        ScanFragment fragment = new ScanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected int setupLayoutResId() {
        return R.layout.fragment_scan;
    }

    @Override
    protected void initView(View view) {

        PermissionsHelper.registerDangerousPermissionOperation(new PermissionsHelper.DangerousPermissionOperation() {
            @Override
            public void doDangerousOperation(FragmentActivity fragmentActivity, String... strings) {
               goScanContinue();
            }

            @Override
            public void permissionProhibition() {
                //Toast.makeText(mContext, "没有相机权限", Toast.LENGTH_SHORT).show();
                DialogManager.showClassicDialog(getActivity(),"打开相机出错,请允许使用相机权限，是否去应用详情设置？",
                         "ask c", new ClassicDialogFragment.OnBtnClickListener() {
                            @Override
                            public void onBtnClickOk(DialogInterface dialogInterface) {
                                super.onBtnClickOk(dialogInterface);
                                //
                                GoToSysConfigHelper.goToNormalEnterWithManufacturer();
                            }
                        });

            }
        });

        setOnNotFastClickListener(findById(R.id.id_tv_scan), new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                goScan();
            }
        });

    }

    private void goScanContinue() {
        Bundle bundle = createBundleExtraInt1(ScanQrCodeType.select);
        bundle.putString(getResources().getString(R.string.scanqrcode_bundleextrakey_bindId), "");
        startAty(ScanQrcodeActivity.class,bundle);
    }

    private void goScan() {
        PermissionsHelper.checkPermissions(getActivity(), Manifest.permission.CAMERA);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        PermissionsHelper.callAtOnRequestPermissionsResult(getActivity(),requestCode,permissions,grantResults);
    }

    @Override
    protected void initListener() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        PermissionsHelper.unRegisterDangerousPermissionOperation();
    }
}
