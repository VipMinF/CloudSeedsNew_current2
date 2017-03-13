package com.sunstar.cloudseeds.logic.yuzhongtaizhang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterViewHolder;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZBean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public class YZTZAdapter extends ClassicRVHeaderFooterAdapter<YZTZBean.ListBean> {
    public YZTZAdapter(Context mContext, List<YZTZBean.ListBean> mDataList, int mItemLayoutId) {
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
    public void findBindView(int pos, ClassicRVHeaderFooterViewHolder classicRVHeaderFooterViewHolder) {
        TextView id_tv_item_title_bzsj= classicRVHeaderFooterViewHolder.findBindView(R.id.id_tv_item_title_bzsj);
        TextView id_tv_item_title_bzsl= classicRVHeaderFooterViewHolder.findBindView(R.id.id_tv_item_title_bzsl);
        TextView id_tv_item_title_dzsl= classicRVHeaderFooterViewHolder.findBindView(R.id.id_tv_item_title_dzsl);
        TextView id_tv_item_title_xtmc= classicRVHeaderFooterViewHolder.findBindView(R.id.id_tv_item_title_xtmc);
        TextView id_tv_item_title_zpdh= classicRVHeaderFooterViewHolder.findBindView(R.id.id_tv_item_title_zpdh);
        TextView id_tv_item_title_zzph= classicRVHeaderFooterViewHolder.findBindView(R.id.id_tv_item_title_zzph);
        id_tv_item_title_bzsj.setText(mDataList.get(pos).getTime());
        id_tv_item_title_bzsl.setText(mDataList.get(pos).getSow_num());
        id_tv_item_title_dzsl.setText(mDataList.get(pos).getConfirm_num());
        id_tv_item_title_xtmc.setText(mDataList.get(pos).getName());
        id_tv_item_title_zpdh.setText(mDataList.get(pos).getPlant_code());
        id_tv_item_title_zzph.setText(mDataList.get(pos).getSeed_batches());
    }
}
