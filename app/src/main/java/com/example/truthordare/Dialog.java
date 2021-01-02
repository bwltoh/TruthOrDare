package com.example.truthordare;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class Dialog extends DialogFragment {

     private static String PARAM1="name";
     String playerName;


    public static Dialog newInstance(String name) {

        Bundle args = new Bundle();
        args.putString(PARAM1,name);
        Dialog fragment = new Dialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog()!=null&&getDialog().getWindow()!=null){
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return inflater.inflate(R.layout.dialog_layout,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playerName=getArguments().getString(PARAM1);
        TextView textView=view.findViewById(R.id.text);
        Button truth=view.findViewById(R.id.truth);
        Button dare =view.findViewById(R.id.dare);

        textView.setText(playerName+".. Truth Or Dare?");
        truth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(1,playerName);
                dismiss();
            }
        });

        dare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(0,playerName);
                dismiss();
            }
        });

    }

    private void start(int mode,String name){
        Intent intent=new Intent(getActivity(),QuestionActivity.class);
        intent.putExtra("mode",mode);
        intent.putExtra("name",name);
        startActivity(intent);

    }
    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCancelable(false);
    }
}
