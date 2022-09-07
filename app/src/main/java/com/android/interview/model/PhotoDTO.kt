package com.android.interview.model

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PhotoDTO(
    @SerializedName("urls") var urls: UrlsDTO,
    @SerializedName("created_at") var created: Date,
    @SerializedName("likes") var likes: Int,
    @SerializedName("views") var views: Int,
    @SerializedName("downloads") var downloads: Int,
    @SerializedName("exif") var exif: ExifDTO
) : Parcelable {

    override fun toString(): String = Gson().toJson(this)

    companion object {
        fun fromJSON(json: String): PhotoDTO = Gson().fromJson(json, PhotoDTO::class.java)
    }
}

@Parcelize
data class UrlsDTO(
    @SerializedName("regular") var regular: String,
) : Parcelable

@Parcelize
data class ExifDTO(
    @SerializedName("name") var name: String? = "N/A",
): Parcelable
