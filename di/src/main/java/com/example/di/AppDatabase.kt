package com.example.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.map_data.datasource.local.CarsDataBase
import com.example.map_model.CarEntity

//-------------------------------------------
//I used this  annotation @Database to define the Room Database,
// and I specify entities inside it. This informs the database to create a table based on this specified entity."
//
//This line defines an abstract class, AppDatabase, inheriting RoomDatabase and implementing
// the functionality specified in the CarsDataBase interface for interacting with a Room database."
//-------------------------------------------------
@Database(entities = [CarEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() , CarsDataBase
