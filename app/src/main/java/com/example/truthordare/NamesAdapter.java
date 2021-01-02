package com.example.truthordare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.truthordare.database.model.Player;

import java.util.List;

public class NamesAdapter extends BaseAdapter {

    private Context      context;
    private List<Player> names;
    private DeletePlayerListener deletePlayerListener;
    public NamesAdapter(Context context,DeletePlayerListener deletePlayerListener) {
        this.context = context;
        this.deletePlayerListener=deletePlayerListener;
    }

    @Override
    public int getCount() {
        if (names==null)
        return 0;
        else
            return names.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView==null)
          view=LayoutInflater.from(context).inflate(R.layout.name_row,parent,false);
        else
            view=convertView;

        TextView name=view.findViewById(R.id.name);
        ImageButton delete=view.findViewById(R.id.delete);
        name.setText(names.get(position).getName());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deletePlayerListener.delete(names.get(position).getId());
            }
        });
        return view;
    }


    public void setNames(List<Player> names){
        this.names=names;
        notifyDataSetChanged();
    }


    public interface DeletePlayerListener{

        void delete(int id);
    }
}
