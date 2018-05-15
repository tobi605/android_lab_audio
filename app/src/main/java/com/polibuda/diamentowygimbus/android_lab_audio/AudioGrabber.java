package com.polibuda.diamentowygimbus.android_lab_audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.util.concurrent.LinkedBlockingQueue;

public class AudioGrabber implements Runnable {
    private LinkedBlockingQueue<byte[]> queue;
    private AudioRecord audioRecord;
    private boolean recordStopper;
    private int bufferSize;

    AudioGrabber(LinkedBlockingQueue<byte[]> queue) {
        this.queue = queue;
        this.audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, 8192);
        this.bufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
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

    private byte[] getNextBlock(){
        short[] buffer = new short[bufferSize];
        int returnCode = audioRecord.read(buffer, 0, bufferSize);
        return returnCode>=0 ? short2byte(buffer) : null;
    }

    void startRecord(){
        this.recordStopper = true;
        this.audioRecord.startRecording();
        this.run();
    }

    void stopRecord(){
        this.recordStopper = false;
        this.audioRecord.stop();
        this.audioRecord.release();
    }

    private byte[] short2byte(short[] shortData){
        int arraySize = shortData.length;
        byte[] bytes = new byte[arraySize*2];
        for (int i = 0; i < arraySize; i++){
            bytes[i*2] = (byte) (shortData[i] & 0x00FF);
            bytes[(i*2)+1] = (byte) (shortData[i] >> 8);
        }
        return bytes;
    }
}
