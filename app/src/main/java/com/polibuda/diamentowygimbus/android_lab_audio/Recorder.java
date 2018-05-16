package com.polibuda.diamentowygimbus.android_lab_audio;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class Recorder {
    private String surname, name, title, note;
    private String folderPath;
    private String date;
    private AudioGrabber grabber;
    private AudioProcess process;
    private LinkedBlockingQueue<byte[]> bufferQueue;

    Recorder(String folderPath) {
        this.bufferQueue = new LinkedBlockingQueue<>();
        this.folderPath = folderPath;
        this.grabber = new AudioGrabber(bufferQueue);
        this.process = new AudioProcess(bufferQueue);
    }

    void startRecording(String filePath){
        this.process.setFilePath(this.folderPath+filePath);
        (new Thread(this.grabber)).start();
        (new Thread(this.process)).start();
    }

    void stopRecording(){
        this.grabber.stopRecord();
        this.process.stopProcess();
    }

    void saveFile(){
        try {
            this.process.saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void clear(){
        this.process.clear();
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
