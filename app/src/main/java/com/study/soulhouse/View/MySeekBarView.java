package com.study.soulhouse.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.study.soulhouse.R;
import com.study.soulhouse.Service.MusicService;
import com.study.soulhouse.Thread.SeekBarThread;
import com.study.soulhouse.helps.MediaPlayerHelp;
import com.study.soulhouse.helps.ThreadHelp;

import java.text.SimpleDateFormat;

public class MySeekBarView extends LinearLayout {
    Context context;
    SeekBar seekBar;
    ImageView next,last;
    private MusicService.MusicBinder musicBinder;
    TextView endTime,startTime;
    int songTime;
    private IntentFilter intentFilter;
    private MusicPreparedReceiver musicPreparedReceiver;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            startTime.setText((String)msg.obj);
        }
    };
    public MySeekBarView(Context context) {
        super(context);
        init(context);
    }
    public MySeekBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public MySeekBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public MySeekBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    //内部初始化
    public void init(Context context) {
        intentFilter=new IntentFilter();
        intentFilter.addAction("MusicPrepared");
        musicPreparedReceiver=new MusicPreparedReceiver();
        context.registerReceiver(musicPreparedReceiver,intentFilter);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.play_music_bottom, this, false);
        addView(view);
        seekBar = view.findViewById(R.id.seeKbar);
        next = view.findViewById(R.id.play_music_next);
        last = view.findViewById(R.id.play_music_last);
        endTime = view.findViewById(R.id.play_music_endTime);
        startTime = view.findViewById(R.id.play_music_startTime);

    }
    /**
     * 从外部传入数据
     * @param musicBinder
     */
    public void initMySeekBarView(MusicService.MusicBinder musicBinder){
        this.musicBinder=musicBinder;
    }
    public void setPosition(int position){
        seekBar.setProgress(position);
    }
    class MusicPreparedReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            //获取歌曲时长，只有当准备完毕后才能获得,时长通过mediaPlayHelp暴露接口。
            songTime=musicBinder.getSongTime();
            seekBar.setMax(songTime);
            String strDateFormat="mm:ss";
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(strDateFormat);
            startTime.setText(simpleDateFormat.format(0));
            endTime.setText(simpleDateFormat.format(songTime));
            Log.d("simpleDateFormat.format(songTime)",simpleDateFormat.format(songTime));
            //准备完成后为seekBar设置监听
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    musicBinder.setTime(seekBar.getProgress());
                }
            });
            //新开一个线程进行进度条播放
                if(ThreadHelp.thread!=null){
                    SeekBarThread.flag=true;
                }
                try{
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                SeekBarThread thread=new SeekBarThread(new SeekBarThread.MyRunnable(){
                    @Override
                    public void run() {
                        while(!SeekBarThread.flag&&seekBar.getProgress()<=songTime){
                            int currentTime=musicBinder.getCurrentPosition();
                            seekBar.setProgress(currentTime);
                            Message message=new Message();
                            message.obj=simpleDateFormat.format(currentTime);
                            try{
                                Thread.sleep(500);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            handler.sendMessage(message);
                        }
                    }
                });
                ThreadHelp.thread=thread;
                SeekBarThread.flag=false;
                thread.start();
                //context.unregisterReceiver(musicPreparedReceiver);
        }
    }
    public void unregisterReceiver(){
        context.unregisterReceiver(musicPreparedReceiver);
    }
}
