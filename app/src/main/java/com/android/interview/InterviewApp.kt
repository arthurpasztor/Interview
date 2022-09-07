package com.android.interview

import android.app.Application
import com.android.interview.BuildConfig.ACCESS_KEY
import com.android.interview.BuildConfig.SECRET_KEY
import com.android.interview.db.PhotoRoomDatabase
import com.android.interview.repository.PhotoRepository
import com.unsplash.pickerandroid.photopicker.UnsplashPhotoPicker

class InterviewApp : Application() {

    private val database by lazy { PhotoRoomDatabase.getDatabase(this) }
    val repository by lazy { PhotoRepository(database.wordDao()) }

    override fun onCreate() {
        super.onCreate()
        UnsplashPhotoPicker.init(this, ACCESS_KEY, SECRET_KEY).setLoggingEnabled(true)
    }
}