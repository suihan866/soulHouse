package com.study.soulhouse.finalDate;

public class api_url {
    //推荐歌单(不建议使用)
    public final static String hot_playlist_url="http://musicapi.leanapp.cn/top/playlist?limit=20&order=new";
    //每次获取20条数据
    //推荐歌单
    public final static String recommend_playlist_url="http://musicapi.leanapp.cn/personalized?limit=20";
    //歌曲url(和搜索框组合)
    public final static String music_url="http://musicapi.leanapp.cn/search?keywords=";
    //歌曲播放(和点击的歌曲组合）
    public final static String music_play_url="https://music.163.com/song/media/outer/url?id=";
    //获取歌单的详细信息
    //public final static String detailed_playlist_url="http://musicapi.leanapp.cn/playlist/detail?id=";
    public final static String detailed_playlist_url="https://api.imjad.cn/cloudmusic/?type=playlist&id=";
    //获取歌曲的详细信息
    public final static String detailed_song_url="http://musicapi.leanapp.cn/song/detail?ids=";
    //获取推荐的新音乐
    public final static String recommend_songs_url="http://musicapi.leanapp.cn/personalized/newsong";
}
