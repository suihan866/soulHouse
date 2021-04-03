package com.study.soulhouse.Until;

import android.graphics.Paint;
import android.util.Log;

import com.study.soulhouse.Gson.Album;
import com.study.soulhouse.Gson.Song;
import com.study.soulhouse.Gson.SongAuthor;
import com.study.soulhouse.Gson.PlayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import com.study.soulhouse.Gson.detailedPlaylist;
public class HttpResponse {
    //搜索获得推荐歌单
    public static List<PlayList> responseForPlaylist(String address){
        List<PlayList> result=new ArrayList<>();
        HttpRequest.request(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("responseForPlaylist","fail");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    JSONArray jsonArray=jsonObject.getJSONArray("result");
                    synchronized (result){
                        for(int i=0; i<jsonArray.length(); i++){
                            PlayList temp=new PlayList();
                            JSONObject json=jsonArray.getJSONObject(i);
                            temp.setCoverImgUrl(json.getString("picUrl"));
                            temp.setName(json.getString("name"));
                            temp.setId(json.getString("id"));
                            result.add(temp);
                        }
                        result.notify();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //等待响应数据加载完成。
        try {
            synchronized (result){
                result.wait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    //获得搜索的歌曲列表
    public static List<Song> responseForSong(String address) {
        List<Song> result = new ArrayList<>();
                HttpRequest.request(address, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            synchronized (result){
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                jsonObject = jsonObject.getJSONObject("result");
                                JSONArray jsonArray = jsonObject.getJSONArray("songs");
                                Log.d("ttt",""+jsonArray.length());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    Song song = new Song();
                                    song.setId(jsonArray.getJSONObject(i).getString("id"));
                                    song.setName(jsonArray.getJSONObject(i).getString("name"));
                                    SongAuthor songAuthor=new SongAuthor();
                                    songAuthor.setName(jsonArray.getJSONObject(i).getJSONArray("artists").getJSONObject(0).getString("name"));
                                    songAuthor.setId(jsonArray.getJSONObject(i).getJSONArray("artists").getJSONObject(0).getString("id"));
                                    song.setAuthor(songAuthor);
                                    result.add(song);
                                }
                                result.notify();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        try {
            synchronized (result){
                result.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("fff",result.size()+"");
        return result;
    }
    //通过歌单id获得详细歌单(包含歌曲)
    public static detailedPlaylist getDetailedPlaylistFromPlaylist(String address){
        detailedPlaylist result=new detailedPlaylist();
        HttpRequest.request(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    synchronized (result){
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        jsonObject=jsonObject.getJSONObject("playlist");
                        PlayList playList=new PlayList();
                        playList.setId(jsonObject.getString("id"));
                        playList.setName(jsonObject.getString("name"));
                        playList.setDescription(jsonObject.getString("description"));
                        playList.setCoverImgUrl(jsonObject.getString("coverImgUrl"));
                        result.setPlayList(playList);
                        List<Song> songs=new ArrayList<>();
                        JSONArray jsonArray=jsonObject.getJSONArray("tracks");
                        Log.d("HttpResponse/jsonArray.length",""+jsonArray.length());
                        for(int i=0; i<jsonArray.length(); i++){

                            Song song=new Song();
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            song.setName(jsonObject1.getString("name"));
                            song.setId(jsonObject1.getString("id"));
                            JSONArray jsonArray1=jsonObject1.getJSONArray("ar");
                            JSONObject jsonObject2=jsonArray1.getJSONObject(0);
                            SongAuthor songAuthor=new SongAuthor();
                            songAuthor.setId(jsonObject2.getString("id"));
                            songAuthor.setName(jsonObject2.getString("name"));
                            song.setAuthor(songAuthor);
                            songs.add(song);

                        }
                        Log.d("HttpResponse",""+songs.size());
                        result.setSongs(songs);
                        result.notify();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        synchronized (result){
            try {
                result.wait();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return result;
    }
    //通过歌曲id获得详细歌曲
    public static Song getDetailedSongFromSongId(String address){
        Song song=new Song();
        HttpRequest.request(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    synchronized (song){
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        JSONArray jsonArray=jsonObject.getJSONArray("songs");
                        jsonObject=jsonArray.getJSONObject(0);
                        song.setId(jsonObject.getString("id"));
                        song.setName(jsonObject.getString("name"));
                        SongAuthor songAuthor=new SongAuthor();
                        jsonArray=jsonObject.getJSONArray("ar");
                        JSONObject temp=jsonObject;
                        jsonObject=jsonArray.getJSONObject(0);
                        songAuthor.setName(jsonObject.getString("name"));
                        songAuthor.setId(jsonObject.getString("id"));
                        song.setAuthor(songAuthor);
                        jsonObject=temp.getJSONObject("al");
                        Album album=new Album();
                        album.setId(jsonObject.getString("id"));
                        album.setName(jsonObject.getString("name"));
                        album.setPicUrl(jsonObject.getString("picUrl"));
                        song.setAlbum(album);
                        song.notify();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        synchronized (song){
            try{
               song.wait();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return song;
    }
    //获得推荐的新音乐
    public static List<Song> getRecommendSong(String address){
        List<Song> songs=new ArrayList<>();
        HttpRequest.request(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                synchronized (songs){
                    try {
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        JSONArray result=jsonObject.getJSONArray("result");
                        for(int i=0; i<result.length(); i++){
                            //获取歌曲信息
                            Song song=new Song();
                            jsonObject=result.getJSONObject(i);
                            song.setId(jsonObject.getString("id"));
                            song.setName(jsonObject.getString("name"));
                            song.setPicUrl(jsonObject.getString("picUrl"));
                            //获得作者信息
                            JSONObject artist=jsonObject.getJSONObject("song").getJSONArray("artists").getJSONObject(0);
                            SongAuthor author=new SongAuthor();
                            author.setId(artist.getString("id"));
                            author.setName(artist.getString("name"));
                            song.setAuthor(author);
                            //获取专辑信息
                            JSONObject albumData=jsonObject.getJSONObject("song").getJSONObject("album");
                            Album album=new Album();
                            album.setPicUrl(albumData.getString("picUrl"));
                            album.setName(albumData.getString("name"));
                            album.setId(albumData.getString("id"));
                            song.setAlbum(album);
                            //加入列表
                            songs.add(song);
                        }
                        songs.notify();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        synchronized (songs){
            try {
                songs.wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return songs;
    }
}
