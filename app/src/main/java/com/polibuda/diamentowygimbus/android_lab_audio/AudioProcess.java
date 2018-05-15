package com.polibuda.diamentowygimbus.android_lab_audio;

import java.util.concurrent.LinkedBlockingQueue;

public class AudioProcess implements Runnable {
    private LinkedBlockingQueue<byte[]> queue;

    public AudioProcess(LinkedBlockingQueue<byte[]> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

    }

}
