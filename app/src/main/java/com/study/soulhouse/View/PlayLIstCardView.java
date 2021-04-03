package com.study.soulhouse.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.study.soulhouse.R;

public class PlayLIstCardView extends FrameLayout {
    private ImageView image;
    private TextView name;
    private Context context;
    public PlayLIstCardView(@NonNull Context context) {
        super(context);
        init(context);
    }
    public PlayLIstCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public PlayLIstCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public PlayLIstCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    public void init(Context context){
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.playlistcardview,this,false);
        addView(view);
        image=findViewById(R.id.imageView);
        name=findViewById(R.id.name);
    }
    public void glideImage(String url){
        Glide.with(context).load(url).into(image);
    }
    public void setName(String text){
        name.setText(text);
    }
    @Override
    /**
     * 当点击MyCardView后会打开歌单界面
     */
    public void setOnClickListener(@Nullable OnClickListener l){
        //super.setOnClickListener(l);
    }
}
