package com.android.interview.business

import android.util.Log
import androidx.lifecycle.*
import com.android.interview.db.toPhotoDTO
import com.android.interview.db.toPhotoEntity
import com.android.interview.model.PhotoDTO
import com.android.interview.repository.PhotoRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Response

class PhotoViewModel(private val repository: PhotoRepository) : ViewModel() {

    fun getRandomPhoto(): LiveData<Response<PhotoDTO>> = liveData {
        repository.getRandomPhoto()
            .catch { exception ->Log.e(TAG, "${exception.message}") }
            .collect { emit(it) }
    }

    fun fetchPhotos(): LiveData<List<PhotoDTO>> = liveData {
        repository.getAllPhotos()
            .catch { exception ->Log.e(TAG, "${exception.message}") }
            .collect { dbList ->
                val photoDtoList = dbList.map { it.toPhotoDTO() }
                emit(photoDtoList)
            }
    }

    fun insert(photo: PhotoDTO) = viewModelScope.launch {
        repository.insert(photo.toPhotoEntity())
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    companion object {
        private val TAG: String = PhotoViewModel::class.java.simpleName
    }
}

class PhotoViewModelFactory(private val repository: PhotoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
