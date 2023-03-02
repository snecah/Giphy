package com.example.giphy.ui.gifDetailed

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.giphy.R
import com.example.giphy.databinding.FragmentGifDetailedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifDetailedFragment : Fragment(R.layout.fragment_gif_detailed) {
    private val binding by viewBinding(FragmentGifDetailedBinding::bind)

}