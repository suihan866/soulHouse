package com.study.soulhouse.Gson;

import java.util.List;

public class detailedPlaylist {
    private PlayList playList;
    private List<Song> songs;

    public void setPlayList(PlayList playList) {
        this.playList = playList;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public PlayList getPlayList() {
        return playList;
    }
}
