package com.sunstar.cloudseeds.logic.normalrecord.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sunstar.cloudseeds.R;
import com.classichu.photoselector.imagespicker.ImagePickBean;
import com.sunstar.cloudseeds.Util.DensityUtil;
import com.sunstar.cloudseeds.Util.ScreenUtil;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoxian on 2017/4/11.
 */

public class GrideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   public  interface OnRecyclerItemClickLister {
        void onItemClick(View view, int positon);
    }

    private Context context;
    public ArrayList<ImagePickBean> dataSoruce;
    private LayoutInflater inflater;
    private OnRecyclerItemClickLister clickLister;

    private final  int maxImages = 5;
    private final  int TYPE_ADD = 0;
    private final  int TYPE_IMG = 1;
    private Context content;
    private int kWidth ;

    //适配器初始化
    public GrideAdapter(Context context,List<ImagePickBean> dataSoruce,OnRecyclerItemClickLister clickLister) {
        this.context = context;
        this.clickLister = clickLister;
        this.dataSoruce = new ArrayList<ImagePickBean>();
        this.context = context;
        if(dataSoruce != null) this.dataSoruce.addAll(dataSoruce);
         cumulateWidth(context);
    }


    public void addItem() {
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        dataSoruce.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position)
    {
      if (dataSoruce.size() >= maxImages) {
          return TYPE_IMG;
      }

      if (dataSoruce.size() == position) {
          return TYPE_ADD;
      }

      return  TYPE_IMG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("recycleview create",viewType+""+parent);
        inflater = LayoutInflater.from(context);
        View view ;
        RecyclerView.ViewHolder viewHolder;

        if (viewType == TYPE_ADD) {
            view = inflater.inflate(R.layout.item_grid_selectedview,parent,false);
            viewHolder = new AddViewHolder(view);
        }else {
            view = inflater.inflate(com.sunstar.cloudseeds.R.layout.item_grid_imageview,parent,false);
            viewHolder = new ImageviewHolder(view);
        }

        Log.d("viewHolder", ""+viewHolder);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ImageviewHolder) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ((ImageviewHolder) holder).imageView.getLayoutParams();
            layoutParams.width = kWidth;
            layoutParams.height = kWidth;
            ((ImageviewHolder) holder).imageView.setLayoutParams(layoutParams);
            ImageviewHolder imageviewHolder = (ImageviewHolder) holder;
            ImagePickBean imagePickBean = dataSoruce.get(position);
            Log.d("imagurl",""+imagePickBean.getImagePathOrUrl());
              Glide.with(imageviewHolder.imageView.getContext())
                      .load(imagePickBean.getImagePathOrUrl())
                      .error(R.drawable.img_logo)
                      .into(imageviewHolder.imageView);
            imageviewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(position);         }
            });
            imageviewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickLister != null)
                        clickLister.onItemClick(v,101+position);
                }
            });

    }

        if (holder instanceof  AddViewHolder) {
            AddViewHolder addViewHolder = (AddViewHolder) holder;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)addViewHolder.imageView.getLayoutParams();
            layoutParams.width = kWidth - DensityUtil.dip2px(context,6);
            layoutParams.height = kWidth -DensityUtil.dip2px(context,20);
            addViewHolder.imageView.setLayoutParams(layoutParams);
            addViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickLister != null) {
                        clickLister.onItemClick(v,(int)v.getTag());
                    }
                }
            });
        }

        holder.itemView.setTag(position);
      Log.d("reclyceviewposition:",position+"");

    }

    @Override
    public int getItemCount() {
        Log.d("count",""+dataSoruce.size());
        return  dataSoruce.size()>0? dataSoruce.size()<maxImages? dataSoruce.size()+1:dataSoruce.size() :1;
    }


    class ImageviewHolder extends  RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageButton imageButton;
        public  ImageviewHolder(View view) {
           super(view);
            imageView = (ImageView) view.findViewById(R.id.id_normal_record_imageview);
            imageButton = (ImageButton)view.findViewById(R.id.id_normal_record_imageButton);
        }
    }

    class AddViewHolder extends RecyclerView.ViewHolder {
        public  ImageView imageView;
        public TextView textView;
        public AddViewHolder(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.id_normal_record_selectedImageView);
            textView = (TextView)view.findViewById(R.id.id_normal_record_textview);
        }
    }


    //计算宽度
   private void cumulateWidth(Context context) {
        int totalWidth = ScreenUtil.getScreenWidth(context) - DensityUtil.dip2px(context,30) - DensityUtil.dip2px(context,15);
        int spanCount = 4;
        int podding = 3;
        kWidth = (totalWidth - spanCount*2*podding)/ spanCount;
    }


}



