package com.polibuda.diamentowygimbus.android_lab_audio;


import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private EditText surnameEdit, nameEdit, titleEdit, notesEdit;
    private Button startButton, stopButton, eraseButton, saveButton;
    private TextView statusText;
    private Recorder recorder;
    private File file;
    private boolean wasRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "voice_notes/");
        this.recorder = new Recorder(file.getAbsolutePath());
        this.wasRecording = false;
        initElements();
        initListeners();
    }

    private void initElements() {
        surnameEdit = findViewById(R.id.editSurname);
        nameEdit = findViewById(R.id.editName);
        titleEdit = findViewById(R.id.editTitle);
        notesEdit = findViewById(R.id.editNotes);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        eraseButton = findViewById(R.id.eraseButton);
        saveButton = findViewById(R.id.saveButton);

        statusText = findViewById(R.id.recordStatus);
    }

    private void initListeners() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.setSurname(String.valueOf(surnameEdit.getText()));
                recorder.setName(String.valueOf(nameEdit.getText()));
                recorder.setTitle(String.valueOf(titleEdit.getText()));
                recorder.setNote(String.valueOf(notesEdit.getText()));
                statusText.setText("Recording...");
                String date = new SimpleDateFormat("HH-mm_dd-MM-yyyy").format(new Date());
                recorder.startRecording(date);
                wasRecording = true;
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.stopRecording();
                statusText.setText("Stopped recording");
                if(wasRecording){
                    eraseButton.setVisibility(View.VISIBLE);
                    saveButton.setVisibility(View.VISIBLE);
                }
            }
        });
        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.clear();
                statusText.setText("Erased, ready to record");
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.saveFile();
                statusText.setText("Saved to file");
            }
        });
    }
}
