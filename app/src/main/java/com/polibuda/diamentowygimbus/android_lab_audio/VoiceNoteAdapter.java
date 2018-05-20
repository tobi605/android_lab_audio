package com.polibuda.diamentowygimbus.android_lab_audio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

class VoiceNoteAdapter<VoiceNote> extends ArrayAdapter<VoiceNote> {
    public VoiceNoteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
