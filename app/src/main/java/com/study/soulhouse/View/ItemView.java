package com.study.soulhouse.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.soulhouse.Gson.PlayList;
import com.study.soulhouse.Gson.Song;
import com.study.soulhouse.R;
import com.study.soulhouse.adapter.SongCardRecyclerAdapter;
import com.study.soulhouse.adapter.playListRecyclerAdapter;

import java.util.List;
public class ItemView extends LinearLayout {
    private Context context;
    private RecyclerView playlistRecycler,songsRecycler;
    //歌单s适配器
    private playListRecyclerAdapter playListRecyclerAdapter;
    //歌曲s适配器
    private SongCardRecyclerAdapter songCardRecyclerAdapter;
    TextView header;
    LinkView Link;
    //歌单s
    private List<PlayList> playLists;
    //歌曲s
    private List<Song> songs;
    public ItemView(Context context) {
        super(context);
        init(context);

    }

    public ItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);

    }

    /**
     * 向下初始化（内部获取数据）
     * @param context
     */
    public void init(Context context){
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.itemview,this,false);
        addView(view);
        Link=findViewById(R.id.link);
        header=findViewById(R.id.header);
        playlistRecycler=findViewById(R.id.list);
        songsRecycler=playlistRecycler;
    }

    /**
     * 向上初始化（外部引入数据）
     *
     */
    public void InitItemView(List<PlayList> playLists,String header){
        this.header.setText(header);
        this.playLists= playLists;
        playListRecyclerAdapter=new playListRecyclerAdapter(playLists);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        playlistRecycler.setLayoutManager(linearLayoutManager);
        playlistRecycler.setAdapter(playListRecyclerAdapter);
    }
    public void InitItemView(List<Song> songs,String header,String s){
        this.header.setText(header);
        this.songs=songs;
        songCardRecyclerAdapter=new SongCardRecyclerAdapter(songs);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        songsRecycler.setLayoutManager(linearLayoutManager);
        songsRecycler.setAdapter(songCardRecyclerAdapter);
    }
}
