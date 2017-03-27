package com.sunstar.cloudseeds.logic.imageupload.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sunstar.cloudseeds.R;

/**
 * Created by louisgeek on 2016/6/7.
 */
public class MyDialogFragmentProgress extends DialogFragment {

    private final static String TITLE_KEY="TitleKey";
    private final static String TITLE_CUSTOM_TITLE="CustomTitleKey";
    ProgressBar mProgressBar;
    TextView mMyCustomTitleView;

    String mTitle;
    String mCustomTitle;
    public static MyDialogFragmentProgress newInstance(String title, String customTitle) {
        MyDialogFragmentProgress myDialogFragment = new MyDialogFragmentProgress();
        Bundle args = new Bundle();
        // 自定义的标题
        args.putString(TITLE_KEY, title);
        args.putString(TITLE_CUSTOM_TITLE, customTitle);
        myDialogFragment.setArguments(args);
        return myDialogFragment;
    }

    /**
     * 在onCreateView中使用  getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);即可去掉
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialogfrag_content_progress,container,false);

        mProgressBar= (ProgressBar) view.findViewById(R.id.id_pb);
        mProgressBar.setMax(100);

        mMyCustomTitleView= (TextView) view.findViewById(R.id.id_my_title);

        mTitle=getArguments().getString(TITLE_KEY);
        mCustomTitle=getArguments().getString(TITLE_CUSTOM_TITLE);


        this.setCancelable(false);
        if (mCustomTitle!=null&&!mCustomTitle.equals("")){
            mMyCustomTitleView.setText(mCustomTitle);
            this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        }else if (mTitle!=null&&!mTitle.equals("")){
            this.getDialog().setTitle(mTitle);
        }else{
            this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        //return super.onCreateView(inflater, container, savedInstanceState);
        return  view;
    }

    public void updateProgress(int allCount,int progress){
        //if (mProgressBar!=null) {
            mProgressBar.setProgress(progress);
            mMyCustomTitleView.setText(mCustomTitle+"("+allCount+":"+ String.valueOf(progress)+"%)");
       // }
    }
    public void updateAddFinshText(){
        //if (mProgressBar!=null) {
        mMyCustomTitleView.setText(mCustomTitle+"已完成！");
        // }
    }

}
