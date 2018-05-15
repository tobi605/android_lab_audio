package com.polibuda.diamentowygimbus.android_lab_audio;

import java.util.concurrent.LinkedBlockingQueue;

public class AudioProcess implements Runnable {
    private LinkedBlockingQueue<byte[]> queue;
    private String filePath;
    private boolean processStopper;

    AudioProcess(LinkedBlockingQueue<byte[]> queue, String filePath) {
        this.queue = queue;
        this.processStopper = true;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        while (processStopper){
            //TODO write to file
        }
    }

    void startProcess(){
        this.processStopper = true;
        //TODO init file to write to
    }

    void stopProcess(){
        this.processStopper = false;
        //TODO close file
    }

}
