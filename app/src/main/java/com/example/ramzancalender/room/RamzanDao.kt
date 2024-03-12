package com.example.ramzancalender.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ramzancalender.models.FilteredModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RamzanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoRamzanDb(data:List<FilteredModel>):List<Long>



    @Query("SELECT * FROM filteredmodel")
    fun getAllRamzanData(): Flow<List<FilteredModel>>

    @Query("DELETE FROM filteredmodel ")
    suspend fun deleteAllRestaurants()



}