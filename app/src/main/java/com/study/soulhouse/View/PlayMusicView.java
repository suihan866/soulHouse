package com.study.soulhouse.View;

import android.content.Context;
import android.media.MediaPlayer;
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
import com.study.soulhouse.R;
import com.study.soulhouse.Until.HttpResponse;
import com.study.soulhouse.helps.MediaPlayerHelp;

public class PlayMusicView extends FrameLayout {
    Context context;
    ImageView imageView;
    FrameLayout disk;
    Animation playMusic,playNeedle,stopNeedle;
    ImageView needle,play;
    boolean isPlaying=false;
    String music_url;
    private MediaPlayerHelp mediaPlayerHelp;
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
    public void init(Context context){
        this.context=context;
        //获得单一对象mediaPlayerHelp
        mediaPlayerHelp=MediaPlayerHelp.getInstance(context);
        View view= LayoutInflater.from(context).inflate(R.layout.play_music,this,false);
        addView(view);
        ImageView imageView=view.findViewById(R.id.play_music_cover);
        this.imageView=imageView;
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
    public void trigger(){
        if(isPlaying){
                play.setVisibility(VISIBLE);
                pauseMusic();
        }else{
            play.setVisibility(GONE);
            playMusic(music_url);
        }
    }

    /**
     * 1,判断是否是当前歌曲，是的话直接播放即可，否则将播放器重置，然后设置相关数据，再start
     * @param music_url
     */
    public void playMusic(String music_url){
        isPlaying=true;
        //如果当前播放的音乐就是要播放的音乐就不需要重新加载了。
        if(this.music_url!=null&&music_url.equals(this.music_url)){
            mediaPlayerHelp.start();
            disk.startAnimation(playMusic);
            needle.startAnimation(playNeedle);
        }else {
            this.music_url=music_url;
            disk.startAnimation(playMusic);
            needle.startAnimation(playNeedle);
            //如果不是当前的歌曲就必须将播放器重置，否则会报错。
            mediaPlayerHelp.reset();
            //这里的setPath是异步设置，需要进行监听
            mediaPlayerHelp.setPath(music_url);
            mediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnMediaPlayerHelperListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d("onPrepared","fffff");
                    mediaPlayerHelp.start();
                }
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //当音乐播放结束后，mediaPlayer重置，同时触发一次状态改变。
                    isPlaying=false;
                    play.setVisibility(VISIBLE);
                    disk.clearAnimation();
                    needle.startAnimation(stopNeedle);
                    //这里必须重新加载资源。
                    mediaPlayerHelp.setPath(music_url);

                }
            });
        }
    }
    public void pauseMusic(){
            isPlaying=false;
            disk.clearAnimation();
            needle.startAnimation(stopNeedle);
            mediaPlayerHelp.pause();
    }
    public void setCoverImage(String url){
        Glide.with(context).load(url).into(imageView);
    }
}
