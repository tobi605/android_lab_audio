package com.polibuda.diamentowygimbus.android_lab_audio;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

public class Recorder {
    private String surname, name, title, note;
    private Date date;
    private AudioGrabber grabber;
    private AudioProcess process;
    private LinkedBlockingQueue<byte[]> bufferQueue;

    public Recorder(String surname, String name, String title, String note, Date date) {
        this.surname = surname;
        this.name = name;
        this.title = title;
        this.note = note;
        this.date = date;
        this.bufferQueue = new LinkedBlockingQueue<>();
        //TODO filepath with usage of date
        String filePath = "file path here";
        this.grabber = new AudioGrabber(bufferQueue);
        this.process = new AudioProcess(bufferQueue, filePath);
    }

    void startRecording(){
        this.grabber.startRecord();
        this.process.startProcess();
    }

    void stopRecording(){
        this.grabber.stopRecord();
        this.process.stopProcess();
    }
}
