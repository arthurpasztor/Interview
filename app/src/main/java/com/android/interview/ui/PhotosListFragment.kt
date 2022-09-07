package com.android.interview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.interview.InterviewApp
import com.android.interview.business.PhotoViewModel
import com.android.interview.business.PhotoViewModelFactory
import com.android.interview.databinding.FragmentPhotosListBinding
import com.android.interview.model.PhotoDTO
import com.android.interview.ui.adapter.PhotosListAdapter

class PhotosListFragment : Fragment() {

    private val viewModel: PhotoViewModel by viewModels {
        PhotoViewModelFactory((activity?.application as InterviewApp).repository)
    }

    private lateinit var adapter: PhotosListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPhotosListBinding.inflate(inflater, container, false).apply {

            viewModel.fetchPhotos().observe(viewLifecycleOwner) { photosList ->
                if (photosList.isNotEmpty()) {
                    //if there are photos in the DB from the previous session, fetch and display them
                    populatePhotosList(photosList)
                } else {
                    //if there are no photos in the DB from the previous session, initialize the photos list with a random one
                    //from the API
                    progressBar.isVisible = true
                    viewModel.getRandomPhoto().observe(viewLifecycleOwner) { response ->
                        progressBar.isVisible = false
                        response.body()?.let {
                            populatePhotosList(listOf(it))

                            viewModel.insert(it)
                        }
                    }
                }
            }

            buttonAddPhotos.setOnClickListener {
                progressBar.isVisible = true
                viewModel.getRandomPhoto().observe(viewLifecycleOwner) { response ->
                    progressBar.isVisible = false
                    response.body()?.let {
                        adapter.addItem(it) { scrollToBottom() }

                        viewModel.insert(it)
                    }
                }
            }

            buttonDelete.setOnClickListener {
                viewModel.deleteAll()

                adapter.setList(mutableListOf())
                adapter.notifyDataSetChanged()
            }
        }

        return binding.root
    }

    private fun FragmentPhotosListBinding.populatePhotosList(photosList: List<PhotoDTO>) {
        adapter = PhotosListAdapter {
            val directions = PhotosListFragmentDirections.actionListToDetail(it)
            findNavController().navigate(directions)
        }.apply {
            setList(photosList.toMutableList())
            reyclerViewPhotos.adapter = this
        }
    }

    private fun FragmentPhotosListBinding.scrollToBottom() {
        reyclerViewPhotos.postDelayed({
            reyclerViewPhotos.smoothScrollToPosition(adapter.getSize() - 1)
        }, PHOTOS_LIST_SCROLL_DELAY)
    }

    companion object {
        private const val PHOTOS_LIST_SCROLL_DELAY = 600L
    }
}