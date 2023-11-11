package com.example.map_data.di

import com.example.map_data.datasource.local.CarsDao
import com.example.map_data.datasource.local.CarsDataBase
import com.example.map_data.datasource.local.CarsLocalDataSource
import com.example.map_data.datasource.local.CarsLocalDataSourceImp
import com.example.map_data.datasource.remote.CarsRemoteDataSource
import com.example.map_data.datasource.remote.CarsRemoteDataSourceImp
import com.example.map_data.datasource.remote.CarsService
import com.example.map_data.repositories.CarsRepositoryImpl
import com.example.map_domain.repository.CarsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CarsModule {

    @Binds
    internal abstract fun bindCarsRepository(impl: CarsRepositoryImpl): CarsRepository

    @Binds
    internal abstract fun provideCarsRemote(carsRemoteDataSourceImp: CarsRemoteDataSourceImp) : CarsRemoteDataSource

    @Binds
    internal abstract fun provideCarsLocal(carsLocalDataSourceImp: CarsLocalDataSourceImp) : CarsLocalDataSource

    companion object {
        @Provides
        internal fun provideCarsDao(carsDataBase: CarsDataBase): CarsDao = carsDataBase.CarsDao()
        @Provides
        internal fun provideCarsService(retrofit: Retrofit): CarsService = retrofit.create(CarsService::class.java)
    }
}