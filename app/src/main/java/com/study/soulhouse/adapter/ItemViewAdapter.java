package com.study.soulhouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.soulhouse.Gson.ItemViewData;
import com.study.soulhouse.R;
import com.study.soulhouse.View.ItemView;
import com.study.soulhouse.View.LinkView;

import java.util.List;

public class ItemViewAdapter extends RecyclerView.Adapter<ItemViewAdapter.ViewHolder> {
    List<ItemViewData> itemData;
    Context context;
    public ItemViewAdapter(List<ItemViewData> itemViewData){
        this.itemData=itemViewData;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView header;
        LinkView link;
        RecyclerView playlist;
        RecyclerView songs;
        ItemView view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    @NonNull
    @Override
    public ItemViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        ItemView view= new ItemView(context);
        ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.view=view;
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ItemViewAdapter.ViewHolder holder, int position) {
        ItemViewData Data=itemData.get(position);
        //根据具体类型来初始化不同的ItemView
        switch (Data.getType()){
            case "playlist":
                holder.view.InitItemView(Data.getPlayLists(),Data.getHeader());
                break;
            case "songs":
                holder.view.InitItemView(Data.getSongs(),Data.getHeader(),null);
                break;
        }
    }
    @Override
    public int getItemCount() {
        return itemData.size();
    }
}
