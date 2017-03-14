package com.sunstar.cloudseeds.logic.xuanzhu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterViewHolder;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.xuanzhu.bean.XuanZhuListBean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public class XuanZhuListAdapter extends ClassicRVHeaderFooterAdapter<XuanZhuListBean.ListBean> {
    public XuanZhuListAdapter(Context mContext, List<XuanZhuListBean.ListBean> mDataList, int mItemLayoutId) {
        super(mContext, mDataList, mItemLayoutId);
    }

    @Override
    public RVHeaderFooterAdapterDelegate setupDelegate() {
        return new RVHeaderFooterAdapterDelegate() {
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public int getItemViewType(int pos) {
                return 0;
            }
        };
    }

    @Override
    public void findBindView(final int pos, ClassicRVHeaderFooterViewHolder classicRVHeaderFooterViewHolder) {
        TextView id_tv_item_title= classicRVHeaderFooterViewHolder.findBindView(R.id.id_tv_item_title);
        id_tv_item_title.setText("选株编号:"+mDataList.get(pos).getSelected_code());


        Button id_btn_item_show_qrcode= classicRVHeaderFooterViewHolder.findBindView(R.id.id_btn_item_show_qrcode);
        id_btn_item_show_qrcode.setOnClickListener(new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                if (onItemOperationListener!=null){
                    onItemOperationListener.onItemShowQrcode(pos);
                }
            }
        });

        Button id_btn_item_show_detail= classicRVHeaderFooterViewHolder.findBindView(R.id.id_btn_item_show_detail);
        id_btn_item_show_detail.setOnClickListener(new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                if (onItemOperationListener!=null){
                    onItemOperationListener.onItemShowDetail(pos);
                }
            }
        });

        Button id_btn_item_show_spqdc= classicRVHeaderFooterViewHolder.findBindView(R.id.id_btn_item_show_spqdc);
        id_btn_item_show_spqdc.setOnClickListener(new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                if (onItemOperationListener!=null){
                    onItemOperationListener.onItemShowSpqDc(pos);
                }
            }
        });
    }

    private  OnItemOperationListener onItemOperationListener;
    public void setOnItemOperationListener(OnItemOperationListener onItemOperationListener) {
        this.onItemOperationListener = onItemOperationListener;
    }
    public abstract static class  OnItemOperationListener{
        public void onItemShowDetail(int position){

        }
        public void onItemShowQrcode(int position){

        }
        public void onItemShowSpqDc(int position){

        }
    }
}
