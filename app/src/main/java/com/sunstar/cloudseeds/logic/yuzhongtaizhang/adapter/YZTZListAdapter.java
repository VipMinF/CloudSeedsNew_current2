package com.sunstar.cloudseeds.logic.yuzhongtaizhang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterAdapter;
import com.classichu.adapter.recyclerview.ClassicRVHeaderFooterViewHolder;
import com.classichu.classichu.basic.listener.OnNotFastClickListener;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.yuzhongtaizhang.bean.YZTZListBean;

import org.apache.commons.lang3.ObjectUtils;

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
        TextView id_yztz_bianhao = classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_yztz_number);
        TextView id_pihao = classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_yztz_pihao);

//        TableRow tableRow_zpbh =  classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_yztz_tab_zpbh);
//        TableRow tableRow_xtbh =  classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_yztz_tab_xtbh);
//        TableRow tableRow_zzph =  classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_yztz_tab_zzph);
//        TableRow tableRow_time =  classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_yztz_tab_time);
//        TableRow tableRow_bzsl =  classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_yztz_tab_bzsl);
//        TableRow tableRow_zzsl =  classicRVHeaderFooterViewHolder.findBindItemView(R.id.id_yztz_tab_zzsl);

//        if (mDataList.get(pos).getPlant_code() == null || mDataList.get(pos).getPlant_code().equals("")) tableRow_zpbh.setVisibility(View.GONE);
//        if (mDataList.get(pos).getName() == null) tableRow_xtbh.setVisibility(View.GONE);
//        if (mDataList.get(pos).getSeed_batches() == null || mDataList.get(pos).getSeed_batches().equals("") ) tableRow_zzph.setVisibility(View.GONE);
//        if (mDataList.get(pos).getTime() == null || mDataList.get(pos).getTime().equals("")) tableRow_time.setVisibility(View.GONE);
//        if (mDataList.get(pos).getSow_num() == null || mDataList.get(pos).getSow_num().equals("")) tableRow_bzsl.setVisibility(View.GONE);
//        if (mDataList.get(pos).getConfirm_num() == null || mDataList.get(pos).getConfirm_num().equals("")) tableRow_zzsl.setVisibility(View.GONE);

//        if(mDataList.get(pos).getSeed_batches().contains("类型")) {
//            id_yztz_bianhao.setText("品种名称:");
//            id_pihao.setText("品种类型:");
//        }else {
//            id_yztz_bianhao.setText("系统编号:");
//            id_pihao.setText("种子批号:");

//        }
        id_tv_item_title_bzsj.setText(mDataList.get(pos).getSeed_batches());
        id_tv_item_title_zzph.setText(mDataList.get(pos).getTime());


        id_tv_item_title_bzsl.setText(mDataList.get(pos).getSow_num());
        id_tv_item_title_dzsl.setText(mDataList.get(pos).getConfirm_num());

        id_tv_item_title_xtmc.setText(mDataList.get(pos).getName());

        id_tv_item_title_zpdh.setText(mDataList.get(pos).getPlant_code());

        String speName = mDataList.get(pos).getSpecies_name();
        String code = mDataList.get(pos).getSeed_batches();
        if (speName != null && !speName.equals("")) {
            id_yztz_bianhao.setText("品种名称:");
            id_pihao.setText("品种类型:");
            id_tv_item_title_bzsj.setText(mDataList.get(pos).getSpecies_type());
            id_tv_item_title_xtmc.setText(mDataList.get(pos).getSpecies_name());
        }

        if (code != null && !code.equals("")) {
            id_yztz_bianhao.setText("系统编号:");
            id_pihao.setText("种子批号:");
        }








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
        //id_btn_item_show_qrcode.setVisibility(View.GONE);//2017年3月30日13:23:25
        if ("1".equals(mDataList.get(pos).getStatus())){
            //已绑定
            id_btn_item_show_qrcode.setText("已绑二维码");
            id_btn_item_show_qrcode.setEnabled(false);
        }else {
            //未绑定
            id_btn_item_show_qrcode.setOnClickListener(new OnNotFastClickListener() {
                @Override
                protected void onNotFastClick(View view) {
                    if (onItemOperationListener!=null){
                        onItemOperationListener.onItemShowQrcode(pos);
                    }
                }
            });
            id_btn_item_show_qrcode.setEnabled(true);
        }

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
        public void onClickItemView(int position) {

        }
    }



}
