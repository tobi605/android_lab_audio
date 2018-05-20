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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFileSystem();
        this.recorder = new Recorder(file.getAbsolutePath());
        initElements();
        initListeners();
    }

    private void initFileSystem() {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath();
        this.file = new File(filePath, "Voice Notes");
        if(!file.exists()) file.mkdir();
    }

    private void initElements() {
        surnameEdit = findViewById(R.id.editSurname);
        nameEdit = findViewById(R.id.editName);
        titleEdit = findViewById(R.id.editTitle);
        notesEdit = findViewById(R.id.editNotes);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        stopButton.setEnabled(false);
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
                statusText.setText("Nagrywam...");
                String date = new SimpleDateFormat("HH-mm_dd-MM-yyyy").format(new Date());
                recorder.setDate(date);
                recorder.startRecording(date);
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.stopRecording();
                statusText.setText("Zatrzymano nagrywanie");
                eraseButton.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                stopButton.setEnabled(false);
                startButton.setEnabled(true);
            }
        });
        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.clear();
                statusText.setText("Skasowano, gotowy na nowe nagranie");
                eraseButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.INVISIBLE);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.saveFile();
                statusText.setText("Zapisano do pliku");
                eraseButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.INVISIBLE);
            }
        });
    }
}
