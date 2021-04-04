package com.study.soulhouse.Service;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.study.soulhouse.Gson.Song;
import com.study.soulhouse.finalDate.MyApplication;
import com.study.soulhouse.helps.MediaPlayerHelp;
public class MusicService extends Service {
    private MediaPlayerHelp mediaPlayerHelp=MediaPlayerHelp.getInstance(MusicService.this);
    private boolean isPrepared=false;
    private Context mContext;
    public MusicService() {

    }
    public class MusicBinder extends Binder{
        /**
         * 播放音乐
         */
        //歌曲模型
        private  OnMusicServiceListener onMusicServiceListener;
        public void setMusicServiceListener(OnMusicServiceListener onMusicServiceListener) {
            this.onMusicServiceListener=onMusicServiceListener;
        }
        public void setContext(Context context){
            mContext=context;
        }
        /**
         * 音乐的初始化
         * 1，如果当前没有音乐正在播放或者需要开启的音乐和正在播放的音乐不同，那么重置播放器，重新给参数，然后start。
         * 2，如果当前音乐正在播放，但是需要开启的音乐和正在播放的音乐相同，那么直接return。
         */
        public void initMusic(Song song){
            if(mediaPlayerHelp.getSong()==null||!mediaPlayerHelp.isPlaying()||!mediaPlayerHelp.getSong().getMusicUrl().equals(song.getMusicUrl())){
                mediaPlayerHelp.reset();
                mediaPlayerHelp.setSong(song);

                mediaPlayerHelp.setPath(song.getMusicUrl());
                    mediaPlayerHelp.prepare();
            }else{
                Intent intent=new Intent("MusicPrepared");
                mContext.sendBroadcast(intent);
                return;
            }
        }
        public void playMusic(){
                        if(!mediaPlayerHelp.isPlaying()){
                            mediaPlayerHelp.start();
                            isPrepared=false;
                        }
        }
        /**
         * 暂停音乐
         */
        public void pauseMusic(){
            mediaPlayerHelp.pause();
        }
        /**
         * 指定播放位置
         */
        public void setTime(int time){
            mediaPlayerHelp.setTime(time);
        }
        public int  getSongTime(){
            return mediaPlayerHelp.getSongTime();
        }
        public int getCurrentPosition(){
            return mediaPlayerHelp.getCurrentPosition();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("534566457567867867897978","5646756755678678");
//                while (mediaPlayerHelp.isPlaying()){
//                    int position=mediaPlayerHelp.getCurrentPosition();
//                    Intent intent=new Intent("MusicPosition");
//                    intent.putExtra("position",position);
//                    sendBroadcast(intent);
//                }
//            }
//        }).start();
        return new MusicBinder();
    }
    public interface OnMusicServiceListener{
        void onPrepared();
    }
}