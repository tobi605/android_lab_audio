package com.polibuda.diamentowygimbus.android_lab_audio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText surnameEdit, nameEdit, titleEdit, notesEdit;
    private Button startButton, stopButton, eraseButton, saveButton;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        //TODO init button listeners
    }
}
