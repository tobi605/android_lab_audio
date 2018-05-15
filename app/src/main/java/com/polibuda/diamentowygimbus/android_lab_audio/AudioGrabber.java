package com.polibuda.diamentowygimbus.android_lab_audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.util.concurrent.LinkedBlockingQueue;

public class AudioGrabber implements Runnable {
    private LinkedBlockingQueue<short[]> queue;
    private AudioRecord audioRecord;
    private boolean recordStopper;

    public AudioGrabber(LinkedBlockingQueue<short[]> queue) {
        this.queue = queue;
        this.audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, 8192);
        this.recordStopper = true;
    }

    @Override
    public void run() {
        while (recordStopper){
            try {
                queue.put(getNextBlock());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private short[] getNextBlock(){
        short[] buffer = new short[AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)];
        int returnCode = audioRecord.read(buffer, 0, buffer.length);
        return returnCode>=0 ? buffer : null;
    }

    void startRecord(){
        this.recordStopper = true;
    }

    void stopRecord(){
        this.recordStopper = false;
    }
}
