package com.study.soulhouse.View;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.study.soulhouse.Gson.Song;
import com.study.soulhouse.R;
import com.study.soulhouse.Service.MusicService;
import com.study.soulhouse.Until.HttpResponse;
import com.study.soulhouse.helps.MediaPlayerHelp;

public class PlayMusicView extends FrameLayout {
    Context context;
    ImageView imageView;
    FrameLayout disk;
    Animation playMusic,playNeedle,stopNeedle;
    ImageView needle,play;
    boolean isPlaying=false;
    private MusicPreparedReceiver musicPreparedReceiver;
    IntentFilter intentFilter;
    Song song;
    //与Service进行交互的机制。
    private MusicService.MusicBinder musicBinder;
    public PlayMusicView(@NonNull Context context) {
        super(context);
        init(context);
    }
    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    /**
     * 内部初始化
     * @param context
     */
    public void init(Context context){
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.play_music,this,false);
        addView(view);
        intentFilter=new IntentFilter();
        intentFilter.addAction("MusicPrepared");
        intentFilter.addAction("PlayMusicFinished");
        musicPreparedReceiver=new MusicPreparedReceiver();
        context.registerReceiver(musicPreparedReceiver,intentFilter);
        imageView=view.findViewById(R.id.play_music_cover);
        playMusic= AnimationUtils.loadAnimation(context,R.anim.play_music_anim);
        playNeedle=AnimationUtils.loadAnimation(context,R.anim.play_needle_anim);
        stopNeedle=AnimationUtils.loadAnimation(context,R.anim.stop_needle_anim);
        disk=view.findViewById(R.id.disk);
        disk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                trigger();
            }
        });
        play=view.findViewById(R.id.play);
        needle=view.findViewById(R.id.needle);
    }

    /**
     * 外部接口，传入Song
     * @param song
     */
    public void initPlayMusicView(Song song){
        this.song=song;
        Glide.with(context).load(song.getPicUrl()).into(imageView);
    }
    public void setMusicBinder(MusicService.MusicBinder musicBinder){
        this.musicBinder=musicBinder;
        musicBinder.initMusic(song);
    }
    //进行歌曲播放状态切换
    public void trigger(){
        if(isPlaying){
                play.setVisibility(VISIBLE);
                pauseMusic();
        }else{
            play.setVisibility(GONE);
            playMusic();
        }
    }
    public void playMusic(){
        isPlaying=true;
        disk.startAnimation(playMusic);
        needle.startAnimation(playNeedle);
        musicBinder.playMusic();
    }
    public void pauseMusic(){
            isPlaying=false;
            disk.clearAnimation();
            needle.startAnimation(stopNeedle);
            musicBinder.pauseMusic();
    }

    /**
     * 接收music的消息
     */
    class MusicPreparedReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("PlayMusicFinished")){
                Log.d("PlayMusicFinished","ertyerty");
                pauseMusic();
            }else{
                isPlaying=true;
                disk.startAnimation(playMusic);
                musicBinder.playMusic();
            }
            //context.unregisterReceiver(musicPreparedReceiver);
        }
    }
    public void unregisterReceiver(){
        context.unregisterReceiver(musicPreparedReceiver);
    }
}
