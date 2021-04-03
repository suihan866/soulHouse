package com.study.soulhouse.Gson;

import java.util.List;

public class ItemViewData {
    //需要装配的数据类型
    private String type;
    //标题
   private String header;
   //歌单列表
    private List<PlayList> playLists;
    //歌曲列表
    private List<Song> songs;
    public void setHeader(String header) {
        this.header = header;
    }

    public List<PlayList> getPlayLists() {
        return playLists;
    }

    public void setPlayLists(List<PlayList> playLists) {
        this.playLists = playLists;
    }

    public String getHeader() {
        return header;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
