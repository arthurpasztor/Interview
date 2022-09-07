package com.android.interview.repository

import com.android.interview.db.PhotoDao
import com.android.interview.db.PhotoEntity
import com.android.interview.model.PhotoDTO
import com.android.interview.network.photosApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class PhotoRepository(private val photoDao: PhotoDao) {

    fun getRandomPhoto(): Flow<Response<PhotoDTO>> = flow {
        emit(photosApi.getPhotos())
    }.flowOn(Dispatchers.IO)

    fun getAllPhotos(): Flow<List<PhotoEntity>> = flow {
        emit(photoDao.getPhotos())
    }.flowOn(Dispatchers.IO)

    suspend fun insert(word: PhotoEntity) {
        photoDao.insert(word)
    }

    suspend fun deleteAll() {
        photoDao.deleteAll()
    }
}