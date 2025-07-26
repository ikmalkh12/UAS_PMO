package com.example.uts.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    long insert(Task task); // return ID hasil insert


    @Query("SELECT * FROM Task WHERE userId = :userId")
    List<Task> getTasksByUser(int userId);

    @Query("SELECT * FROM Task WHERE id = :id LIMIT 1")
    Task getTaskById(int id);


    // (opsional)
    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
