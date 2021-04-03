package com.study.soulhouse.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.study.soulhouse.R;

public class SongCardView extends LinearLayout {
    Context context;
    ImageView imageView;
    TextView songName,author;
    public SongCardView(Context context) {
        super(context);
        init(context);
    }
    public SongCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public SongCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SongCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    public void init(Context context){
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.songcardview,this,false);
        addView(view);
        songName=view.findViewById(R.id.songName);
        author=view.findViewById(R.id.author);
        imageView=view.findViewById(R.id.imageView);
    }
    /**
     * 点击播放歌曲
     * @param l
     */
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    /**
     * 外部数据导入
     * @param picUrl
     * @param songName
     * @param authorName
     */
    public void initSongCardView(String picUrl,String songName,String authorName){
        Glide.with(context).load(picUrl).into(imageView);
        this.songName.setText(songName+" - ");
        this.author.setText(authorName);
    }
}
