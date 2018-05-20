package com.polibuda.diamentowygimbus.android_lab_audio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

class VoiceNoteAdapter extends ArrayAdapter<VoiceNote> {

    int container;

    VoiceNoteAdapter(@NonNull Context context, int resource, @NonNull List<VoiceNote> objects) {
        super(context, resource, objects);
        container = resource;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(container, parent, false);
        final VoiceNote current = getItem(position);
        TextView title = convertView.findViewById(R.id.elementTitle);
        TextView surname = convertView.findViewById(R.id.elementSurname);
        TextView name = convertView.findViewById(R.id.elementName);
        TextView date = convertView.findViewById(R.id.elementDate);
        TextView note = convertView.findViewById(R.id.elementNote);
        title.setText(current.getTitle());
        surname.setText(current.getSurname());
        name.setText(current.getName());
        date.setText(current.getDate());
        note.setText(current.getNote());
        convertView.findViewById(R.id.elementDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current.remove();
                remove(current);
                notifyDataSetChanged();
            }
        });
        convertView.findViewById(R.id.elementPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    current.playNote(getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ((CheckBox) convertView.findViewById(R.id.elementCheckbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) getItem(position).unselect();
                else getItem(position).select();
            }
        });
        return convertView;
    }
}
