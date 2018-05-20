package com.polibuda.diamentowygimbus.android_lab_audio;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VoiceNote {
    private String date, surname, name, title, note, audioFilePath, infoFilePath;
    private boolean selected;

    VoiceNote(String date, String surname, String name, String title, String note, String audioFilePath) {
        this.date = date;
        this.surname = surname;
        this.name = name;
        this.title = title;
        this.note = note;
        this.audioFilePath = audioFilePath;
        this.infoFilePath = this.audioFilePath.substring(0, this.audioFilePath.length() - 3) + "info";
        this.selected = false;
    }

    void playNote(Context context) throws IOException {
        FileInputStream reader = new FileInputStream(this.audioFilePath);
        reader.skip(44); //skip header
        int bufferSize = AudioRecord.getMinBufferSize
                (44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC,
                44100,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize,
                AudioTrack.MODE_STREAM);
        byte[] buffer = new byte[bufferSize];
        track.play();
        while (reader.read(buffer) != -1) {
            track.write(buffer, 0, bufferSize);
        }
        track.stop();
        track.release();
    }

    void remove() {
        File audioFile = new File(this.audioFilePath);
        File infoFile = new File(this.infoFilePath);
        audioFile.delete();
        infoFile.delete();
    }

    VoiceNote concatWith(VoiceNote note) throws IOException {
        String date = new SimpleDateFormat("HH:mm:ss_dd-MM-yyyy").format(new Date());
        String folderPath = (new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_MUSIC).getPath(), "Voice Notes")).getPath();
        String outFilePath = folderPath + "/Voice Note " + date;
        mergeAudio(this.audioFilePath, note.audioFilePath, outFilePath + ".wav");
        mergeInfo(this.infoFilePath, note.infoFilePath, outFilePath + ".info");
        return new VoiceNote(date, this.surname, this.name, this.title, this.note, outFilePath + ".wav");
    }

    VoiceNote concatWithList(List<VoiceNote> notes) throws IOException {
        for (int i = notes.size() - 1; i >= 1; i--) {
            notes.get(i - 1).concatWith(notes.get(i));
        }
        return this.concatWith(notes.get(0));
    }

    private void mergeAudio(String firstPath, String secondPath, String outPath) throws IOException {
        FileInputStream first = new FileInputStream(firstPath);
        FileInputStream second = new FileInputStream(secondPath);
        FileOutputStream outputStream = new FileOutputStream(outPath);
        long audioLen = first.getChannel().size() + second.getChannel().size();
        long totalLen = audioLen + 36;
        byte[] header = AudioProcess.wavFileHeader(audioLen, totalLen, 44100, 1, 88200);
        outputStream.write(header);
        byte[] buffer = new byte[AudioRecord.getMinBufferSize
                (44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)];
        while (first.read(buffer) != -1) outputStream.write(buffer);
        while (second.read(buffer) != -1) outputStream.write(buffer);
        (new File(firstPath)).delete();
        (new File(secondPath)).delete();
    }

    private void mergeInfo(String firstPath, String secondPath, String outPath) throws IOException {
        BufferedReader inputStream = new BufferedReader(new FileReader(firstPath));
        BufferedWriter outputStream = new BufferedWriter(new FileWriter(outPath));
        int read = inputStream.read();
        while (read != -1) {
            outputStream.write(read);
            read = inputStream.read();
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
        (new File(firstPath)).delete();
        (new File(secondPath)).delete();
    }

    public String getDate() {
        return date;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    void select() {
        this.selected = true;
    }

    void unselect() {
        this.selected = false;
    }

    boolean getSelected() {
        return this.selected;
    }
}
