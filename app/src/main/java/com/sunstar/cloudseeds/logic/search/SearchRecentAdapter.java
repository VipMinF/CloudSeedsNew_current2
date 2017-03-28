package com.sunstar.cloudseeds.logic.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunstar.cloudseeds.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/17.
 */

public class SearchRecentAdapter extends RecyclerView.Adapter<SearchRecentAdapter.BaseViewHolder> {

    private OnRecentbuttonClickListener mrecentbuttonClickListener;
    private ArrayList<String> dataList = new ArrayList<>();

    public void setOnItemClickListener(OnRecentbuttonClickListener listener) {
        mrecentbuttonClickListener = listener;
    }


    public void replaceAll(ArrayList<String> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searchrecent, parent, false));
    }

    @Override
    public void onBindViewHolder( final BaseViewHolder holder, final int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }


    private class OneViewHolder extends BaseViewHolder {
        private TextView button_searrecent;
        public OneViewHolder(View view) {
            super(view);

            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT);
            //view.setLayoutParams(params);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 120);
            LinearLayout linearLayout_recentBtn=(LinearLayout)view.findViewById(R.id.id_linearlayout_recent_btn);
             param.setMargins(8,15,8,15);
            linearLayout_recentBtn.setLayoutParams(param);
            button_searrecent = (TextView) view.findViewById(R.id.id_searchrecent_button);
            button_searrecent.setClickable(true);
            button_searrecent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mrecentbuttonClickListener.onRecentButtonClick(v);
                }
            });
        }

        @Override
        void setData(Object data) {
            if (data != null) {
                String text = (String) data;
                button_searrecent.setText(text);
            }
        }
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }
        void setData(Object data) {
        }
    }


}
