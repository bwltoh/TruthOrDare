package com.example.truthordare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.truthordare.database.DatabaseRepo;
import com.example.truthordare.database.model.Player;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayersActivity extends AppCompatActivity implements View.OnClickListener, NamesAdapter.DeletePlayerListener {


    DatabaseRepo databaseRepo;
    Disposable   disposable;
    EditText     nameInput;
    ImageButton  addName;
    Button Play;
    ListView     namesList;
    NamesAdapter adapter;
    List<Player> names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        initViews();

        names=new ArrayList<>();
        namesList.setAdapter(adapter);


        databaseRepo=new DatabaseRepo(this);


        databaseRepo.getAllPlayers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Player>>() {
                    @Override
                    public void onSubscribe(Disposable s) {
                       disposable=s;
                    }

                    @Override
                    public void onNext(List<Player> players) {
                        names=players;
                        adapter.setNames(players);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(PlayersActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });


    }

    private void initViews() {
        nameInput=findViewById(R.id.name_input);
        addName=findViewById(R.id.add_name);
        namesList=findViewById(R.id.names_list);
        Play=findViewById(R.id.play);
        adapter=new NamesAdapter(this,this);
        Play.setOnClickListener(this);
        addName.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        if (!disposable.isDisposed())
        disposable.dispose();

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.add_name){
            String newName =nameInput.getText().toString();
            if (!newName.equals("")){
                databaseRepo.insertNewPlayer(new Player(newName));
                nameInput.setText("");
            }
        }else if (v.getId()==R.id.play){
            startPlaying(names);
        }
    }

    void startPlaying(List<Player> listPlayer){

        if (listPlayer==null)
            return;

        List<String> list=new ArrayList<>();
        for (int i = 0; i <listPlayer.size() ; i++) {
            list.add(listPlayer.get(i).getName());
        }

        if (list.size()>=2&&list.size()<=8){
        Intent intent=new Intent(PlayersActivity.this,GameActivity.class);
        intent.putStringArrayListExtra("players",(ArrayList<String>) list);
        startActivity(intent);
        }else{
            Toast.makeText(this, "Must Add At Least Two Players And Less Than Eight", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void delete(int id) {
        databaseRepo.deletePlayer(id);
    }
}
