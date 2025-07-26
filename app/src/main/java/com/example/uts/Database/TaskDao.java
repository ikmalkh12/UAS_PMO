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
    void insert(Task task);

    @Query("SELECT * FROM Task WHERE userId = :userId")
    List<Task> getTasksByUser(int userId);

    @Query("SELECT * FROM Task WHERE id = :id LIMIT 1")
    Task getTaskById(int id);


    // (opsional) tambahan lain jika kamu pakai update/delete:
    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
