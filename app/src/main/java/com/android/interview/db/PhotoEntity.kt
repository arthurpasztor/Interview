package com.android.interview.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.interview.model.PhotoDTO

@Entity(tableName = "photo_table")
class PhotoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "photoJson") val photoJson: String
)

fun PhotoEntity.toPhotoDTO() = PhotoDTO.fromJSON(photoJson)

fun PhotoDTO.toPhotoEntity() = PhotoEntity(
    photoJson = this.toString()
)