package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.map_data.datasource.local.CarsDataBase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//I want to say that I used objects for two reasons, one is that functions become static with objects,
// to define functions statically in Kotlin, we put them inside the object,
// and calling static functions is much faster than calling a function on an instance of a class, and secondly,
// we are stateless or passive. We don't have properties in the class, so we don't need to create an instance of the class

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DatabaseModule {

    @Binds
    @Singleton
    internal abstract fun bindCarsDataBase(appDatabase: AppDatabase): CarsDataBase

    companion object {
        @Provides
        @Singleton
        internal fun provideRoom(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "map"
            ).build()
        }
    }
}
