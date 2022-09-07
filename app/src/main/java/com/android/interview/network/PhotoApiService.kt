package com.android.interview.network

import com.android.interview.model.PhotoDTO
import retrofit2.Response
import retrofit2.http.GET

interface PhotoApiService {

    @GET("photos/random/")
    suspend fun getPhotos(): Response<PhotoDTO>
}

val photosApi: PhotoApiService by lazy {
    NetworkWrapper.retrofit.create(PhotoApiService::class.java)
}