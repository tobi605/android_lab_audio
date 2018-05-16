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
    private int shortsRead;

    AudioGrabber(LinkedBlockingQueue<byte[]> queue) {
        this.queue = queue;
        this.bufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        this.recordStopper = true;
        this.shortsRead = 0;
    }

    @Override
    public void run() {
        startRecord();
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
        if(returnCode>=0){
            shortsRead+=returnCode;
            return short2byte(buffer);
        }
        else return null;
    }

    private void startRecord(){
        this.audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
        this.recordStopper = true;
        this.audioRecord.startRecording();
    }

    void stopRecord(){
        this.recordStopper = false;
        this.audioRecord.stop();
        this.audioRecord.release();
        this.audioRecord = null;
    }

    private byte[] short2byte(short[] shortData){
        int arraySize = shortData.length;
        byte[] bytes = new byte[arraySize*2];
        for (int i = 0; i < arraySize; i++){
            bytes[i*2] = (byte) (shortData[i] & 0xFF);
            bytes[(i*2)+1] = (byte) ((shortData[i] >> 8) & 0xff);
        }
        return bytes;
    }
}
