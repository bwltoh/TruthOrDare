package com.example.truthordare.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.truthordare.database.model.Player;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface PlayersNamesDao {

    @Insert
    void insertPlayer(Player player);

    @Query("SELECT * FROM Player")
    Observable<List<Player>> getPlayers();



    @Update
    void updatePlayerPoints(Player player);

    @Query("DELETE FROM PLAYER WHERE id LIKE :id")
    void deletePlayer(int id);

}
