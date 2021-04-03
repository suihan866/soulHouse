package com.study.soulhouse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.soulhouse.Gson.ItemViewData;
import com.study.soulhouse.Gson.PlayList;
import com.study.soulhouse.Gson.Song;
import com.study.soulhouse.Until.HttpResponse;
import com.study.soulhouse.adapter.ItemViewAdapter;
import com.study.soulhouse.finalDate.MyApplication;
import com.study.soulhouse.finalDate.api_url;

import java.util.ArrayList;
import java.util.List;

public class recommendFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.recommend_fragment,container,false);
        //设置每个ItemView参数
        List<ItemViewData> itemData=new ArrayList<>();
        //设置歌单列表
        ItemViewData item1=new ItemViewData();
        item1.setHeader("推荐歌单");
        item1.setType("playlist");
        List<PlayList> playlist= HttpResponse.responseForPlaylist(api_url.recommend_playlist_url);
        item1.setPlayLists(playlist);
        itemData.add(item1);
        //设置歌曲列表
        ItemViewData item2=new ItemViewData();
        item2.setType("songs");
        item2.setHeader("推荐新音乐");
        List<Song> songs=HttpResponse.getRecommendSong(api_url.recommend_songs_url);
        item2.setSongs(songs);
        Log.d("recommendFragment",""+songs.get(0).getPicUrl());
        itemData.add(item2);
        //构建itemView的数据适配器
        ItemViewAdapter itemViewAdapter=new ItemViewAdapter(itemData);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MyApplication.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //将适配器传入RecyclerView
        RecyclerView recyclerView=view.findViewById(R.id.main_recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemViewAdapter);
//        playListRecyclerAdapter playListRecyclerAdapter=new playListRecyclerAdapter(playlist);
        //将专辑封面填入recyclerView中
//        RecyclerView recyclerView=findViewById(R.id.main_recyclerView);
//        recyclerView.setAdapter(playListRecyclerAdapter);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
        //搜索功能
//        SearchView searchView=view.findViewById(R.id.main_searchView);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                songs=HttpResponse.responseForSong(api_url.music_url+query);
//                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
//                intent.putExtra("url", api_url.music_url+query);
//                startActivity(intent);
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        //加深字体
//        TextView today_recommend=findViewById(R.id.activity_main_recommend);
//        TextPaint textPaint=today_recommend.getPaint();
//        textPaint.setStrokeWidth(10.0f);

        return view;
    }
}
