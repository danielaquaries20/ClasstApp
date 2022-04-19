package com.example.classtapp.data.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
abstract class UserDao {
    @Query("DELETE FROM User")
    abstract suspend fun deleteAll()

    @Query("SELECT * FROM User WHERE idRoom = 1")
    abstract fun getUser(): LiveData<User?>

    @Query("SELECT EXISTS (SELECT 1 FROM User WHERE idRoom = 1)")
    abstract suspend fun isLogin(): Boolean

    @Query("SELECT id FROM User WHERE idRoom = 1")
    abstract suspend fun getUserId(): Int


}