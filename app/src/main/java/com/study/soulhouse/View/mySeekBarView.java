package com.study.soulhouse.View;

import android.content.Context;
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
import com.study.soulhouse.helps.MediaPlayerHelp;

import java.text.SimpleDateFormat;

public class mySeekBarView extends LinearLayout {
    Context context;
    SeekBar seekBar;
    ImageView next,last;
    MediaPlayerHelp mediaPlayerHelp;
    TextView endTime,startTime;
    int songTime;
    public mySeekBarView(Context context) {
        super(context);
        init(context);
    }

    public mySeekBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public mySeekBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public mySeekBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    public void init(Context context){
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.play_music_bottom,this,false);
        addView(view);
        seekBar=view.findViewById(R.id.activity_play_music_seeKbar);
        next=view.findViewById(R.id.play_music_next);
        last=view.findViewById(R.id.play_music_last);
        endTime=view.findViewById(R.id.play_music_endTime);
        startTime=view.findViewById(R.id.play_music_startTime);
        Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                startTime.setText((String)msg.obj);
            }
        };
        //获得单一播放器
        mediaPlayerHelp=MediaPlayerHelp.getInstance(context);
        //播放器暴露出准备完毕的接口，在这里进行使用。
        mediaPlayerHelp.setOnMediaPlayerHelperListener(new MediaPlayerHelp.OnSeekBarListener() {
            @Override
            public void onPrepared() {
                //获取歌曲时长，只有当准备完毕后才能获得,时长通过mediaPlayHelp暴露接口。
                songTime=mediaPlayerHelp.getSongTime();
                Log.d("songTime",""+songTime);
                seekBar.setMax(songTime);
                String strDateFormat="mm:ss";
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat(strDateFormat);
                endTime.setText(simpleDateFormat.format(songTime));
                //新开一个线程进行进度条播放
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(seekBar.getProgress()<=songTime){
                            int currentTime=mediaPlayerHelp.getCurrentPosition();
                            seekBar.setProgress(currentTime);
                            Message message=new Message();
                            
                            message.obj=simpleDateFormat.format(currentTime);
                            handler.sendMessage(message);
                        }
                    }
                }).start();
            }
        });
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
                mediaPlayerHelp.setTime(seekBar.getProgress());
            }
        });
    }
}
