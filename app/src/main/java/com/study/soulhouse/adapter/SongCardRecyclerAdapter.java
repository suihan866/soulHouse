package com.study.soulhouse.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.soulhouse.Gson.Song;
import com.study.soulhouse.View.SongCardView;

import java.util.List;

public class SongCardRecyclerAdapter extends RecyclerView.Adapter<SongCardRecyclerAdapter.ViewHolder> {
    List<Song> songs;
    Context context;
    public SongCardRecyclerAdapter(List<Song> songs){
        this.songs=songs;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
         SongCardView songCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    @NonNull
    @Override
    public SongCardRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context=parent.getContext();
        SongCardView songCardView=new SongCardView(context);
        ViewHolder viewHolder=new ViewHolder(songCardView);
        viewHolder.songCardView=songCardView;
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull SongCardRecyclerAdapter.ViewHolder holder, int position) {
        Song song=songs.get(position);
        holder.songCardView.initSongCardView(song.getPicUrl(),song.getName(),song.getAuthor().getName());
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
