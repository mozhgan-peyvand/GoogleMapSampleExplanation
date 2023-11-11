package com.example.base.api

import retrofit2.Response
import com.example.base.models.Exceptions
import com.example.base.models.Resource
import javax.inject.Inject

class NetworkHandler @Inject constructor(
    private val connectionManager: ConnectionManager
) {

    suspend fun <T> call(networkCall: suspend () -> Response<T>): Resource<T> =
        try {
            if (!connectionManager.isConnected())
                Resource.Error(Exceptions.NoInternetConnectionException())
            else {
                val apiResponse = networkCall()
                if (apiResponse.isSuccessful) {
                    val body = apiResponse.body()
                    if (body != null) {
                        Resource.Success(body)
                    } else {
                        Resource.Error(Exceptions.GeneralRemoteException())
                    }

                } else {
                    Resource.Error(Exceptions.GeneralRemoteException())
                }
            }

        } catch (t: Throwable) {
            Resource.Error(Exceptions.GeneralRemoteException())
        }
}