package com.polibuda.diamentowygimbus.android_lab_audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VoiceNote {
    private String date, surname, name, title, note, audioFilePath;

    public VoiceNote(String date, String surname, String name, String title, String note, String audioFilePath) {
        this.date = date;
        this.surname = surname;
        this.name = name;
        this.title = title;
        this.note = note;
        this.audioFilePath = audioFilePath;
    }

    void playNote(Context context){
        MediaPlayer player;
        player = MediaPlayer.create(context, Uri.parse(this.audioFilePath));
        player.start();
    }

    void remove(){
        File audioFile = new File(this.audioFilePath);
        String infoFilePath = this.audioFilePath.substring(0,this.audioFilePath.length()-3)+"info";
        File infoFile = new File(infoFilePath);
        audioFile.delete();
        infoFile.delete();
    }

    void concatWith(VoiceNote note){

    }

    void concatWithList(List<VoiceNote> notes){

    }

    void mergeAudio(String firstPath, String secondPath) throws IOException {
        FileInputStream first = new FileInputStream(firstPath);
        FileInputStream second = new FileInputStream(secondPath);
        String date = new SimpleDateFormat("HH-mm_dd-MM-yyyy").format(new Date());
        FileOutputStream outputStream = new FileOutputStream();
    }

    void mergeInfo(String firstPath, String secondPath){

    }
}
