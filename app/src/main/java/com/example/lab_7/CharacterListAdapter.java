package com.example.lab_7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CharacterListAdapter extends BaseAdapter {

    private ArrayList<String> characterList;
    private LayoutInflater inflater;

    public CharacterListAdapter(Context context, ArrayList<String> characterList) {
        this.characterList = characterList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return characterList.size();
    }

    @Override
    public Object getItem(int position) {
        return characterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_character, parent, false);
            holder = new ViewHolder();
            holder.characterNameTextView = convertView.findViewById(R.id.characterNameTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String characterName = characterList.get(position);
        holder.characterNameTextView.setText(characterName);

        return convertView;
    }

    private static class ViewHolder {
        TextView characterNameTextView;
    }
}
