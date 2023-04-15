package com.example.jobready.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.jobready.model.Industry;

import java.util.List;

public class IndustryAdapter extends ArrayAdapter<Industry> {

    private Context context;
    private int resource;
    private List<Industry> industries;

    public IndustryAdapter(@NonNull Context context, int resource, @NonNull List<Industry> industries) {
        super(context, resource, industries);
        this.context = context;
        this.resource = resource;
        this.industries = industries;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Industry industry = industries.get(position);

        TextView industryTextView = convertView.findViewById(android.R.id.text1);
        industryTextView.setText(industry.getDescription());

        return convertView;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        Industry industry = industries.get(position);

        TextView industryTextView = convertView.findViewById(android.R.id.text1);
        industryTextView.setText(industry.getDescription());

        return convertView;
    }
}
