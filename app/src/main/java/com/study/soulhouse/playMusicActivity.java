package com.study.soulhouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.study.soulhouse.Gson.Song;
import com.study.soulhouse.Service.MusicService;
import com.study.soulhouse.Thread.SeekBarThread;
import com.study.soulhouse.Until.HttpRequest;
import com.study.soulhouse.Until.HttpResponse;
import com.study.soulhouse.View.MySeekBarView;
import com.study.soulhouse.View.PlayMusicView;
import com.study.soulhouse.finalDate.api_url;
import com.study.soulhouse.helps.ThreadHelp;

public class playMusicActivity extends AppCompatActivity {
    private PlayMusicView playMusicView;
    private MusicService.MusicBinder musicBinder;
    private ServiceConnection connection;
//    private MusicBroadcastReceiver musicBroadcastReceiver;
    private MySeekBarView mySeekBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mySeekBarView=findViewById(R.id.m_seekBar);
        /*
        图片处理成模糊
         */
//        Glide.with(this).load(R.drawable.demo)
//                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
//                .into(imageView)
        Intent intent=getIntent();
        //获得歌曲详细信息路径
        String music_detailed_url=api_url.detailed_song_url+intent.getStringExtra("music_id");
        //获得详细歌曲信息
        Song song=HttpResponse.getDetailedSongFromSongId(music_detailed_url);
        //找到playMusicView并初始化
        playMusicView=findViewById(R.id.playMusicView);
        playMusicView.initPlayMusicView(song);
        //设置歌曲名和歌手名
        TextView songName=findViewById(R.id.songName);
        TextView songAuthor=findViewById(R.id.songAuthor);
        songName.setText(song.getName());
        songAuthor.setText(song.getAuthor().getName());
        //绑定Service
         connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicBinder=(MusicService.MusicBinder)service;
                musicBinder.setContext(playMusicActivity.this);
                //musicBinder.initMusic(song);
                //将通信机制Binder给playMusicView
                playMusicView.setMusicBinder(musicBinder);
                mySeekBarView.initMySeekBarView(musicBinder);
                //然后播放音乐
                //playMusicView.playMusic();
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent bindIntent=new Intent(this,MusicService.class);
        bindService(bindIntent,connection, Context.BIND_AUTO_CREATE);
        //注册广播
//        musicBroadcastReceiver=new MusicBroadcastReceiver();
//        IntentFilter intentFilter=new IntentFilter();
//        intentFilter.addAction("PlayMusicFinished");
//        intentFilter.addAction("MusicPosition");
//        registerReceiver(musicBroadcastReceiver,intentFilter);
    }
    @Override
    protected void onDestroy() {
        //当退出Activity时，统计时间的线程就要结束
        SeekBarThread.flag=true;
        unbindService(connection);
        playMusicView.unregisterReceiver();
        mySeekBarView.unregisterReceiver();
        super.onDestroy();
    }

}