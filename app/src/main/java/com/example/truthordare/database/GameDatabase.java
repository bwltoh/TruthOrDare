package com.example.truthordare.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.truthordare.database.dao.PlayersNamesDao;
import com.example.truthordare.database.dao.QuestionsDao;
import com.example.truthordare.database.model.DareOrder;
import com.example.truthordare.database.model.Player;
import com.example.truthordare.database.model.TruthQuestions;


@Database(entities = {Player.class, TruthQuestions.class, DareOrder.class}, version = 1,exportSchema = false)
public abstract class GameDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="game.db";
    private static final Object lock=new Object();
    private static GameDatabase  mInstance;

    public static GameDatabase getmInstance(Context context){
        if (mInstance==null)
            synchronized (lock){
            mInstance= Room.databaseBuilder(context.getApplicationContext(),
                    GameDatabase.class,
                    DATABASE_NAME)
                    .createFromAsset("game.db")
                    .build();
            }
        return mInstance;
    }

    public abstract PlayersNamesDao playersNamesDao();

    public abstract QuestionsDao questionsDao();
}
