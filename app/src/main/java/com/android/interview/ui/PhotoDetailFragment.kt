package com.android.interview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.android.interview.R
import com.android.interview.databinding.FragmentPhotoDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class PhotoDetailFragment : Fragment() {

    private val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        arguments?.let { arguments ->
            val binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)

            binding.apply {
                val photo = PhotoDetailFragmentArgs.fromBundle(arguments).photo

                photoCameraName.text = getString(R.string.info_camera, photo.exif.name)
                photoCreated.text = getString(R.string.info_created_at, dateFormatter.format(photo.created))
                photoLikes.text = getString(R.string.info_likes, photo.likes)
                photoViews.text = getString(R.string.info_views, photo.views)
                photoDownloads.text = getString(R.string.info_downloads, photo.downloads)

                Glide.with(photoImage).load(photo.urls.regular).fitCenter().into(photoImage)
            }

            return binding.root
        }

    companion object {
        private const val DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss"
    }
}