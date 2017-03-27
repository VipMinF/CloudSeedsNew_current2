package com.sunstar.cloudseeds.logic.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterViewHolder;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.main.bean.TaiZhangBean;

import java.util.List;

/**
 * Created by louisgeek on 2017/3/10.
 */

public class MainAdapter extends ClassicRVHeaderFooterAdapter<TaiZhangBean.ListBean>{


    public MainAdapter(Context mContext, List<TaiZhangBean.ListBean> mDataList, int mItemLayoutId) {
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
       TextView id_tv_item_title= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_tv_item_title);
       TextView id_tv_item_more= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_tv_item_more);
       TextView id_tv_item_plan= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_tv_item_plan);
       TextView id_tv_item_product= classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_tv_item_product);

      id_tv_item_title.setText(mDataList.get(pos).getName());
        id_tv_item_more.setText(mDataList.get(pos).getTime());
        id_tv_item_product.setText(mDataList.get(pos).getProt_ype());
        id_tv_item_plan.setText(mDataList.get(pos).getPlan_type());

    }
}
