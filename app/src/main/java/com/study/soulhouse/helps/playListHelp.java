package com.study.soulhouse.helps;

import com.study.soulhouse.Gson.Song;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class playListHelp {
    private  Deque<Song> SongQueue;
    private static playListHelp pLH;
    public static playListHelp getInstance(){
        if(pLH==null){
            synchronized (pLH){
                if(pLH==null){
                    pLH=new playListHelp();
                }
            }
        }
        return pLH;
    }
    private playListHelp(){
        SongQueue=new LinkedList<>();
    }
}
