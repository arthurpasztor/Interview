package com.android.interview.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.android.interview.databinding.ListItemPhotoBinding
import com.android.interview.model.PhotoDTO

typealias ImageClickHandler = (image: PhotoDTO) -> Unit

class PhotosListAdapter(private val onImageClick: ImageClickHandler) :
    ListAdapter<PhotoDTO, PhotoViewHolder>(PhotoDiffCallback()) {

    private var answersList = mutableListOf<PhotoDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PhotoViewHolder.from(parent)

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position), onImageClick)
    }

    fun setList(list: MutableList<PhotoDTO>) {
        answersList = list
        submitList(answersList)
    }

    fun addItem(item: PhotoDTO, onImageAdded: () -> Unit) {
        answersList.add(item)
        notifyItemInserted(answersList.size - 1)
        onImageAdded.invoke()
    }

    fun getSize() = answersList.size
}

class PhotoViewHolder private constructor(private val binding: ListItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(image: PhotoDTO, onImageClick: ImageClickHandler) {
        binding.itemPhoto.apply {
            Glide.with(this).load(image.urls.regular).fitCenter().into(this)

            setOnClickListener { onImageClick.invoke(image) }
        }
    }

    companion object {
        fun from(parent: ViewGroup): PhotoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemPhotoBinding.inflate(layoutInflater, parent, false)
            return PhotoViewHolder(binding)
        }
    }
}

class PhotoDiffCallback : DiffUtil.ItemCallback<PhotoDTO>() {

    override fun areItemsTheSame(oldItem: PhotoDTO, newItem: PhotoDTO): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: PhotoDTO, newItem: PhotoDTO): Boolean =
        oldItem == newItem
}
