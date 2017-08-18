package com.sunstar.cloudseeds.logic.xuanzhu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.classichu.imageshow.bean.ImageShowBean;
import com.classichu.imageshow.helper.ImageShowDataHelper;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.logic.xuanzhu.bean.RecordListBean;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by xiaoxian on 2017/5/2.
 */

public class RecordListAdapter extends RecyclerView.Adapter {
    public List<RecordListBean.ListInfo> resourcedata;
    private  Context context;
    private  OnclickItemListener listener;
    public   int footLoadState ;
    public interface  OnclickItemListener {
        void onItemClick(View view,int position);
    }

     public  RecordListAdapter(Context context, List<RecordListBean.ListInfo> resoucedata, OnclickItemListener listener) {
         this.resourcedata = resoucedata;
         this.context = context;
         this.listener = listener;
         footLoadState = -1;
     }

    @Override
    public int getItemViewType(int position) {
        if (resourcedata.size() > 0){
            if(resourcedata.size() == position ) {
                return 1;
            }else {
                return 0;
            }
        }else {
            return -1;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater =  LayoutInflater.from(context);
        View itemView;
        if(viewType == 0) {
            itemView = inflater.inflate(R.layout.normal_record_display_item,parent,false);
            return new RecordListViewHolder(itemView);
        }else if (viewType == 1){
            itemView = inflater.inflate(R.layout.recycleview_prograssbar,parent,false);
            return new LoadingViewHolder(itemView);
        }else {
            itemView = inflater.inflate(R.layout.fragment_norecord,parent,false);
            RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(itemView) {
                @Override
                public String toString() {
                    return super.toString();
                }
            };
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof RecordListViewHolder){
            final RecordListBean.ListInfo info = resourcedata.get(position);
            RecordListViewHolder itemView = (RecordListViewHolder)holder;
            itemView.timeTextView.setText(info.getTime());
            itemView.detailTextView.setText(info.getDetail());

            if(info.getImage().size()>0) {
                itemView.imageButton.setVisibility(View.VISIBLE);
                itemView.imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadimage(info.getImage());
                    }
                });
            }else  {
                itemView.imageButton.setVisibility(View.INVISIBLE);

            }
            itemView.farmTextView.setText(info.getFarm_type_name());
            itemView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemClick(v,position);
                    }
                }
            });
        }else if (holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder)holder;
           switch (footLoadState){
               case -1:{
                   loadingViewHolder.textView.setText("上拉加载更多");
                   loadingViewHolder.progressBar.setVisibility(View.INVISIBLE);
                   break;
               }
               case 0: {
                   loadingViewHolder.textView.setText("正在加载中....");
                    loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                   break;
               }
               case 1:{
                   loadingViewHolder.textView.setText("全部加载完成");
                   loadingViewHolder.progressBar.setVisibility(View.INVISIBLE);
               }
           }
        }

    }


    private void loadimage(List<RecordListBean.imageInfo> images) {
        ArrayList<ImageShowBean> imagesArray = new ArrayList();
        for (int i = 0; i < images.size(); i++) {
            RecordListBean.imageInfo imageInfo = images.get(i);
            //String imagesUrl = imageInfo.getBigImageName();
           // String imagesUrl = "http://sucai.qqjay.com/qqjayxiaowo/201210/26/1.jpg";
            String imagesUrl = imageInfo.getBigImageName().replace("\\","/");
            ImageShowBean showBean = new ImageShowBean();
            showBean.setImageUrl(imagesUrl);
            imagesArray.add(showBean);
        }
        ImageShowDataHelper.setDataAndToImageShow(context,imagesArray,0,false);
    }


    @Override
    public int getItemCount() {
        if (resourcedata == null || resourcedata.size() == 0) {
            return 1;
        }
        return resourcedata.size()+1;
    }

    class RecordListViewHolder extends RecyclerView.ViewHolder {
        public TextView timeTextView;
        public TextView farmTextView;
        public TextView detailTextView;
        public ImageButton imageButton;

        public RecordListViewHolder(View itemView) {
            super(itemView);
            timeTextView = (TextView) itemView.findViewById(R.id.id_record_display_timetext);
            farmTextView = (TextView) itemView.findViewById(R.id.id_record_framtext);
            detailTextView = (TextView) itemView.findViewById(R.id.id_record_detail);
            imageButton = (ImageButton) itemView.findViewById(R.id.id_record_imagebutton);
        }
    }


    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.id_prograsstextview);
            progressBar = (ProgressBar) view.findViewById(R.id.id_loading_prograssbar);
        }

    }
}
