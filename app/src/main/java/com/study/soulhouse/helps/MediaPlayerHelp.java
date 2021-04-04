package com.study.soulhouse.helps;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

import com.study.soulhouse.Gson.Song;

/**
 * 创建单例对象类
 */
public class MediaPlayerHelp {
    private static MediaPlayerHelp instance;
    private  MediaPlayer mediaPlayer;
    private Context context;
    Intent intent1=new Intent("PlayMusicFinished");
    Intent intent2=new Intent("MusicPrepared");
    private Song song;

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;
    //由于两个地方都要重写setOnMediaPlayerHelperListener,且接口不同，所以这里进行重载。
    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMediaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMediaPlayerHelperListener;
    }
    public static MediaPlayerHelp getInstance(Context context){
        if(instance==null){
            synchronized (MediaPlayerHelp.class){
                if(instance==null){
                    instance=new MediaPlayerHelp(context);
                }
            }
        }
        return instance;
    }
    private MediaPlayerHelp(Context context){
        this.context=context;
        mediaPlayer=new MediaPlayer();
    }
    /**
     * 设置播放源
     * @param url
     */
    public void setPath(String url){
        try {
            mediaPlayer.setDataSource(url);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 获得音乐的播放状态
     */
    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }
    /**
     * 获得音乐的时长 （准备结束后会调用该方法，没准备不要调用）
     */
    public int getSongTime(){
        return mediaPlayer.getDuration();
    }
    /**
     * 获得音乐当前的播放位置（准备结束后会调用该方法，没准备不要调用）
     */
    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }
    public void setTime(int time){
        mediaPlayer.seekTo(time);
    }
    public void start(){
        mediaPlayer.start();
    }
    public void release(){
        mediaPlayer.release();
    }
    public void reset(){
        release();
        this.mediaPlayer=new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("intent1","wetertyeryg");
                context.sendBroadcast(intent1);
            }
        });
    }
    public void pause(){
        mediaPlayer.pause();
    }
    public void prepare(){
        try {
        mediaPlayer.prepareAsync();
            //由于是异步加载，加载时间不知道，所以要监听加载是否完成，然后进行通知
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                   context.sendBroadcast(intent2);
                }
            });
    }catch (Exception e){
    e.printStackTrace();
        }
    }
    public interface OnMediaPlayerHelperListener{
        void onPrepared();
    }
}
