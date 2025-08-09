package com.example.tarea_03.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarea_03.R;
import com.example.tarea_03.model.Lawyer;

import java.util.List;

public class LawyerAdapter extends ArrayAdapter<Lawyer> {

    public LawyerAdapter(Context context, List<Lawyer> lawyers) {
        super(context, 0, lawyers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Lawyer lawyer = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lawyer, parent, false);
        }

        ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);
        TextView txtName = convertView.findViewById(R.id.txtName);

        txtName.setText(lawyer.getName());

        imgAvatar.setImageResource(R.drawable.avatar_circle);

        return convertView;
    }
}

