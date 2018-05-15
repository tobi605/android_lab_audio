package com.polibuda.diamentowygimbus.android_lab_audio;

import java.util.concurrent.LinkedBlockingQueue;

public class Recorder {
    private String surname, name, title, note;
    private AudioGrabber grabber;
    private AudioProcess process;
    private LinkedBlockingQueue<byte[]> bufferQueue;

    public Recorder(String surname, String name, String title, String note, AudioGrabber grabber,
                    AudioProcess process, LinkedBlockingQueue<byte[]> bufferQueue) {
        this.surname = surname;
        this.name = name;
        this.title = title;
        this.note = note;
        this.grabber = grabber;
        this.process = process;
        this.bufferQueue = bufferQueue;
    }


}
