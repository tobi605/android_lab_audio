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
        this.bufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        this.recordStopper = true;
    }

    @Override
    public void run() {
        startRecord();
        while (recordStopper) {
            try {
                byte[] nextBlock = getNextBlock();
                if (nextBlock != null) queue.put(nextBlock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getNextBlock() {
        byte[] buffer = new byte[bufferSize];
        int returnCode = audioRecord.read(buffer, 0, bufferSize);
        return (returnCode >= 0) ? buffer : null;
    }

    private void startRecord() {
        this.audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
        this.recordStopper = true;
        this.audioRecord.startRecording();
    }

    void stopRecord() {
        this.recordStopper = false;
        this.audioRecord.stop();
        this.audioRecord.release();
        this.audioRecord = null;
    }
}
