package com.sunstar.cloudseeds.logic.imageupload.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.classichu.adapter.listview.ClassicBaseAdapter;
import com.classichu.adapter.listview.ClassicBaseViewHolder;
import com.classichu.classichu.basic.extend.DataHolderSingleton;
import com.sunstar.cloudseeds.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by louisgeek on 2016/6/7.
 */
public class MyDialogFragmentGrid extends DialogFragment {

    private final static String TITLE_KEY="TitleKey";
    private final static String MESSAGE_KEY="MessageKey";


    private GridView id_gv_images;
    private List<String> mStringList=new ArrayList<>();
    public static MyDialogFragmentGrid newInstance(String title, String message) {
        MyDialogFragmentGrid myDialogFragment = new MyDialogFragmentGrid();
        Bundle args = new Bundle();

        args.putString(TITLE_KEY, title);
        args.putString(MESSAGE_KEY, message);
        myDialogFragment.setArguments(args);
        return myDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
       View myview = inflater.inflate(R.layout.dialogfrag_content_grid, null);
        id_gv_images= (GridView) myview.findViewById(R.id.id_gv_images);
        mStringList= (List<String>) DataHolderSingleton.getInstance().getData("dialog_mStringList");
        ClassicBaseAdapter<String> adapter=new ClassicBaseAdapter<String>(mStringList, R.layout.item_grid_classic22) {
            @Override
            public void findBindView(int position, ClassicBaseViewHolder classicBaseViewHolder) {
                ImageView imageView=classicBaseViewHolder.findBindItemView(R.id.imageView);
                ViewGroup.LayoutParams vlp=imageView.getLayoutParams();
                vlp.width=270;
                vlp.height=270;
                imageView.setLayoutParams(vlp);
                //
                Glide.with(imageView.getContext())
                        .load(mDataList.get(position))
                        .centerCrop()
                       /* .placeholder(R.drawable.logo)
                        .error(R.drawable.logo)*/
                        .into(imageView);
            }

        };
        id_gv_images.setAdapter(adapter);

        String title = getArguments().getString(TITLE_KEY);
        String message= getArguments().getString(MESSAGE_KEY);


        builder.setTitle(title).setMessage(message).setView(myview).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (onBtnClickListener!=null){
                onBtnClickListener.onBtnClickOk( dialog, which);}
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (onBtnClickListener!=null){
                onBtnClickListener.onBtnClickCancel( dialog, which);}
            }
        });
        // builder.setView(myview).setTitle(title).setMessage(message).setPositiveButton("确定",this).setNegativeButton("取消",this);

        // return super.onCreateDialog(savedInstanceState);
        return   builder.create();

    }

  /*  *//**
     * 在onCreateView中使用  getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);即可去掉

     * @return
     *//*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialogfrag_content_grid,container,false);

        id_gv_images= (GridView) view.findViewById(R.id.id_gv_images);

        mMyCustomTitleView= (TextView) view.findViewById(R.id.id_my_title);

        mTitle=getArguments().getString(TITLE_KEY);

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
    }*/

    public interface  OnBtnClickListener{
        void  onBtnClickOk(DialogInterface dialog, int which);
        void  onBtnClickCancel(DialogInterface dialog, int which);
    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        this.onBtnClickListener = onBtnClickListener;
    }

    OnBtnClickListener onBtnClickListener;

}
