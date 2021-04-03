package com.study.soulhouse.helps;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * 创建单例对象类
 */
public class MediaPlayerHelp {
    private static MediaPlayerHelp instance;
    private  MediaPlayer mediaPlayer;
    private Context context;
    private int songTime;
    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;
    private OnSeekBarListener onSeekBarListener;
    //由于两个地方都要重写setOnMediaPlayerHelperListener,且接口不同，所以这里进行重载。
    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMediaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMediaPlayerHelperListener;
    }
    public void setOnMediaPlayerHelperListener(OnSeekBarListener onSeekBarListener){
        this.onSeekBarListener=onSeekBarListener;
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
        if(mediaPlayer.isPlaying()){
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(url);
            //由于网络加载有延时，这里采用异步加载
            mediaPlayer.prepareAsync();
        }catch (Exception e){
            e.printStackTrace();
        }
        //由于是异步加载，加载时间不知道，所以要监听加载是否完成，然后进行通知
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if(onMediaPlayerHelperListener!=null){
                    onMediaPlayerHelperListener.onPrepared(mp);
                }
                if(onSeekBarListener!=null){
                    songTime=mediaPlayer.getDuration();
                    onSeekBarListener.onPrepared();
                }
            }
        });
        //每次音频播放完后处于stop状态，此时要让mediaPlayer重置，重用处于错误状态的mediaPlayer
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(onMediaPlayerHelperListener!=null){
                    onMediaPlayerHelperListener.onCompletion(mp);
                }
            }
        });
    }

    /**
     * 获得音乐的时长 （准备结束后会调用该方法，没准备不要调用）
     */
    public int getSongTime(){
        return songTime;
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
        if(mediaPlayer.isPlaying()) return;

        mediaPlayer.start();
    }
    public void reset(){
        mediaPlayer.reset();
    }
    public void pause(){
        mediaPlayer.pause();
    }
    public interface OnMediaPlayerHelperListener{
        void onPrepared(MediaPlayer mp);
        void onCompletion(MediaPlayer mp);
    }
    public interface OnSeekBarListener{
        void onPrepared();
    }
}
