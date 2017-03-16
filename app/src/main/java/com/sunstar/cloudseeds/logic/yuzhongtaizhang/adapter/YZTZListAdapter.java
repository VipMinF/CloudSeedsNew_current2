package com.sunstar.cloudseeds.logic.yuzhongtaizhang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterViewHolder;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZListBean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public class YZTZListAdapter extends ClassicRVHeaderFooterAdapter<YZTZListBean.ListBean> {
    public YZTZListAdapter(Context mContext, List<YZTZListBean.ListBean> mDataList, int mItemLayoutId) {
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
        TextView id_tv_item_title_bzsj= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_tv_item_title_bzsj);
        TextView id_tv_item_title_bzsl= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_tv_item_title_bzsl);
        TextView id_tv_item_title_dzsl= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_tv_item_title_dzsl);
        TextView id_tv_item_title_xtmc= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_tv_item_title_xtmc);
        TextView id_tv_item_title_zpdh= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_tv_item_title_zpdh);
        TextView id_tv_item_title_zzph= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_tv_item_title_zzph);

        id_tv_item_title_bzsj.setText(mDataList.get(pos).getTime());
        id_tv_item_title_bzsl.setText(mDataList.get(pos).getSow_num());
        id_tv_item_title_dzsl.setText(mDataList.get(pos).getConfirm_num());
        id_tv_item_title_xtmc.setText(mDataList.get(pos).getName());
        id_tv_item_title_zpdh.setText(mDataList.get(pos).getPlant_code());
        id_tv_item_title_zzph.setText(mDataList.get(pos).getSeed_batches());

        Button id_btn_item_show_detail= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_btn_item_show_detail);
        id_btn_item_show_detail.setOnClickListener(new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                    if (onItemOperationListener!=null){
                        onItemOperationListener.onItemShowDetail(pos);
                    }
            }
        });

        Button id_btn_item_show_xuan_zhu= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_btn_item_show_xuan_zhu);
        id_btn_item_show_xuan_zhu.setOnClickListener(new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                if (onItemOperationListener!=null){
                    onItemOperationListener.onItemShowXuanZhu(pos);
                }
            }
        });

        Button id_btn_item_show_qrcode= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_btn_item_show_qrcode);
        id_btn_item_show_qrcode.setOnClickListener(new OnNotFastClickListener() {
            @Override
            protected void onNotFastClick(View view) {
                if (onItemOperationListener!=null){
                    onItemOperationListener.onItemShowQrcode(pos);
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
        public void onItemShowXuanZhu(int position){

        }
    }
}
