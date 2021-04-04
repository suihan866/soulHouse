package com.study.soulhouse.Thread;

public class SeekBarThread extends Thread{
    public static boolean flag=false;
    private MyRunnable myRunnable;
    public SeekBarThread(MyRunnable myRunnable){
        this.myRunnable=myRunnable;
    }
    @Override
    public void run() {
       myRunnable.run();
    }
    public interface MyRunnable{
        void run();
    }
}
