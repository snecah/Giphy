package com.example.giphy.ui.gifDetailed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.giphy.R
import com.example.giphy.databinding.FragmentGifDetailedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifDetailedFragment : Fragment(R.layout.fragment_gif_detailed) {
    private val binding by viewBinding(FragmentGifDetailedBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args = GifDetailedFragmentArgs.fromBundle(requireArguments())
        Glide.with(view.context).load(args.gifImage.original.url)
            .placeholder(R.drawable.loading_animation).error(R.drawable.ic_broken_image).into(binding.gifImageDetailed)
    }

}