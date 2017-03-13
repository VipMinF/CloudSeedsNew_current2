package com.sunstar.cloudseeds.logic.yuzhongxuanzhu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterViewHolder;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.yuzhongxuanzhu.bean.XuanZhuBean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/13.
 */

public class XuanZhuAdapter extends ClassicRVHeaderFooterAdapter<XuanZhuBean.ListBean> {
    public XuanZhuAdapter(Context mContext, List<XuanZhuBean.ListBean> mDataList, int mItemLayoutId) {
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
        TextView id_tv_item_title= classicRVHeaderFooterViewHolder.findBindView(R.id.id_tv_item_title);
        id_tv_item_title.setText("选株编号:"+mDataList.get(pos).getSelected_code());
    }
}
