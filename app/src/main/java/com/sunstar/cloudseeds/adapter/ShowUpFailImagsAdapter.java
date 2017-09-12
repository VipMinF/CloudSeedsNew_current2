package com.sunstar.cloudseeds.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.classichu.photoselector.imagespicker.ImagePickBean;
import com.sunstar.cloudseeds.R;
import com.sunstar.cloudseeds.adapter.utils.BitmapOptions;
import com.sunstar.cloudseeds.logic.shangpinqi.bean.OnWifiUpLoadImageBean;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/9/11.
 */

public class ShowUpFailImagsAdapter extends RecyclerView.Adapter<ShowUpFailImagsAdapter.MyViewHolder> {
    private Context context;
    private ArrayList mFailUpImgsBean;
    private final LayoutInflater inflater;

    public ShowUpFailImagsAdapter(Context context, ArrayList failUpImgsBean) {
        this.context = context;
        this.mFailUpImgsBean = failUpImgsBean;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ShowUpFailImagsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(inflater.inflate(R.layout.showupfailimgs_ietmm, parent, false));

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ShowUpFailImagsAdapter.MyViewHolder holder, final int position) {
        if (mFailUpImgsBean.size()==0||mFailUpImgsBean.size()<0||mFailUpImgsBean==null){
            return;
        }
        OnWifiUpLoadImageBean onWifiUpLoadImageBean = (OnWifiUpLoadImageBean) mFailUpImgsBean.get(position);
        ImagePickBean imagePickBean = onWifiUpLoadImageBean.getImagePickBean();
        //获取图片的路径
        String imagePathOrUrl = imagePickBean.getImagePathOrUrl();
        //获取图片的时间
        String imageTime = imagePickBean.getImageTime();
        //获取图片的名称
        String imageName = imagePickBean.getImageName();
        //将path转成图片
        Bitmap samplesizeBitmap = new BitmapOptions().get2SamplesizeBitmap(imagePathOrUrl);
        int byteCount = samplesizeBitmap.getByteCount();

        holder.image_failTime.setText(imageTime);
        holder.image_name.setText(imageName);
        holder.image_size.setText(""+byteCount);
        holder.fail_image.setImageBitmap(samplesizeBitmap);

        if (itemClick!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.setItemClick(v,position);
                }
            });
        }
    }



    public ItemClick itemClick;
    public interface ItemClick{
        void setItemClick(View view,int position);
    }

    public void setOnItemClick(ItemClick itemClick){
        this.itemClick=itemClick;
    }

    @Override
    public int getItemCount() {
        return mFailUpImgsBean.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView image_name, image_failTime, image_size;
        ImageView fail_image;

        public MyViewHolder(final View itemView) {
            super(itemView);
            image_name = (TextView) itemView.findViewById(R.id.image_name);
            image_failTime = (TextView) itemView.findViewById(R.id.image_failTime);
            image_size = (TextView) itemView.findViewById(R.id.image_size);
            fail_image = (ImageView) itemView.findViewById(R.id.fail_image);
        }
    }
}
