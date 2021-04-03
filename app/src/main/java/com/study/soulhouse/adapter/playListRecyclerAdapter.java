package com.study.soulhouse.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.soulhouse.View.PlayLIstCardView;
import com.study.soulhouse.Gson.PlayList;

import java.util.List;

public class playListRecyclerAdapter extends RecyclerView.Adapter<playListRecyclerAdapter.ViewHolder> {
    Context context;
    List<PlayList> playLists;
    public playListRecyclerAdapter(List<PlayList> playLists){
        this.playLists=playLists;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        PlayLIstCardView view;
        public ViewHolder(@NonNull PlayLIstCardView itemView) {
            super(itemView);
            this.view=itemView;
        }
    }
    @NonNull
    @Override
    public playListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        PlayLIstCardView view= new PlayLIstCardView(context);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull playListRecyclerAdapter.ViewHolder holder, int position) {
        PlayList playList=playLists.get(position);
        holder.view.glideImage(playList.getCoverImgUrl());
        holder.view.setName(playList.getName());
    }
    @Override
    public int getItemCount() {
        return playLists.size();
    }
}
