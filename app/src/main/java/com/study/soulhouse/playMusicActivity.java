package com.study.soulhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.study.soulhouse.Gson.Song;
import com.study.soulhouse.Until.HttpRequest;
import com.study.soulhouse.Until.HttpResponse;
import com.study.soulhouse.View.PlayMusicView;
import com.study.soulhouse.finalDate.api_url;

public class playMusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SeekBar seekBar=findViewById(R.id.activity_play_music_seeKbar);
        /*
        图片处理成模糊
         */
//        Glide.with(this).load(R.drawable.demo)
//                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
//                .into(imageView)
        Intent intent=getIntent();
        //歌曲播放路径
        String music_url=api_url.music_play_url+intent.getStringExtra("music_id");
        //获得歌曲详细信息路径
        String music_detailed_url=api_url.detailed_song_url+intent.getStringExtra("music_id");
        PlayMusicView playMusicView=findViewById(R.id.activity_play_music_playMusicView);
        //获得详细歌曲信息
        Song song=HttpResponse.getDetailedSongFromSongId(music_detailed_url);
        //设置歌曲名和歌手名
        TextView songName=findViewById(R.id.songName);
        TextView songAuthor=findViewById(R.id.songAuthor);
        songName.setText(song.getName());
        songAuthor.setText(song.getAuthor().getName());
        //初始化playMusicView并播放音乐
        playMusicView.setCoverImage(song.getAlbum().getPicUrl());
        playMusicView.playMusic(music_url);
    }
}