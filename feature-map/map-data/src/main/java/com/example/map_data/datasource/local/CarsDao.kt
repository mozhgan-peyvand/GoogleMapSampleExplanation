package com.example.map_data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.map_model.CarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarsDao {

    @Query("SELECT * FROM CarEntity ")
    fun getCarsList(): Flow<List<CarEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarsList(cars: List<CarEntity>)

    @Query("DELETE FROM CarEntity")
    suspend fun deleteCarsList()

}
