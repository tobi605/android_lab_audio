package com.polibuda.diamentowygimbus.android_lab_audio;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListActivity extends AppCompatActivity {
    private List<VoiceNote> voiceNotes;
    private VoiceNoteAdapter adapter;
    private ListView listView;
    private Button mergeButton;
    private Menu menu;
    private boolean onSelections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        voiceNotes = new ArrayList<>();
        try {
            initVoiceNotes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView = findViewById(R.id.recordsList);
        adapter = new VoiceNoteAdapter(getApplicationContext(), R.layout.record_list_element, voiceNotes);
        listView.setAdapter(adapter);
        mergeButton = findViewById(R.id.mergeButton);
        mergeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mergeItems();
            }
        });
        onSelections = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.merge_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (onSelections) menu.getItem(0).setTitle("Wróć do odtwarzania");
        else menu.getItem(0).setTitle("Scal");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.merge_menu_merge) {
            if (onSelections) {
                changeToNormal();
            } else changeToSelections();
        }
        return true;
    }

    private void initVoiceNotes() throws IOException {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath();
        File file = new File(filePath, "Voice Notes");
        for (File f : file.listFiles()) {
            if (f.getName().substring(f.getName().length() - 4).equals("info")) {
                voiceNotes.add(loadNoteFromFile(f));
            }
        }
    }

    private VoiceNote loadNoteFromFile(File f) throws IOException {
        Scanner scanner = new Scanner(f);
        String audioPath = f.getPath().substring(0, f.getPath().length() - 4) + "wav";
        String toParse = scanner.nextLine();
        String[] info = toParse.split(";");
        String date = "", surname = "Brak", name = "Brak", title = "Brak", note = "BrakBrakBrak";
        if (info.length >= 1) date = info[0];
        if (info.length >= 2) surname = info[1];
        if (info.length >= 3) name = info[2];
        if (info.length >= 4) title = info[3];
        if (info.length >= 5) note = info[4];
        return new VoiceNote(date, surname, name, title, note, audioPath);
    }

    private void changeToSelections() {
        for (int i = 0; i < listView.getChildCount(); i++) {
            View v = listView.getChildAt(i);
            if (v != null) {
                v.findViewById(R.id.elementPlay).setVisibility(View.INVISIBLE);
                v.findViewById(R.id.elementDelete).setVisibility(View.INVISIBLE);
                v.findViewById(R.id.elementCheckbox).setVisibility(View.VISIBLE);
            }
        }
        mergeButton.setVisibility(View.VISIBLE);
        onSelections = true;
        onPrepareOptionsMenu(menu);
    }

    private void changeToNormal() {
        for (int i = 0; i < listView.getChildCount(); i++) {
            View v = listView.getChildAt(i);
            if (v != null) {
                v.findViewById(R.id.elementPlay).setVisibility(View.VISIBLE);
                v.findViewById(R.id.elementDelete).setVisibility(View.VISIBLE);
                v.findViewById(R.id.elementCheckbox).setVisibility(View.INVISIBLE);
            }
        }
        mergeButton.setVisibility(View.INVISIBLE);
        onSelections = false;
        onPrepareOptionsMenu(menu);
    }

    private void mergeItems() {
        ArrayList<VoiceNote> toMerge = new ArrayList<>();
        for (int i = 0; i < adapter.getCount(); i++) {
            VoiceNote voiceNote = adapter.getItem(i);
            if (voiceNote != null) {
                toMerge.add(voiceNote);
            }
        }
        VoiceNote first = toMerge.remove(0);
        VoiceNote created = null;
        if (toMerge.size() == 1) {
            VoiceNote second = toMerge.remove(0);
            try {
                created = first.concatWith(second);
            } catch (IOException e) {
                e.printStackTrace();
            }
            voiceNotes.remove(second);
            voiceNotes.remove(first);
            if (voiceNotes != null) voiceNotes.add(created);

        } else {
            try {
                created = first.concatWithList(toMerge);
            } catch (IOException e) {
                e.printStackTrace();
            }
            voiceNotes.removeAll(toMerge);
            voiceNotes.remove(first);
            voiceNotes.add(created);
        }
        adapter.notifyDataSetChanged();
        changeToNormal();
    }
}
