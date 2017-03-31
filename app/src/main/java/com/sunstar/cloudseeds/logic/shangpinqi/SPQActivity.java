package com.sunstar.cloudseeds.logic.shangpinqi;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.classichu.classichu.classic.ClassicActivity;
import com.classichu.dialogview.manager.DialogManager;
import com.classichu.dialogview.ui.ClassicDialogFragment;
import com.classichu.titlebar.widget.ClassicTitleBar;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.data.AtyGoToWhere;
import com.sunstar.cloudseeds.logic.shangpinqi.ui.SPQAddFragment;
import com.sunstar.cloudseeds.logic.shangpinqi.ui.SPQDetailFragment;

public class SPQActivity extends ClassicActivity {
    private String mNowTertiary_id;
    @Override
    protected int setupLayoutResId() {
        return R.layout.activity_spq;
    }
    private  int goToWhere;
    private  SPQAddFragment sPQAddFragment;
    @Override
    protected void initView() {
         goToWhere=getBundleExtraInt1();

        Bundle bundle=getBundleExtra();
        mNowTertiary_id=bundle.getString("Tertiary_id");
        sPQAddFragment=SPQAddFragment.newInstance(mNowTertiary_id,"");
        switch (goToWhere){
            case AtyGoToWhere.LIST:
         /*       setAppBarTitle("育种台账计划");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, YZTZListFragment.newInstance("",""))
                        .commitAllowingStateLoss();*/
              /*  setAppBarTitle("商品期调查记录dsadasd");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, SPQAddFragment.newInstance("",""))
                        .commitAllowingStateLoss();*/
                break;
            case AtyGoToWhere.DETAIL:
                setAppBarTitle("商品期调查");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, SPQDetailFragment.newInstance(mNowTertiary_id,""))
                        .commitAllowingStateLoss();
                break;
            case AtyGoToWhere.ADD:
                setAppBarTitle("商品期调查记录");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.id_frame_layout_content, sPQAddFragment)
                        .commitAllowingStateLoss();
                break;
        }
    }

    @Override
    protected void initListener() {

        if (goToWhere==AtyGoToWhere.ADD) {
            mClassicTitleBar.setOnTitleBarLeftItemClickListener(new ClassicTitleBar.OnTitleBarLeftItemClickListener() {
                @Override
                public void onLeftClick(View view) {
                    DialogManager.showClassicDialog(SPQActivity.this, "提示", "数据未保存！是否保存？", new ClassicDialogFragment.OnBtnClickListener() {
                        @Override
                        public void onBtnClickOk(DialogInterface dialogInterface) {
                            super.onBtnClickOk(dialogInterface);
                            if (sPQAddFragment!=null){
                                sPQAddFragment.submitData();
                            }
                        }

                        @Override
                        public void onBtnClickCancel(DialogInterface dialogInterface) {
                            super.onBtnClickCancel(dialogInterface);
                            //
                            finish();
                        }
                    });
                }
            });
        }
    }


    @Override
    protected AppBarStyle configAppBarStyle() {
        return AppBarStyle.ClassicTitleBar;
    }


}
