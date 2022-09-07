package com.android.interview.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo_table")
    fun getPhotos(): List<PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: PhotoEntity)

    @Query("DELETE FROM photo_table")
    suspend fun deleteAll()
}