package com.example.truthordare.database;

import android.content.Context;

import com.example.truthordare.database.model.DareOrder;
import com.example.truthordare.database.model.Player;
import com.example.truthordare.database.model.TruthQuestions;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.Single;

public class DatabaseRepo {

    private GameDatabase database;
    private Context context;

    public DatabaseRepo( Context context) {
        this.context = context;
        database=GameDatabase.getmInstance(context);
    }



    public  void insertNewPlayer(final Player player){

        provideExcuter().execute(new Runnable() {
            @Override
            public void run() {
                database.playersNamesDao().insertPlayer(player);
            }
        });;
    }

    public void deletePlayer(final int id){
        provideExcuter().execute(new Runnable() {
            @Override
            public void run() {
                database.playersNamesDao().deletePlayer(id);
            }
        });
    }

    public Observable<List<Player>> getAllPlayers(){
        return database.playersNamesDao().getPlayers();
    }

    public void updatepoints(Player player){
        provideExcuter().execute(new Runnable() {
            @Override
            public void run() {
                database.playersNamesDao().updatePlayerPoints(player);
            }
        });
    }
    public Single<TruthQuestions> getQuestion(){
        return database.questionsDao().getQuestion();
    }
   public Single<DareOrder> getOrder(){
        return database.questionsDao().getOrder();
    }

    private ExecutorService provideExcuter(){
        return Executors.newSingleThreadExecutor();
    }
}
