package com.study.soulhouse.Gson;

public class Song {
    private String name;
    private String id;
    private  SongAuthor author;
    private Album album;
    private String picUrl;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAuthor(SongAuthor author) {
        this.author = author;
    }

    public SongAuthor getAuthor() {
        return author;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }
}
