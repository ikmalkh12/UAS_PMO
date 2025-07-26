package com.example.uts.Adapter;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.uts.Database.User;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM User WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);

    @Query("SELECT * FROM User WHERE id = :id LIMIT 1")
    User getUserById(int id);  // ⬅️ digunakan di DashboardFragment
}
