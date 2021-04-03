package com.study.soulhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.study.soulhouse.Gson.Song;
import com.study.soulhouse.R;
import com.study.soulhouse.playMusicActivity;
import java.util.List;

public class songsRecyclerAdapter extends RecyclerView.Adapter<songsRecyclerAdapter.viewHolder> {
    List<Song> songs;
    Context context;
    public songsRecyclerAdapter(List<Song> songs){
        this.songs=songs;
    }
    class viewHolder extends RecyclerView.ViewHolder{
        TextView songName;
        TextView order;
        TextView author;
        View view;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.view=itemView;
            order=itemView.findViewById(R.id.order);
            author=itemView.findViewById(R.id.author);
            songName=itemView.findViewById(R.id.textView_song_name);
        }
    }
    @NonNull
    @Override
    public songsRecyclerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        View view= LayoutInflater.from(context).inflate(R.layout.song,parent,false);
        viewHolder viewHolder=new viewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=viewHolder.getAdapterPosition();
                Intent intent=new Intent(context,playMusicActivity.class);
                intent.putExtra("music_id",songs.get(position).getId());
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull songsRecyclerAdapter.viewHolder holder, int position) {
        Song song=songs.get(position);
        holder.order.setText(""+(position+1));
        holder.songName.setText(song.getName());
        holder.author.setText(song.getAuthor().getName());
    }
    @Override
    public int getItemCount() {
        return songs.size();
    }
}
