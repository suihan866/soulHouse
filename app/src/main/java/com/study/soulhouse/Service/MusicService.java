package com.study.soulhouse.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {
    public MusicService() {
    }
    class MusicBinder extends Binder{
        /**
         * 播放音乐
         */
        public void startMusic(){

        }
        /**
         * 暂停音乐
         */
        public void pauseMusic(){

        }

    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}