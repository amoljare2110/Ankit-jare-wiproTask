package com.ankit.jare.wiproDataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WiproDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveData(infosysEntity: WiproEntity)

    @Query("SELECT * FROM wipro_app")
    suspend fun getRecords(): List<WiproEntity>
}