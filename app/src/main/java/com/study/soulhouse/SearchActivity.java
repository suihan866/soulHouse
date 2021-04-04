package com.study.soulhouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.study.soulhouse.Gson.Song;
import com.study.soulhouse.Until.HttpResponse;
import com.study.soulhouse.adapter.songsRecyclerAdapter;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        RecyclerView recyclerView=findViewById(R.id.searchRecyclerView);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        List<Song> songs= HttpResponse.responseForSong(url);
        for (Song song : songs) {
            Log.d("songs",""+song.getId());
        }
        Log.d("songs",songs.size()+"");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        songsRecyclerAdapter songsRecyclerAdapter =new songsRecyclerAdapter(songs);
        recyclerView.setAdapter(songsRecyclerAdapter);
    }
}